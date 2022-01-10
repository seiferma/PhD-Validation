package edu.kit.kastel.dsis.seifermann.phd.validation.application.workflow.jobs;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.dataflow.confidentiality.transformation.workflow.TransformationWorkflowBuilder;
import org.palladiosimulator.supporting.prolog.model.prolog.CompoundTerm;
import org.palladiosimulator.supporting.prolog.model.prolog.expressions.Expression;
import org.palladiosimulator.supporting.prolog.parser.antlr.PrologParser;
import org.prolog4j.IProverFactory;

import de.uka.ipd.sdq.workflow.blackboard.Blackboard;
import de.uka.ipd.sdq.workflow.jobs.AbstractBlackboardInteractingJob;
import de.uka.ipd.sdq.workflow.jobs.CleanupFailedException;
import de.uka.ipd.sdq.workflow.jobs.JobFailedException;
import de.uka.ipd.sdq.workflow.jobs.UserCanceledException;
import edu.kit.kastel.dsis.seifermann.phd.validation.application.internal.Activator;

public class RunDFDAnalysisJob extends AbstractBlackboardInteractingJob<Blackboard<Object>> {

    private static final IProverFactory PROVER_FACTORY = createProverFactory();
    private static final PrologParser PROLOG_PARSER = createPrologParser();
    private final String resultKey;
    private final URI dfdModelUri;
    private final URL queryRulesLocation;
    private final URL queryLocation;

    public RunDFDAnalysisJob(String resultKey, URI dfdModelUri, URL queryRulesLocation, URL queryLocation) {
        this.resultKey = resultKey;
        this.dfdModelUri = dfdModelUri;
        this.queryRulesLocation = queryRulesLocation;
        this.queryLocation = queryLocation;
    }

    @Override
    public void execute(IProgressMonitor monitor) throws JobFailedException, UserCanceledException {
        monitor.beginTask("Run DFD Analysis", 4);

        // transform DFD to Prolog
        var workflow = new TransformationWorkflowBuilder().addDFD(dfdModelUri)
            .addSerializeToString()
            .build();
        workflow.run();
        var prologProgram = workflow.getSerializedPrologProgram();
        monitor.worked(1);

        // load queries and build full program
        String queryRules;
        String query;
        try {
            queryRules = readStringOrReturnEmpty(queryRulesLocation);
            query = readStringOrReturnEmpty(queryLocation);
        } catch (IOException e) {
            throw new JobFailedException("Could not load query.", e);
        }
        var fullPrologProgram = prologProgram.get() + System.lineSeparator() + queryRules;
        monitor.worked(1);

        // run prolog program
        var prover = PROVER_FACTORY.createProver();
        prover.addTheory(fullPrologProgram);
        var proverQuery = prover.query(query);
        var solution = proverQuery.solve();
        monitor.worked(1);

        // parse result
        var result = new ArrayList<Map<String, Object>>();
        if (solution.isSuccess()) {
            var variableNames = parseVariableNames(query);
            for (var iter = solution.iterator(); iter.hasNext(); iter.next()) {
                var resultMap = new HashMap<String, Object>();
                for (var variableName : variableNames) {
                    var object = iter.get(variableName);
                    resultMap.put(variableName, object);
                }
                result.add(resultMap);
            }

        }
        monitor.worked(1);

        // add result to blackboard
        getBlackboard().addPartition(resultKey, result);
        monitor.done();
    }

    @Override
    public void cleanup(IProgressMonitor monitor) throws CleanupFailedException {
        // nothing to cleanup
    }

    @Override
    public String getName() {
        return "Run DFD Analysis in Prolog";
    }

    private static Collection<String> parseVariableNames(String query) {
        var initialRule = PROLOG_PARSER.getGrammarAccess()
            .getExpression_1100_xfyRule();
        var parsingResult = PROLOG_PARSER.parse(initialRule, new StringReader(query));
        var parsedQuery = (Expression) parsingResult.getRootASTElement();

        var variables = new LinkedHashSet<String>();
        var queue = new LinkedList<EObject>();
        queue.add(parsedQuery);
        while (!queue.isEmpty()) {
            EObject currentElement = queue.pop();
            queue.addAll(currentElement.eContents());
            if (currentElement instanceof CompoundTerm) {
                var term = (CompoundTerm) currentElement;
                if (term.getArguments()
                    .isEmpty()) {
                    variables.add(term.getValue());
                }
            }
        }
        return variables;
    }

    private static String readStringOrReturnEmpty(URL location) throws IOException {
        if (location == null) {
            return "";
        }
        return IOUtils.toString(location.openStream(), StandardCharsets.UTF_8);
    }

    private static IProverFactory createProverFactory() {
        return Activator.getInstance()
            .getProverManager()
            .getProvers()
            .values()
            .iterator()
            .next();
    }

    private static PrologParser createPrologParser() {
        return Activator.getInstance()
            .getPrologAPI()
            .getParser();
    }

}
