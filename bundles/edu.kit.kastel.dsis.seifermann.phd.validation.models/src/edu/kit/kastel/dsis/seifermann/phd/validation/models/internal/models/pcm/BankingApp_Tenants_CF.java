package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.models.pcm;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.CommunicationParadigm;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanism;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.PCMModel;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.PCMModelBase;

@Component(scope = ServiceScope.SINGLETON, service = { PCMModel.class })
public class BankingApp_Tenants_CF extends PCMModelBase {

    public BankingApp_Tenants_CF() {
        super(5, "BankingApp", ConfidentialityMechanism.NonInterferenceTenant, CommunicationParadigm.CONTROL_FLOW, null,
                null, null, null, null, null, null);
    }

    @Override
    public boolean isAcceptableViolation(Map<String, Object> violation) {
        return false;
    }

}
