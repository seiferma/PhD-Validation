package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.models;

import java.util.Collection;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanism;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.DFDModel;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.DFDModelBase;

@Component(scope = ServiceScope.SINGLETON, service = { DFDModel.class })
public class DistanceTrackerAccessControlDFDModel extends DFDModelBase {

    public DistanceTrackerAccessControlDFDModel() {
        super(11, "DistanceTracker", ConfidentialityMechanism.RBAC, "distancetracker",
                "DFDC_DistanceTracker_AccessControl.xmi", "DFDC_DistanceTracker_AccessControl_Withissue.xmi", null,
                "AccessControlQuery.pl", "DistanceTracker-AccessControl.svg");
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean isAcceptableViolation(Map<String, Object> violation) {
        var requiredRoles = (Collection<String>) violation.get("REQ");
        var assignedRoles = (Collection<String>) violation.get("ROLES");
        var flowTree = (Collection<Object>) violation.get("S");
        var flattenedFlowTree = flattenFlowTree(flowTree);

        var flowTreeContainsMaliciousFlow = flattenedFlowTree.stream()
            .anyMatch(flow -> flow.contains("direct distance"));
        var hasOnlyTrackingServiceRole = assignedRoles.size() == 1 && assignedRoles.stream()
            .anyMatch(id -> id.contains("TrackingService "));
        var trackingServiceMustNotAccessData = requiredRoles.stream()
            .noneMatch(id -> id.contains("TrackingService "));

        return flowTreeContainsMaliciousFlow && hasOnlyTrackingServiceRole && trackingServiceMustNotAccessData;
    }

}
