package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.models;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanism;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.DFDModel;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.DFDModelBase;

@Component(scope = ServiceScope.SINGLETON, service = { DFDModel.class })
public class ContactSMSInformationFlowDFDModel extends DFDModelBase {

    public ContactSMSInformationFlowDFDModel() {
        super(3, "ContactSMS", ConfidentialityMechanism.NonInterferenceLinear, "contactsms",
                "DFDC_ContactSMS_InformationFlow.xmi", "DFDC_ContactSMS_InformationFlow_WithIssue.xmi", null,
                "InformationFlowQuery.pl", "ContactSMS-InformationFlow.svg");
    }

    @Override
    protected boolean isAcceptableViolation(Map<String, Object> violation) {
        // TODO Auto-generated method stub
        return false;
    }

}
