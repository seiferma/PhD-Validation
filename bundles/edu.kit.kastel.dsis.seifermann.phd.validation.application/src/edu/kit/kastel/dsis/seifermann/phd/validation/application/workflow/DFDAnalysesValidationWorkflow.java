package edu.kit.kastel.dsis.seifermann.phd.validation.application.workflow;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IProgressMonitor;

import de.uka.ipd.sdq.workflow.blackboard.Blackboard;
import de.uka.ipd.sdq.workflow.jobs.AbstractBlackboardInteractingJob;
import de.uka.ipd.sdq.workflow.jobs.CleanupFailedException;
import de.uka.ipd.sdq.workflow.jobs.JobFailedException;
import de.uka.ipd.sdq.workflow.jobs.UserCanceledException;
import edu.kit.kastel.dsis.seifermann.phd.validation.application.dto.DFDAnalysesValidationResult;
import edu.kit.kastel.dsis.seifermann.phd.validation.application.dto.RatioDTO;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanism;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanismCategory;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.DFDModel;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.DFDModelIndex;

public class DFDAnalysesValidationWorkflow extends AbstractBlackboardInteractingJob<Blackboard<Object>> {

    private final String resultKey;

    public DFDAnalysesValidationWorkflow(String resultKey) {
        this.resultKey = resultKey;
    }

    @Override
    public void execute(IProgressMonitor monitor) throws JobFailedException, UserCanceledException {
        monitor.beginTask("Validate DFD analyses", 3);
        var result = new DFDAnalysesValidationResult();
        getBlackboard().addPartition(resultKey, result);

        var expressedRequirementAccessControl = getExpressedRequirements(
                ConfidentialityMechanismCategory.AccessControl);
        var weightedRatioMetricAccessControl = calculateWeightedRatioMetric(expressedRequirementAccessControl);
        result.setVm51(weightedRatioMetricAccessControl);
        result.setVm51_raw(expressedRequirementAccessControl);
        monitor.worked(1);

        var expressedRequirementInformationFlow = getExpressedRequirements(
                ConfidentialityMechanismCategory.InformationFlow);
        var weightedRatioMetricInformationFlow = calculateWeightedRatioMetric(expressedRequirementInformationFlow);
        result.setVm52(weightedRatioMetricInformationFlow);
        result.setVm52_raw(expressedRequirementInformationFlow);
        monitor.worked(1);

        var expressedRequirementMixed = getExpressedRequirements(ConfidentialityMechanismCategory.Mixed);
        var weightedRatioMetricMixed = calculateWeightedRatioMetric(expressedRequirementMixed);
        result.setVm53(weightedRatioMetricMixed);
        result.setVm53_raw(expressedRequirementMixed);
        monitor.worked(1);

        monitor.done();
    }

    private double calculateWeightedRatioMetric(Map<ConfidentialityMechanism, RatioDTO> ratios) {
        return ratios.entrySet()
            .stream()
            .map(Entry::getValue)
            .mapToDouble(RatioDTO::getRatio)
            .average()
            .getAsDouble();
    }

    private Map<ConfidentialityMechanism, RatioDTO> getExpressedRequirements(
            ConfidentialityMechanismCategory category) {
        var result = new HashMap<ConfidentialityMechanism, RatioDTO>();

        var mechanismsInCategory = DFDModelIndex.getModelList(m -> m.hasModel() && m.getMechanism()
            .getCategory() == category)
            .stream()
            .map(DFDModel::getMechanism)
            .collect(Collectors.toSet());
        for (var mechanism : mechanismsInCategory) {
            var modelsUsingMechanism = DFDModelIndex.getModelList(m -> m.hasModel() && m.getMechanism() == mechanism);
            var modelsWithQuery = (int) modelsUsingMechanism.stream()
                .filter(DFDModel::hasQuery)
                .count();
            var numberOfModels = modelsUsingMechanism.size();
            var ratio = new RatioDTO(modelsWithQuery, numberOfModels);
            result.put(mechanism, ratio);
        }

        return result;
    }

    @Override
    public void cleanup(IProgressMonitor monitor) throws CleanupFailedException {
        // nothing to do here
    }

    @Override
    public String getName() {
        return "Validation of DFD Analyses";
    }

}
