package edu.kit.kastel.dsis.seifermann.phd.validation.application.workflow;

import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;

import de.uka.ipd.sdq.workflow.blackboard.Blackboard;
import de.uka.ipd.sdq.workflow.jobs.AbstractBlackboardInteractingJob;
import de.uka.ipd.sdq.workflow.jobs.CleanupFailedException;
import de.uka.ipd.sdq.workflow.jobs.IBlackboardInteractingJob;
import de.uka.ipd.sdq.workflow.jobs.JobFailedException;
import de.uka.ipd.sdq.workflow.jobs.UserCanceledException;
import edu.kit.kastel.dsis.seifermann.phd.validation.application.calculations.CorrectnessMetricCalculator;
import edu.kit.kastel.dsis.seifermann.phd.validation.application.calculations.CorrectnessMetricCalculator.AnalysisJobFactory;
import edu.kit.kastel.dsis.seifermann.phd.validation.application.calculations.ExpressivenessWeightedRatioMetricCalculator;
import edu.kit.kastel.dsis.seifermann.phd.validation.application.dto.ADLIntegrationValidationResult;
import edu.kit.kastel.dsis.seifermann.phd.validation.application.dto.RatioDTO;
import edu.kit.kastel.dsis.seifermann.phd.validation.application.internal.Activator;
import edu.kit.kastel.dsis.seifermann.phd.validation.application.workflow.jobs.RunPCMAnalysisJob;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.CommunicationParadigm;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanism;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanismCategory;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.PCMModel;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.PCMModelIndex;

public class PCMValidationWorkflow extends AbstractBlackboardInteractingJob<Blackboard<Object>> {

    private static final PCMModelIndex MODEL_INDEX = Activator.getInstance()
        .getPCMModelIndex();
    private final String resultsKey;

    public PCMValidationWorkflow(String resultsKey) {
        super();
        this.resultsKey = resultsKey;
    }

    @Override
    public void execute(IProgressMonitor monitor) throws JobFailedException, UserCanceledException {
        var result = new ADLIntegrationValidationResult();
        getBlackboard().addPartition(resultsKey, result);
        monitor.beginTask("Run DFD analyses in Prolog", 1);

        validateExpressiveness(result);
        validateCorrectness(result, monitor);
    }

    private void validateExpressiveness(ADLIntegrationValidationResult resultObject) {
        var vm81_raw = getExpressedModelsRatios(CommunicationParadigm.CONTROL_FLOW,
                ConfidentialityMechanismCategory.AccessControl);
        var vm81 = calculateWeightedRatioMetric(vm81_raw);
        resultObject.setVm81(vm81);
        resultObject.setVm81_raw(vm81_raw);

        var vm82_raw = getExpressedModelsRatios(CommunicationParadigm.CONTROL_FLOW,
                ConfidentialityMechanismCategory.InformationFlow);
        var vm82 = calculateWeightedRatioMetric(vm82_raw);
        resultObject.setVm82(vm82);
        resultObject.setVm82_raw(vm82_raw);

        var vm83_raw = getExpressedModelsRatios(CommunicationParadigm.CONTROL_FLOW,
                ConfidentialityMechanismCategory.Mixed);
        var vm83 = calculateWeightedRatioMetric(vm83_raw);
        resultObject.setVm83(vm83);
        resultObject.setVm83_raw(vm83_raw);

        var vm84_raw = getExpressedModelsRatios(CommunicationParadigm.DATA_FLOW,
                ConfidentialityMechanismCategory.AccessControl);
        var vm84 = calculateWeightedRatioMetric(vm84_raw);
        resultObject.setVm84(vm84);
        resultObject.setVm84_raw(vm84_raw);

        var vm85_raw = getExpressedModelsRatios(CommunicationParadigm.DATA_FLOW,
                ConfidentialityMechanismCategory.InformationFlow);
        var vm85 = calculateWeightedRatioMetric(vm85_raw);
        resultObject.setVm85(vm85);
        resultObject.setVm85_raw(vm85_raw);

        var vm86_raw = getExpressedModelsRatios(CommunicationParadigm.DATA_FLOW,
                ConfidentialityMechanismCategory.Mixed);
        var vm86 = calculateWeightedRatioMetric(vm86_raw);
        resultObject.setVm86(vm86);
        resultObject.setVm86_raw(vm86_raw);
    }

