package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.models.pcm;

import java.util.Collection;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.CommunicationParadigm;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanism;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.PCMModel;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.PCMModelBase;

@Component(scope = ServiceScope.SINGLETON, service = { PCMModel.class })
public class ContactSMS_RBAC_CF extends PCMModelBase {

    public ContactSMS_RBAC_CF() {
        super(12, "ContactSMS", ConfidentialityMechanism.RBAC, CommunicationParadigm.CONTROL_FLOW,
                "ContactSMS_CallReturn_RBAC", "newUsageModel.usagemodel", "newUsageModel_withIssue.usagemodel",
                "newAllocation.allocation", "newAllocation_withIssue.allocation", null, "query.pl");
    }

    @Override
    public boolean isAcceptableViolation(Map<String, Object> violation) {
        var nodeId = (String) violation.get("N");
        var pinId = (String) violation.get("PIN");
        @SuppressWarnings("unchecked")
        var assignedRoleIds = (Collection<String>) violation.get("ROLES");
        @SuppressWarnings("unchecked")
        var requiredRoleIds = (Collection<String>) violation.get("REQ");

        var isSMSGateway = nodeId.contains("Assembly_SMSGateway");
        var isNumberPin = pinId.contains("number ");
        var assignedRoleIsReceiver = assignedRoleIds.size() == 1 && assignedRoleIds.iterator()
            .next()
            .contains("Receiver ");
        var userRoleIsRequired = requiredRoleIds.size() == 1 && requiredRoleIds.iterator()
            .next()
            .contains("User ");

        return isSMSGateway && isNumberPin && assignedRoleIsReceiver && userRoleIsRequired;
    }

}
