package edu.kit.kastel.dsis.seifermann.phd.validation.application.workflow.jobs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.eclipse.core.runtime.IProgressMonitor;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

import de.uka.ipd.sdq.workflow.blackboard.Blackboard;
import de.uka.ipd.sdq.workflow.jobs.AbstractBlackboardInteractingJob;
import de.uka.ipd.sdq.workflow.jobs.CleanupFailedException;
import de.uka.ipd.sdq.workflow.jobs.JobFailedException;
import de.uka.ipd.sdq.workflow.jobs.UserCanceledException;

public class SerialiseObjectToJsonJob extends AbstractBlackboardInteractingJob<Blackboard<Object>> {

    private final String objectKey;
    private final File destinationFile;

    public SerialiseObjectToJsonJob(String objectKey, File destinationFile) {
        this.objectKey = objectKey;
        this.destinationFile = destinationFile;
    }

    @Override
    public void execute(IProgressMonitor monitor) throws JobFailedException, UserCanceledException {
        monitor.beginTask("Serialize JSON", 1);
        var objectToSerialize = getBlackboard().getPartition(objectKey);
        var jsonFactory = createJsonFactory();
        serializeCharacteristics(objectToSerialize, jsonFactory);
        monitor.done();
    }

    private void serializeCharacteristics(Object objectToSerialize, JsonFactory jsonFactory)
            throws JobFailedException {
        try (var baos = new FileOutputStream(destinationFile)) {
            var generator = jsonFactory.createGenerator(baos, JsonEncoding.UTF8);
            generator.writeObject(objectToSerialize);
        } catch (IOException e) {
            throw new JobFailedException("Error while serializing JSON", e);
        }
    }

    private JsonFactory createJsonFactory() {
        var jsonFactory = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT)
            .registerModule(new SimpleModule())
            .getFactory();
        return jsonFactory;
    }

    @Override
    public void cleanup(IProgressMonitor monitor) throws CleanupFailedException {
        // nothing to do here
    }

    @Override
    public String getName() {
        return "Serialize object to JSON";
    }

}
