package edu.kit.kastel.dsis.seifermann.phd.validation.application.workflow;

import java.io.File;

import de.uka.ipd.sdq.workflow.BlackboardBasedWorkflow;
import de.uka.ipd.sdq.workflow.WorkflowExceptionHandler;
import de.uka.ipd.sdq.workflow.blackboard.Blackboard;
import de.uka.ipd.sdq.workflow.jobs.IJob;
import de.uka.ipd.sdq.workflow.jobs.SequentialBlackboardInteractingJob;
import edu.kit.kastel.dsis.seifermann.phd.validation.application.workflow.jobs.CreateDirectoryJob;
import edu.kit.kastel.dsis.seifermann.phd.validation.application.workflow.jobs.SerialiseObjectToJsonJob;

public class ValidationWorkflow extends BlackboardBasedWorkflow<Blackboard<Object>> {

    protected static final String KEY_RESULT_DFDSYNTAX = "validationResultDfdSyntax";
    protected static final String KEY_RESULT_DFDANALYSES = "validationResultDfdAnalyses";
    protected static final String KEY_RESULT_DFDSEMANTICS = "validationResultDfdSemantics";
    protected static final String KEY_RESULT_ADLINTEGRATION = "validationResultAdlIntegration";
    protected final File outputDirectory;

    protected ValidationWorkflow(WorkflowExceptionHandler handler, File outputDirectory) {
        super(createWorkflowJob(outputDirectory), handler, new Blackboard<Object>());
        this.outputDirectory = outputDirectory;
    }

    protected static IJob createWorkflowJob(File outputDirectory) {
        var job = new SequentialBlackboardInteractingJob<>("Execution of validation goal jobs");

        var createOuputDirJob = new CreateDirectoryJob(outputDirectory);
        job.add(createOuputDirJob);

        var vg1Job = new DFDSyntaxValidationWorkflow(KEY_RESULT_DFDSYNTAX);
        job.add(vg1Job);

        var vg1File = new File(outputDirectory, "vg1.json");
        var vg1SerializeJob = new SerialiseObjectToJsonJob(KEY_RESULT_DFDSYNTAX, vg1File);
        job.add(vg1SerializeJob);

        var vg2Job = new DFDAnalysesValidationWorkflow(KEY_RESULT_DFDANALYSES);
        job.add(vg2Job);

        var vg2File = new File(outputDirectory, "vg2.json");
        var vg2SerializeJob = new SerialiseObjectToJsonJob(KEY_RESULT_DFDANALYSES, vg2File);
        job.add(vg2SerializeJob);

        var vg3Job = new DFDSemanticsValidationWorkflow(KEY_RESULT_DFDSEMANTICS);
        job.add(vg3Job);

        var vg3File = new File(outputDirectory, "vg3.json");
        var vg3SerializeJob = new SerialiseObjectToJsonJob(KEY_RESULT_DFDSEMANTICS, vg3File);
        job.add(vg3SerializeJob);
        
        var vg4Job = new PCMValidationWorkflow(KEY_RESULT_ADLINTEGRATION);
        job.add(vg4Job);

        var vg4File = new File(outputDirectory, "vg4.json");
        var vg4SerializeJob = new SerialiseObjectToJsonJob(KEY_RESULT_ADLINTEGRATION, vg4File);
        job.add(vg4SerializeJob);

        return job;
    }

    public static ValidationWorkflow build(WorkflowExceptionHandler handler, File outputDirectory) {
        return new ValidationWorkflow(handler, outputDirectory);
    }

}
