package edu.kit.kastel.dsis.seifermann.phd.validation.application.calculations;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

import edu.kit.kastel.dsis.seifermann.phd.validation.application.calculations.JaccardCoefficientCalculator.JaccardCoefficientRaw;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.CommunicationParadigm;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.PCMModel;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.PCMModelIndex;

public class PCMModelJaccardCoefficientCalculator {

    private final PCMModelIndex modelIndex;

    public PCMModelJaccardCoefficientCalculator(PCMModelIndex modelIndex) {
        this.modelIndex = modelIndex;
    }

    public static Map<Pair<Integer, Integer>, Double> calculateJaccardCoefficient(
            Map<Pair<Integer, Integer>, JaccardCoefficientRaw> jaccardCoefficientsRaw) {
        return jaccardCoefficientsRaw.entrySet()
            .stream()
            .collect(Collectors.toMap(Entry::getKey, e -> e.getValue()
                .getJaccardCoefficient(), (u, v) -> u, LinkedHashMap::new));
    }

    public Map<Pair<Integer, Integer>, JaccardCoefficientRaw> calculateJaccardCoefficientForSwitching(
            CommunicationParadigm pardigm) {
        var modelGroups = modelIndex.getModelList(model -> model.getCommunicationParadigm() == pardigm)
            .stream()
            .sorted((m1, m2) -> m1.getCaseStudySystemIdentifier() - m2.getCaseStudySystemIdentifier())
            .collect(Collectors.groupingBy(PCMModel::getName, LinkedHashMap::new, Collectors.toList()))
            .entrySet()
            .stream()
            .map(Entry::getValue)
            .filter(e -> e.size() > 1)
            .collect(Collectors.toList());

        Map<Pair<Integer, Integer>, JaccardCoefficientRaw> jaccardCoefficientsRaw = new LinkedHashMap<>();
        for (var modelGroup : modelGroups) {
            // construct all possible pairs within model group ignoring the order
            for (int i = 0; i < modelGroup.size(); i++) {
                for (int j = i + 1; j < modelGroup.size(); j++) {
                    var firstModel = modelGroup.get(i);
                    var secondModel = modelGroup.get(j);
                    var jaccardResult = calculateJaccard(firstModel, secondModel);
                    var pairIdentifier = Pair.of(firstModel.getCaseStudySystemIdentifier(),
                            secondModel.getCaseStudySystemIdentifier());
                    jaccardCoefficientsRaw.put(pairIdentifier, jaccardResult);
                }
            }
        }
        return jaccardCoefficientsRaw;
    }

    private static JaccardCoefficientRaw calculateJaccard(PCMModel model1, PCMModel model2) {
        var rs1 = loadModel(model1);
        var rs2 = loadModel(model2);
        var calculator = new JaccardCoefficientCalculator(rs1, rs2);
        var comparisonResult = calculator.compare();
        return JaccardCoefficientCalculator.calculateJaccardCoefficient(comparisonResult);
    }

    private static ResourceSet loadModel(PCMModel model) {
        var rs = new ResourceSetImpl();
        rs.getResource(model.getUsageModelWithoutViolation(), true);
        rs.getResource(model.getAllocationModelWithoutViolation(), true);
        EcoreUtil.resolveAll(rs);
        return rs;
    }
}
