package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.models;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanism;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.DFDModel;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.DFDModelBase;

@Component(scope = ServiceScope.SINGLETON, service = { DFDModel.class })
public class ContactSMSAccessControlDFDModel extends DFDModelBase {

    public ContactSMSAccessControlDFDModel() {
        super(12, "ContactSMS", ConfidentialityMechanism.RBAC, "contactsms", "DFDC_ContactSMS_AccessControl.xmi",
                "DFDC_ContactSMS_AccessControl_WithIssue.xmi", null, "AccessControlQuery.pl", "ContactSMS-RBAC.svg");
    }

    @Override
    protected boolean isAcceptableViolation(Map<String, Object> violation) {
        // TODO Auto-generated method stub
        return false;
    }

}
