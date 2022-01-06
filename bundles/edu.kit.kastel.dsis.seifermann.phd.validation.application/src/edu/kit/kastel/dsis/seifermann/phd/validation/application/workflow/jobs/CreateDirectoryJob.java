package edu.kit.kastel.dsis.seifermann.phd.validation.application.workflow.jobs;

import java.io.File;

import org.eclipse.core.runtime.IProgressMonitor;

import de.uka.ipd.sdq.workflow.jobs.AbstractJob;
import de.uka.ipd.sdq.workflow.jobs.CleanupFailedException;
import de.uka.ipd.sdq.workflow.jobs.JobFailedException;
import de.uka.ipd.sdq.workflow.jobs.UserCanceledException;

public class CreateDirectoryJob extends AbstractJob {

    private final File directoryToCreate;

    public CreateDirectoryJob(File directoryToCreate) {
        super();
        this.directoryToCreate = directoryToCreate;
    }

    @Override
    public void execute(IProgressMonitor monitor) throws JobFailedException, UserCanceledException {
        if (!directoryToCreate.exists()) {
            if (!directoryToCreate.mkdir()) {
                throw new JobFailedException("Could not create directory " + directoryToCreate.getAbsolutePath());
            }
        } else if (!directoryToCreate.isDirectory()) {
            throw new JobFailedException(
                    "File " + directoryToCreate.getAbsolutePath() + " exists but should be a directory.");
        }
    }

    @Override
    public void cleanup(IProgressMonitor monitor) throws CleanupFailedException {
        // nothing to do here
    }

    @Override
    public String getName() {
        return "Create directory";
    }

}
