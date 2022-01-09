package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.models;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanism;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.DFDModel;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.DFDModelBase;

@Component(scope = ServiceScope.SINGLETON, service = { DFDModel.class })
public class PrivateTaxiInformationFlowDFDModel extends DFDModelBase {

    public PrivateTaxiInformationFlowDFDModel() {
        super(4, "PrivateTaxi", ConfidentialityMechanism.NonInterferenceArbitraryWithEncryption, "privatetaxi",
                "privatetaxi_arbitrary_dfd.xmi", "privatetaxi_arbitrary_dfd_withIssue.xmi", "InformationFlowRules.pl",
                "InformationFlowQuery.pl", "PrivateTaxi.svg");
    }

    @Override
    protected boolean isAcceptableViolation(Map<String, Object> violation) {
        // TODO Auto-generated method stub
        return false;
    }

}
