package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal;

import org.eclipse.emf.common.util.URI;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.CommunicationParadigm;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.PlainPCMModel;

public abstract class PlainPCMModelImpl implements PlainPCMModel {

    private final String systemName;
    private final CommunicationParadigm communicationParadigm;
    private final URI usageModel;
    private final URI allocationModel;

    public PlainPCMModelImpl(String systemName, CommunicationParadigm communicationParadigm, String folderName,
            String usageModelName, String allocationModelName) {
        this.systemName = systemName;
        this.communicationParadigm = communicationParadigm;
        this.usageModel = createPluginURI(folderName, usageModelName);
        this.allocationModel = createPluginURI(folderName, allocationModelName);
    }

    @Override
    public URI getUsageModel() {
        return usageModel;
    }

    @Override
    public URI getAllocationModel() {
        return allocationModel;
    }

    @Override
    public String getSystemName() {
        return systemName;
    }

    @Override
    public CommunicationParadigm getCommunicationParadigm() {
        return communicationParadigm;
    }

    private static URI createPluginURI(String folderName, String fileName) {
        String bundleName = Activator.getInstance()
            .getBundle()
            .getSymbolicName();
        String uriString = String.format("/%s/pcmModels/%s/%s", bundleName, folderName, fileName);
        return URI.createPlatformPluginURI(uriString, true);
    }

}
