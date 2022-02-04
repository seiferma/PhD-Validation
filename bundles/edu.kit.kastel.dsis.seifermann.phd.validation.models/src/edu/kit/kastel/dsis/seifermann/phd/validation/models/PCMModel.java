package edu.kit.kastel.dsis.seifermann.phd.validation.models;

import org.eclipse.emf.common.util.URI;

public interface PCMModel extends Model {

    URI getUsageModelWithoutViolation();

    URI getUsageModelWithViolation();

    URI getAllocationModelWithoutViolation();

    URI getAllocationModelWithViolation();

    CommunicationParadigm getCommunicationParadigm();

    @Override
    default boolean hasModel() {
        return getAllocationModelWithoutViolation() != null && getAllocationModelWithViolation() != null;
    }

}
