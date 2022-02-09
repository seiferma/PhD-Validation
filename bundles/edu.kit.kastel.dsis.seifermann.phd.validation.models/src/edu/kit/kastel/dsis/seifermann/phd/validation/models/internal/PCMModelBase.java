package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal;

import org.eclipse.emf.common.util.URI;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.CommunicationParadigm;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanism;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.PCMModel;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.PlainPCMModel;

public abstract class PCMModelBase extends ModelBase implements PCMModel {

    private final CommunicationParadigm communicationParadigm;
    private final URI usageModelWithoutIssue;
    private final URI usageModelWithIssue;
    private final URI allocationModelWithoutIssue;
    private final URI allocationModelWithIssue;
    private PlainPCMModel plainModel;

    public PCMModelBase(int id, String name, ConfidentialityMechanism mechanism, CommunicationParadigm paradigm,
            String folderName, String usageModelWithoutIssueName, String usageModelWithIssueName,
            String allocationModelWithoutIssueName, String allocationModelWithIssueName, String queryRulesName,
            String queryName) {
        super("pcmModels", id, name, mechanism, folderName, queryRulesName, queryName);
        this.communicationParadigm = paradigm;
        this.usageModelWithoutIssue = getModelURI(folderName, usageModelWithoutIssueName);
        this.usageModelWithIssue = getModelURI(folderName, usageModelWithIssueName);
        this.allocationModelWithoutIssue = getModelURI(folderName, allocationModelWithoutIssueName);
        this.allocationModelWithIssue = getModelURI(folderName, allocationModelWithIssueName);
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
    public URI getAllocationModelWithoutViolation() {
        return allocationModelWithoutIssue;
    }

    @Override
    public URI getAllocationModelWithViolation() {
        return allocationModelWithIssue;
    }

    @Override
    public CommunicationParadigm getCommunicationParadigm() {
        return communicationParadigm;
    }

    @Override
    public PlainPCMModel getPlainPCMModel() {
        return plainModel;
    }

    protected void bindPlainPCMModel(PlainPCMModel plainModel) {
        if (plainModel.getCommunicationParadigm() == getCommunicationParadigm()
                && plainModel.getSystemName() == getName()) {
            this.plainModel = plainModel;
        }
    }

}
