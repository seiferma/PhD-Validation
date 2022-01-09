package edu.kit.kastel.dsis.seifermann.phd.validation.application.workflow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.palladiosimulator.dataflow.diagram.DataFlowDiagram.DataFlowDiagramPackage;
import org.palladiosimulator.dataflow.diagram.characterized.DataFlowDiagramCharacterized.DataFlowDiagramCharacterizedPackage;
import org.palladiosimulator.dataflow.dictionary.DataDictionary.DataDictionaryPackage;
import org.palladiosimulator.dataflow.dictionary.characterized.DataDictionaryCharacterized.DataDictionaryCharacterizedPackage;

import de.uka.ipd.sdq.workflow.blackboard.Blackboard;
import de.uka.ipd.sdq.workflow.jobs.AbstractBlackboardInteractingJob;
import de.uka.ipd.sdq.workflow.jobs.CleanupFailedException;
import de.uka.ipd.sdq.workflow.jobs.JobFailedException;
import de.uka.ipd.sdq.workflow.jobs.UserCanceledException;
import edu.kit.kastel.dsis.seifermann.phd.validation.application.dto.DFDSyntaxValidationResult;
import edu.kit.kastel.dsis.seifermann.phd.validation.application.dto.RatioDTO;
import edu.kit.kastel.dsis.seifermann.phd.validation.application.internal.Activator;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanism;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanismCategory;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.DFDModel;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.DFDModelIndex;

public class DFDSyntaxValidationWorkflow extends AbstractBlackboardInteractingJob<Blackboard<Object>> {

    private static final DFDModelIndex DFD_MODEL_INDEX = Activator.getInstance()
        .getDFDModelIndex();
    private final String resultKey;

    public DFDSyntaxValidationWorkflow(String resultKey) {
        this.resultKey = resultKey;
    }

    @Override
    public void execute(IProgressMonitor monitor) throws JobFailedException, UserCanceledException {
        monitor.beginTask("Validate DFD syntax", 4);
        var validationResult = new DFDSyntaxValidationResult();
        getBlackboard().addPartition(resultKey, validationResult);

        var expressedModelsRatiosAccessControl = getExpressedModelsRatios(
                ConfidentialityMechanismCategory.AccessControl);
        var weightedRatioMetricAccessControl = calculateWeightedRatioMetric(expressedModelsRatiosAccessControl);
        validationResult.setVm11(weightedRatioMetricAccessControl);
        validationResult.setVm11_raw(expressedModelsRatiosAccessControl);
        monitor.worked(1);

        var expressedModelsRatiosInformationFlow = getExpressedModelsRatios(
                ConfidentialityMechanismCategory.InformationFlow);
        var weightedRatioMetricInformationFlow = calculateWeightedRatioMetric(expressedModelsRatiosInformationFlow);
        validationResult.setVm12(weightedRatioMetricInformationFlow);
        validationResult.setVm12_raw(expressedModelsRatiosInformationFlow);
        monitor.worked(1);

        var expressedModelsRatiosMixed = getExpressedModelsRatios(ConfidentialityMechanismCategory.Mixed);
        var weightedRatioMetricMixed = calculateWeightedRatioMetric(expressedModelsRatiosMixed);
        validationResult.setVm21(weightedRatioMetricMixed);
        validationResult.setVm21_raw(expressedModelsRatiosMixed);
        monitor.worked(1);

        var metaModelUsage = getMetaModelUsage();
        var failedUsageMetric = getFailedUsages(metaModelUsage);
        validationResult.setVm31(failedUsageMetric);
        validationResult.setVm31_raw(metaModelUsage);
        monitor.worked(1);

        monitor.done();
    }

    private double calculateWeightedRatioMetric(Map<ConfidentialityMechanism, RatioDTO> expressedModelsRatios) {
        double ratioSum = expressedModelsRatios.values()
            .stream()
            .mapToDouble(ratio -> ratio.getAmount() / (double) ratio.getTotal())
            .sum();
        int ratioAmount = expressedModelsRatios.size();
        double weightedRatio = ratioSum / ratioAmount;
        return weightedRatio;
    }

    private Map<ConfidentialityMechanism, RatioDTO> getExpressedModelsRatios(
            ConfidentialityMechanismCategory category) {
        var result = new HashMap<ConfidentialityMechanism, RatioDTO>();

        var modelsByMechanism = DFD_MODEL_INDEX.getModelList()
            .stream()
            .filter(m -> m.getMechanism()
                .getCategory() == category)
            .collect(Collectors.groupingBy(DFDModel::getMechanism));

        for (var mechanismEntry : modelsByMechanism.entrySet()) {
            var modeled = (int) mechanismEntry.getValue()
                .stream()
                .filter(DFDModel::hasModel)
                .count();
            var total = mechanismEntry.getValue()
                .size();
            var ratio = new RatioDTO(modeled, total);
            result.put(mechanismEntry.getKey(), ratio);
        }

        return result;
    }

    private Collection<String> getFailedUsages(Map<String, Collection<ConfidentialityMechanism>> mmUsage) {
        return mmUsage.entrySet()
            .stream()
            .filter(entry -> entry.getValue()
                .size() < 2)
            .map(Entry::getKey)
            .collect(Collectors.toList());
    }

