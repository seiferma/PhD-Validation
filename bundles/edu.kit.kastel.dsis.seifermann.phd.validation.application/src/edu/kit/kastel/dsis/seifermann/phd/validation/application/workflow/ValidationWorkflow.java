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
    protected final File outputDirectory;

    protected ValidationWorkflow(WorkflowExceptionHandler handler, File outputDirectory) {
        super(createWorkflowJob(outputDirectory), handler, new Blackboard<Object>());
        this.outputDirectory = outputDirectory;
    }

    protected static IJob createWorkflowJob(File outputDirectory) {
        var job = new SequentialBlackboardInteractingJob<>();

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

        return job;
    }

    public static ValidationWorkflow build(WorkflowExceptionHandler handler, File outputDirectory) {
        return new ValidationWorkflow(handler, outputDirectory);
    }

}
