package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal;

import org.eclipse.emf.common.util.URI;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.CommunicationParadigm;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanism;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.PCMModel;

public abstract class PCMModelBase extends ModelBase implements PCMModel {

    private final CommunicationParadigm communicationParadigm;
    private final URI usageModelWithoutIssue;
    private final URI usageModelWithIssue;
    private final URI allocationModel;

    public PCMModelBase(int id, String name, ConfidentialityMechanism mechanism, CommunicationParadigm paradigm,
            String folderName, String usageModelWithoutIssueName, String usageModelWithIssueName,
            String allocationModelName, String queryRulesName, String queryName) {
        super("pcmModels", id, name, mechanism, folderName, queryRulesName, queryName);
        this.communicationParadigm = paradigm;
        this.usageModelWithoutIssue = getModelURI(folderName, usageModelWithoutIssueName);
        this.usageModelWithIssue = getModelURI(folderName, usageModelWithIssueName);
        this.allocationModel = getModelURI(folderName, allocationModelName);
    }

    @Override
    public URI getUsageModelWithoutViolation() {
        return usageModelWithoutIssue;
    }

    @Override
    public URI getUsageModelWithViolation() {
        return usageModelWithIssue;
    }

    @Override
    public URI getAllocationModel() {
        return allocationModel;
    }

    @Override
    public CommunicationParadigm getCommunicationParadigm() {
        return communicationParadigm;
    }

}
