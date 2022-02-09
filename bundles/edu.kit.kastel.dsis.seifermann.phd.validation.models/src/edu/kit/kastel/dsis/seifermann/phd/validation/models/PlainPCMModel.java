package edu.kit.kastel.dsis.seifermann.phd.validation.models;

import org.eclipse.emf.common.util.URI;

public interface PlainPCMModel {
    String getSystemName();
    
    CommunicationParadigm getCommunicationParadigm();
    
    URI getUsageModel();

    URI getAllocationModel();
}
