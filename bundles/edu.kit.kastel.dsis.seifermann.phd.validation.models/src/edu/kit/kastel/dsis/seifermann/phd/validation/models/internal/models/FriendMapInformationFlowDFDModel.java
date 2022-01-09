package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.models;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanism;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.DFDModel;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.DFDModelBase;

@Component(scope = ServiceScope.SINGLETON, service = { DFDModel.class })
public class FriendMapInformationFlowDFDModel extends DFDModelBase {

    public FriendMapInformationFlowDFDModel() {
        super(6, "FriendMap", ConfidentialityMechanism.NonInterferenceLinearWithEncryption, "friendmap",
                "DFDC_FriendMapAlternative_Linear.xmi", "DFDC_FriendMap_Linear.xmi", null, "InformationFlowQuery.pl",
                "FriendMap.svg");
    }

    @Override
    protected boolean isAcceptableViolation(Map<String, Object> violation) {
        // TODO Auto-generated method stub
        return false;
    }

}
