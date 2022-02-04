package edu.kit.kastel.dsis.seifermann.phd.validation.application.workflow.jobs;

import java.net.URL;

import org.eclipse.emf.common.util.URI;
import org.palladiosimulator.dataflow.confidentiality.pcm.workflow.TransformPCMDFDToPrologWorkflowFactory;
import org.palladiosimulator.dataflow.confidentiality.pcm.workflow.jobs.TransformPCMDFDToPrologJobBuilder;

public class RunPCMAnalysisJob extends RunPrologAnalysisJob {

    private final URI usageModelUri;
    private final URI allocationModelUri;

    public RunPCMAnalysisJob(String resultKey, URI usageModelUri, URI allocationModelUri, URL queryRulesLocation,
            URL queryLocation) {
        super(resultKey, queryRulesLocation, queryLocation);
        this.usageModelUri = usageModelUri;
        this.allocationModelUri = allocationModelUri;
    }

    @Override
    protected String generatePrologProgram() {
        var transformJob = TransformPCMDFDToPrologJobBuilder.create()
            .addUsageModelsByURI(usageModelUri)
            .addAllocationModelByURI(allocationModelUri)
            .addSerializeModelToString()
            .build();
        var transformWorkflow = TransformPCMDFDToPrologWorkflowFactory.createWorkflow(transformJob);
        transformWorkflow.run();
        var prologProgram = transformWorkflow.getPrologProgram();
        return prologProgram.get();
    }

}