    private Map<String, Collection<ConfidentialityMechanism>> getMetaModelUsage() {
        var allElements = findAllMetaModelElements();
        var blacklistedElements = findBlacklistedElements(allElements);
        var relevantElements = allElements.stream()
            .filter(element -> !blacklistedElements.contains(element))
            .collect(Collectors.toSet());
        var result = new HashMap<String, Collection<ConfidentialityMechanism>>();
        relevantElements.forEach(m -> result.put(m.getName(), new ArrayList<>()));

        var mechanisms = DFD_MODEL_INDEX.getModelList(DFDModel::hasModel)
            .stream()
            .map(DFDModel::getMechanism)
            .distinct()
            .sorted()
            .collect(Collectors.toList());
        for (var mechanism : mechanisms) {
            var models = DFD_MODEL_INDEX.getModelList(DFDModel::hasModel)
                .stream()
                .filter(m -> m.getMechanism() == mechanism)
                .collect(Collectors.toList());
            var mmUsage = getMetaModelUsage(relevantElements, blacklistedElements, models);
            for (var entry : mmUsage.entrySet()) {
                if (entry.getValue() > 0) {
                    result.get(entry.getKey())
                        .add(mechanism);
                }
            }
        }

        return result;
    }

    private Map<String, Integer> getMetaModelUsage(Collection<EClass> relevantElements,
            Collection<EClass> forbiddenElements, Collection<DFDModel> models) {
        var usageStatistics = new HashMap<EClass, Integer>();
        relevantElements.forEach(element -> usageStatistics.put(element, 0));
        for (var model : models) {
            var usedElements = getUsedMetaModelElements(model.getDfdWithoutViolation());
            if (forbiddenElements.stream()
                .anyMatch(usedElements::contains)) {
                // blacklist is illegal
                throw new IllegalStateException();
            }
            usedElements.stream()
                .filter(relevantElements::contains)
                .forEach(element -> usageStatistics.put(element, usageStatistics.get(element) + 1));
        }

        return usageStatistics.entrySet()
            .stream()
            .collect(Collectors.toMap(entry -> entry.getKey()
                .getName(), entry -> entry.getValue()));
    }

    private Collection<EClass> findBlacklistedElements(Collection<EClass> allElements) {
        var dd = DataDictionaryPackage.eINSTANCE;
        var dfd = DataFlowDiagramPackage.eINSTANCE;
        // ignore primitive data types: not used and not described in thesis
        // ignore refinements: not used and not described in thesis
        // ignore data flow: specialized variant is used instead
        // ignore data: concept replaced by characteristics
        return Arrays.asList(dd.getCollectionDataType(), dd.getCompositeDataType(), dd.getDataType(),
                dd.getPrimitiveDataType(), dd.getEntry(), dfd.getEdgeRefinement(), dfd.getDataFlowDiagramRefinement(),
                dfd.getDataFlow(), dfd.getData());
    }

    private Collection<EClass> getUsedMetaModelElements(URI dfdModelUri) {
        var rs = new ResourceSetImpl();
        rs.getResource(dfdModelUri, true);
        EcoreUtil.resolveAll(rs);
        var metaClasses = new HashSet<EClass>();
        rs.getAllContents()
            .forEachRemaining(element -> {
                if (element instanceof EObject) {
                    metaClasses.add(((EObject) element).eClass());
                }
            });

        return metaClasses.stream()
            .flatMap(element -> {
                var elementsToMarkAsUsed = new ArrayList<>(element.getEAllSuperTypes());
                elementsToMarkAsUsed.add(element);
                return elementsToMarkAsUsed.stream();
            })
            .collect(Collectors.toSet());
    }

    private Collection<EClass> findAllMetaModelElements() {
        var relevantElements = new ArrayList<EClass>();
        var processed = new HashSet<>();
        var queue = new LinkedList<EObject>();
        queue.addAll(DataFlowDiagramCharacterizedPackage.eINSTANCE.eContents());
        queue.addAll(DataDictionaryCharacterizedPackage.eINSTANCE.eContents());
        while (!queue.isEmpty()) {
            var element = queue.pop();
            if (!processed.add(element)) {
                continue;
            }
            if (element instanceof EPackage) {
                queue.add(((EPackage) element).getESuperPackage());
                queue.addAll(element.eContents());
            } else if (element instanceof EReference) {
                queue.add(((EReference) element).getEReferenceType());
            } else if (element instanceof EClass) {
                var eclass = (EClass) element;
                queue.add(eclass.getEPackage());
                queue.addAll(eclass.getESuperTypes());
                queue.addAll(((EClass) element).getEAllStructuralFeatures());
                if (eclass.isAbstract() || eclass.isInterface()) {
                    continue;
                }
                relevantElements.add(eclass);
            }
        }
        return relevantElements;
    }

    @Override
    public void cleanup(IProgressMonitor monitor) throws CleanupFailedException {
        // nothing to do here
    }

    @Override
    public String getName() {
        return "Validation of DFD Syntax";
    }

}
