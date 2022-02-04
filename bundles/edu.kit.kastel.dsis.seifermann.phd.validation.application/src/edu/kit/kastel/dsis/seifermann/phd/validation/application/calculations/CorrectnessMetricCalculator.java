package edu.kit.kastel.dsis.seifermann.phd.validation.application.calculations;

import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;

import de.uka.ipd.sdq.workflow.blackboard.Blackboard;
import de.uka.ipd.sdq.workflow.jobs.IBlackboardInteractingJob;
import de.uka.ipd.sdq.workflow.jobs.JobFailedException;
import de.uka.ipd.sdq.workflow.jobs.UserCanceledException;
import edu.kit.kastel.dsis.seifermann.phd.validation.application.dto.DFDAnalysisResultDTO;
import edu.kit.kastel.dsis.seifermann.phd.validation.application.dto.DFDModelAnalysisResultDTO;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.Model;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.ModelIndex;

public class CorrectnessMetricCalculator<T extends Model> {

    @FunctionalInterface
    public interface AnalysisJobFactory {
        IBlackboardInteractingJob<Blackboard<Object>> createAnalysisJob(String resultKey, URL queryRulesLocation,
                URL queryLocation, Collection<URI> modelUris);
    }

    private final ModelIndex<T> modelIndex;
    private final AnalysisJobFactory analysisJobFactory;
    private final Function<T, Collection<URI>> noIssueURIsGetter;
    private final Function<T, Collection<URI>> issueURIsGetter;

    public CorrectnessMetricCalculator(ModelIndex<T> modelIndex, AnalysisJobFactory analysisJobFactory,
            Function<T, Collection<URI>> noIssueURIsGetter, Function<T, Collection<URI>> issueURIsGetter) {
        this.modelIndex = modelIndex;
        this.analysisJobFactory = analysisJobFactory;
        this.noIssueURIsGetter = noIssueURIsGetter;
        this.issueURIsGetter = issueURIsGetter;
    }

    public Map<T, DFDAnalysisResultDTO> runAnalyses(Predicate<T> modelSelector, IProgressMonitor monitor)
            throws JobFailedException, UserCanceledException {
        var result = new HashMap<T, DFDAnalysisResultDTO>();
        var models = modelIndex.getModelList(m -> m.hasQuery() && modelSelector.test(m));
        for (var model : models) {
            var analysisResult = runAnalysis(model, monitor);
            result.put(model, analysisResult);
        }
        return result;
    }

    public Map<Integer, DFDModelAnalysisResultDTO> getTruePositivesRaw(
            Map<T, DFDAnalysisResultDTO> dfdAnalysisResult) {
        return dfdAnalysisResult.entrySet()
            .stream()
            .collect(Collectors.toMap(entry -> entry.getKey()
                .getCaseStudySystemIdentifier(),
                    entry -> entry.getValue()
                        .getDfdWithIssue()));
    }

    public Map<Integer, DFDModelAnalysisResultDTO> getTrueNegativesRaw(
            Map<T, DFDAnalysisResultDTO> dfdAnalysisResult) {
        return dfdAnalysisResult.entrySet()
            .stream()
            .collect(Collectors.toMap(entry -> entry.getKey()
                .getCaseStudySystemIdentifier(),
                    entry -> entry.getValue()
                        .getDfdWithoutIssue()));
    }

    public double calculateTrueNegativeRate(Map<Integer, DFDModelAnalysisResultDTO> trueNegativesRaw) {
        return calculateRateByWrongViolations(trueNegativesRaw.values(), r -> !r.getViolations()
            .isEmpty());
    }

    public double calculateTruePositiveRate(Map<Integer, DFDModelAnalysisResultDTO> truePositivesRaw) {
        return calculateRateByWrongViolations(truePositivesRaw.values(), r -> r.getViolations()
            .isEmpty()
                || !r.getWrongViolations()
                    .isEmpty());
    }

    private double calculateRateByWrongViolations(Collection<DFDModelAnalysisResultDTO> results,
            Predicate<DFDModelAnalysisResultDTO> invalidResultTester) {
        var systemsWithWrongResults = (int) results.stream()
            .filter(invalidResultTester::test)
            .count();
        var totalAmountOfSystems = results.size();
        return (totalAmountOfSystems - systemsWithWrongResults) / (double) totalAmountOfSystems;
    }

    private DFDAnalysisResultDTO runAnalysis(T model, IProgressMonitor monitor)
            throws JobFailedException, UserCanceledException {
        var result = new DFDAnalysisResultDTO();

        // run analysis for variant without issue -> all violations are wrong
        var noIssueURIs = noIssueURIsGetter.apply(model);
        var noIssueResult = new DFDModelAnalysisResultDTO();
        var noIssueSolution = runPrologAnalysis(model.getQueryRulesLocation(), model.getQueryLocation(), monitor,
                noIssueURIs);
        noIssueResult.setViolations(noIssueSolution);
        noIssueResult.setWrongViolations(noIssueSolution);
        result.setDfdWithoutIssue(noIssueResult);

        // run analysis for variant with issue -> test if violations are correct
        var issueURIs = issueURIsGetter.apply(model);
        var issueResult = new DFDModelAnalysisResultDTO();
        var issueSolution = runPrologAnalysis(model.getQueryRulesLocation(), model.getQueryLocation(), monitor,
                issueURIs);
        var issueSolutionWrong = issueSolution.stream()
            .filter(solution -> !model.isAcceptableViolation(solution))
            .collect(Collectors.toList());
        issueResult.setViolations(issueSolution);
        issueResult.setWrongViolations(issueSolutionWrong);
        result.setDfdWithIssue(issueResult);

        return result;
    }

    @SuppressWarnings("unchecked")
    private Collection<Map<String, Object>> runPrologAnalysis(URL queryRules, URL query, IProgressMonitor monitor,
            Collection<URI> modelUris) throws JobFailedException, UserCanceledException {
        var analysisJob = analysisJobFactory.createAnalysisJob("result", queryRules, query, modelUris);
        var blackboard = new Blackboard<Object>();
        analysisJob.setBlackboard(blackboard);
        analysisJob.execute(monitor);
        return (Collection<Map<String, Object>>) blackboard.getPartition("result");
    }

}
