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
public class DistanceTracker_RBAC_CF extends PCMModelBase {

    public DistanceTracker_RBAC_CF() {
        super(11, "DistanceTracker", ConfidentialityMechanism.RBAC, CommunicationParadigm.CONTROL_FLOW,
                "DistanceTracker_CallReturn_RBAC", "newUsageModel.usagemodel", "newUsageModel_withIssue.usagemodel",
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

        var isTrackingService = nodeId.contains("Assembly_TrackingService") || nodeId.contains("TrackingServiceLogic");
        var isDistanceDB = nodeId.contains("DistanceDB");
        var isDistancePin = pinId.contains("distance ");
        var isOnlyTrackingServiceRoleAssigned = assignedRoleIds.size() == 1 && assignedRoleIds.iterator()
            .next()
            .contains("TrackingService ");
        var requiredRolesAreUserAndDistanceTracker = requiredRoleIds.size() == 2 && requiredRoleIds.stream()
            .anyMatch(id -> id.contains("DistanceTracker")) && requiredRoleIds.stream()
                .anyMatch(id -> id.contains("User "));

        return ((isTrackingService && isDistancePin) || isDistanceDB) && isOnlyTrackingServiceRoleAssigned
                && requiredRolesAreUserAndDistanceTracker;
    }

}
