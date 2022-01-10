package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.models;

import java.util.Collection;
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
    @SuppressWarnings("unchecked")
    public boolean isAcceptableViolation(Map<String, Object> violation) {
        var requiredRoles = (Collection<String>) violation.get("REQ");
        var assignedRoles = (Collection<String>) violation.get("ROLES");
        var flowTree = (Collection<Object>) violation.get("S");
        var flattenedFlowTree = flattenFlowTree(flowTree);

        var flowTreeContainsMaliciousFlow = flattenedFlowTree.stream()
            .anyMatch(flow -> flow.contains("contact direct"));
        var onlyHasSMSManagerRole = assignedRoles.size() == 1 && assignedRoles.stream()
            .anyMatch(id -> id.contains("SMSManager "));
        var noAccessForSMSManager = requiredRoles.stream()
            .noneMatch(id -> id.contains("SMSManager "));

        return flowTreeContainsMaliciousFlow && onlyHasSMSManagerRole && noAccessForSMSManager;
    }

}