    private void validateCorrectness(ADLIntegrationValidationResult result, IProgressMonitor monitor)
            throws JobFailedException, UserCanceledException {
        var calculator = createCorrectnessMetricCalculator();

        var cfAnalysisResults = calculator
            .runAnalyses(m -> m.getCommunicationParadigm() == CommunicationParadigm.CONTROL_FLOW, monitor);
        var cfTrueNegativesRaw = calculator.getTrueNegativesRaw(cfAnalysisResults);
        var cfTrueNegativeRate = calculator.calculateTrueNegativeRate(cfTrueNegativesRaw);
        var cfTruePositivesRaw = calculator.getTruePositivesRaw(cfAnalysisResults);
        var cfTruePositivesRate = calculator.calculateTruePositiveRate(cfTruePositivesRaw);

        result.setVm91(cfTruePositivesRate);
        result.setVm91_raw(cfTruePositivesRaw);
        result.setVm92(cfTrueNegativeRate);
        result.setVm92_raw(cfTrueNegativesRaw);

        var dfAnalysisResults = calculator
            .runAnalyses(m -> m.getCommunicationParadigm() == CommunicationParadigm.DATA_FLOW, monitor);
        var dfTrueNegativesRaw = calculator.getTrueNegativesRaw(dfAnalysisResults);
        var dfTrueNegativeRate = calculator.calculateTrueNegativeRate(dfTrueNegativesRaw);
        var dfTruePositivesRaw = calculator.getTruePositivesRaw(dfAnalysisResults);
        var dfTruePositivesRate = calculator.calculateTruePositiveRate(dfTruePositivesRaw);

        result.setVm93(dfTruePositivesRate);
        result.setVm93_raw(dfTruePositivesRaw);
        result.setVm94(dfTrueNegativeRate);
        result.setVm94_raw(dfTrueNegativesRaw);
    }

    private double calculateWeightedRatioMetric(Map<ConfidentialityMechanism, RatioDTO> expressedModelsRatios) {
        return ExpressivenessWeightedRatioMetricCalculator.calculateWeightedRatioMetric(expressedModelsRatios);
    }

    private Map<ConfidentialityMechanism, RatioDTO> getExpressedModelsRatios(CommunicationParadigm paradigm,
            ConfidentialityMechanismCategory category) {
        var models = MODEL_INDEX.getModelList(m -> m.getCommunicationParadigm() == paradigm && m.getMechanism()
            .getCategory() == category);
        var calculator = new ExpressivenessWeightedRatioMetricCalculator(models);
        return calculator.getExpressedModelsRatios();
    }

    private CorrectnessMetricCalculator<PCMModel> createCorrectnessMetricCalculator() {
        AnalysisJobFactory factory = new AnalysisJobFactory() {
            @Override
            public IBlackboardInteractingJob<Blackboard<Object>> createAnalysisJob(String resultKey,
                    URL queryRulesLocation, URL queryLocation, Collection<URI> modelUris) {
                var usageModelURI = modelUris.stream()
                    .filter(u -> u.lastSegment()
                        .endsWith(".usagemodel"))
                    .findFirst()
                    .orElseThrow(
                            () -> new IllegalArgumentException("Could not find a usage model URI for the analysis."));
                var allocationModelURI = modelUris.stream()
                    .filter(u -> u.lastSegment()
                        .endsWith(".allocation"))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Could not find an allocation model URI for the analysis."));
                return new RunPCMAnalysisJob(resultKey, usageModelURI, allocationModelURI, queryRulesLocation,
                        queryLocation);
            }
        };
        Function<PCMModel, Collection<URI>> noIssueURIExtractor = m -> Arrays.asList(m.getUsageModelWithoutViolation(),
                m.getAllocationModelWithoutViolation());
        Function<PCMModel, Collection<URI>> issueURIExtractor = m -> Arrays.asList(m.getUsageModelWithViolation(),
                m.getAllocationModelWithViolation());
        return new CorrectnessMetricCalculator<>(MODEL_INDEX, factory, noIssueURIExtractor, issueURIExtractor);
    }

    @Override
    public void cleanup(IProgressMonitor monitor) throws CleanupFailedException {
        // nothing to do here
    }

    @Override
    public String getName() {
        return "Validation of ADL Integration";
    }

}
