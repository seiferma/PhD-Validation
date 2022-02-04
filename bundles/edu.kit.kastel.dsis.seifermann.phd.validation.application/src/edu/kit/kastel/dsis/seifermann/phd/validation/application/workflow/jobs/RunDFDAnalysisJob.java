package edu.kit.kastel.dsis.seifermann.phd.validation.application.workflow.jobs;

import java.net.URL;

import org.eclipse.emf.common.util.URI;
import org.palladiosimulator.dataflow.confidentiality.transformation.workflow.TransformationWorkflowBuilder;

public class RunDFDAnalysisJob extends RunPrologAnalysisJob {
    private final URI dfdModelUri;

    public RunDFDAnalysisJob(String resultKey, URI dfdModelUri, URL queryRulesLocation, URL queryLocation) {
        super(resultKey, queryRulesLocation, queryLocation);
        this.dfdModelUri = dfdModelUri;
    }

    @Override
    protected String generatePrologProgram() {
        var workflow = new TransformationWorkflowBuilder().addDFD(dfdModelUri)
            .addSerializeToString()
            .build();
        workflow.run();
        return workflow.getSerializedPrologProgram()
            .get();
    }

}
