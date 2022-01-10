package edu.kit.kastel.dsis.seifermann.phd.validation.application.workflow;

import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;

import de.uka.ipd.sdq.workflow.blackboard.Blackboard;
import de.uka.ipd.sdq.workflow.jobs.AbstractBlackboardInteractingJob;
import de.uka.ipd.sdq.workflow.jobs.CleanupFailedException;
import de.uka.ipd.sdq.workflow.jobs.JobFailedException;
import de.uka.ipd.sdq.workflow.jobs.UserCanceledException;
import edu.kit.kastel.dsis.seifermann.phd.validation.application.dto.DFDAnalysisResultDTO;
import edu.kit.kastel.dsis.seifermann.phd.validation.application.dto.DFDModelAnalysisResultDTO;
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

        var dfdAnalysisResult = runAnalyses(new NullProgressMonitor());
        var falsePositivesRaw = getFalsePositivesRaw(dfdAnalysisResult);
        var falsePositives = countResultsWithWrongViolations(falsePositivesRaw.values());
        var falseNegativesRaw = getFalseNegativesRaw(dfdAnalysisResult);
        var falseNegatives = countResultsWithWrongViolations(falseNegativesRaw.values());
        result.setVm61(falsePositives);
        result.setVm61_raw(falsePositivesRaw);
        result.setVm62(falseNegatives);
        result.setVm62_raw(falseNegativesRaw);
        
        monitor.worked(1);
        monitor.done();
    }

    private Map<Integer, DFDModelAnalysisResultDTO> getFalseNegativesRaw(
            Map<DFDModel, DFDAnalysisResultDTO> dfdAnalysisResult) {
        return dfdAnalysisResult.entrySet()
            .stream()
            .collect(Collectors.toMap(entry -> entry.getKey()
                .getCaseStudySystemIdentifier(),
                    entry -> entry.getValue()
                        .getDfdWithIssue()));
    }

    private Map<Integer, DFDModelAnalysisResultDTO> getFalsePositivesRaw(
            Map<DFDModel, DFDAnalysisResultDTO> dfdAnalysisResult) {
        return dfdAnalysisResult.entrySet()
            .stream()
            .collect(Collectors.toMap(entry -> entry.getKey()
                .getCaseStudySystemIdentifier(),
                    entry -> entry.getValue()
                        .getDfdWithoutIssue()));
    }

    private int countResultsWithWrongViolations(Collection<DFDModelAnalysisResultDTO> results) {
        return (int) results.stream()
            .filter(result -> !result.getWrongViolations()
                .isEmpty())
            .count();
    }

    private Map<DFDModel, DFDAnalysisResultDTO> runAnalyses(IProgressMonitor monitor)
            throws JobFailedException, UserCanceledException {
        var result = new HashMap<DFDModel, DFDAnalysisResultDTO>();
        var models = DFD_MODEL_INDEX.getModelList(DFDModel::hasQuery);
        for (var model : models) {
            var analysisResult = runAnalysis(model, monitor);
            result.put(model, analysisResult);
        }
        return result;
    }

    private DFDAnalysisResultDTO runAnalysis(DFDModel model, IProgressMonitor monitor)
            throws JobFailedException, UserCanceledException {
        var result = new DFDAnalysisResultDTO();

        // run analysis for variant without issue -> all violations are wrong
        var noIssueResult = new DFDModelAnalysisResultDTO();
        var noIssueSolution = runPrologAnalysis(model.getDfdWithoutViolation(), model.getQueryRulesLocation(),
                model.getQueryLocation(), monitor);
        noIssueResult.setViolations(noIssueSolution);
        noIssueResult.setWrongViolations(noIssueSolution);
        result.setDfdWithoutIssue(noIssueResult);

        // run analysis for variant with issue -> test if violations are correct
        var issueResult = new DFDModelAnalysisResultDTO();
        var issueSolution = runPrologAnalysis(model.getDfdWithViolation(), model.getQueryRulesLocation(),
                model.getQueryLocation(), monitor);
        var issueSolutionWrong = issueSolution.stream()
            .filter(solution -> !model.isAcceptableViolation(solution))
            .collect(Collectors.toList());
        issueResult.setViolations(issueSolution);
        issueResult.setWrongViolations(issueSolutionWrong);
        result.setDfdWithIssue(issueResult);

        return result;
    }

    @SuppressWarnings("unchecked")
    private Collection<Map<String, Object>> runPrologAnalysis(URI dfdModel, URL queryRules, URL query,
            IProgressMonitor monitor) throws JobFailedException, UserCanceledException {
        var analysisJob = new RunDFDAnalysisJob("result", dfdModel, queryRules, query);
        var blackboard = new Blackboard<Object>();
        analysisJob.setBlackboard(blackboard);
        analysisJob.execute(monitor);
        return (Collection<Map<String, Object>>) blackboard.getPartition("result");
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
