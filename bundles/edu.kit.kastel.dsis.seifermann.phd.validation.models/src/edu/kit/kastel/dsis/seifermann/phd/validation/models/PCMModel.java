package edu.kit.kastel.dsis.seifermann.phd.validation.models;

import org.eclipse.emf.common.util.URI;

public interface PCMModel extends Model {

    URI getUsageModelWithoutViolation();

    URI getUsageModelWithViolation();

    URI getAllocationModel();

    CommunicationParadigm getCommunicationParadigm();

}
