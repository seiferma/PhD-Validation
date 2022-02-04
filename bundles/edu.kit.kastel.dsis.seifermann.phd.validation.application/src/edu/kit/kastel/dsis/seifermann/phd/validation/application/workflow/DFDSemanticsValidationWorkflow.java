package edu.kit.kastel.dsis.seifermann.phd.validation.application.workflow;

import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;

import com.google.common.base.Predicates;

import de.uka.ipd.sdq.workflow.blackboard.Blackboard;
import de.uka.ipd.sdq.workflow.jobs.AbstractBlackboardInteractingJob;
import de.uka.ipd.sdq.workflow.jobs.CleanupFailedException;
import de.uka.ipd.sdq.workflow.jobs.IBlackboardInteractingJob;
import de.uka.ipd.sdq.workflow.jobs.JobFailedException;
import de.uka.ipd.sdq.workflow.jobs.UserCanceledException;
import edu.kit.kastel.dsis.seifermann.phd.validation.application.calculations.CorrectnessMetricCalculator;
import edu.kit.kastel.dsis.seifermann.phd.validation.application.calculations.CorrectnessMetricCalculator.AnalysisJobFactory;
import edu.kit.kastel.dsis.seifermann.phd.validation.application.dto.DFDSemanticsValidationResult;
import edu.kit.kastel.dsis.seifermann.phd.validation.application.internal.Activator;
import edu.kit.kastel.dsis.seifermann.phd.validation.application.workflow.jobs.RunDFDAnalysisJob;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.DFDModel;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.DFDModelIndex;

public class DFDSemanticsValidationWorkflow extends AbstractBlackboardInteractingJob<Blackboard<Object>> {

    private static final DFDModelIndex DFD_MODEL_INDEX = Activator.getInstance()
        .getDFDModelIndex();
    private final String resultsKey;

    public DFDSemanticsValidationWorkflow(String resultsKey) {
        this.resultsKey = resultsKey;
    }

    @Override
    public void execute(IProgressMonitor monitor) throws JobFailedException, UserCanceledException {
        var result = new DFDSemanticsValidationResult();
        getBlackboard().addPartition(resultsKey, result);
        monitor.beginTask("Run DFD analyses in Prolog", 1);

        var calculator = createMetricCalculator();

        var dfdAnalysisResult = calculator.runAnalyses(Predicates.alwaysTrue(), monitor);
        var trueNegativesRaw = calculator.getTrueNegativesRaw(dfdAnalysisResult);
        var trueNegativeRate = calculator.calculateTrueNegativeRate(trueNegativesRaw);
        var truePositivesRaw = calculator.getTruePositivesRaw(dfdAnalysisResult);
        var truePositivesRate = calculator.calculateTruePositiveRate(truePositivesRaw);
        result.setVm61(truePositivesRate);
        result.setVm61_raw(truePositivesRaw);
        result.setVm62(trueNegativeRate);
        result.setVm62_raw(trueNegativesRaw);

        monitor.worked(1);
        monitor.done();
    }

    private CorrectnessMetricCalculator<DFDModel> createMetricCalculator() {
        AnalysisJobFactory factory = new AnalysisJobFactory() {
            @Override
            public IBlackboardInteractingJob<Blackboard<Object>> createAnalysisJob(String resultKey,
                    URL queryRulesLocation, URL queryLocation, Collection<URI> modelUris) {
                return new RunDFDAnalysisJob(resultKey, modelUris.iterator()
                    .next(), queryRulesLocation, queryLocation);
            }
        };
        Function<DFDModel, Collection<URI>> noIssueURIExtractor = m -> Arrays.asList(m.getDfdWithoutViolation());
        Function<DFDModel, Collection<URI>> issueURIExtractor = m -> Arrays.asList(m.getDfdWithViolation());
        return new CorrectnessMetricCalculator<>(DFD_MODEL_INDEX, factory, noIssueURIExtractor, issueURIExtractor);
    }

    @Override
    public void cleanup(IProgressMonitor monitor) throws CleanupFailedException {
        // nothing to do here
    }

    @Override
    public String getName() {
        return "Validation of DFD Semantics";
    }

}
