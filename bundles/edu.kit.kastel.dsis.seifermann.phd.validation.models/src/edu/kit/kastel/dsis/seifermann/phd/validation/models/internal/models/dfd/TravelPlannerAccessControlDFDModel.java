package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.models.dfd;

import java.util.Collection;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanism;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.DFDModel;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.DFDModelBase;

@Component(scope = ServiceScope.SINGLETON, service = { DFDModel.class })
public class TravelPlannerAccessControlDFDModel extends DFDModelBase {

    public TravelPlannerAccessControlDFDModel() {
        super(10, "TravelPlanner", ConfidentialityMechanism.RBAC, "travelplanner",
                "DFDC_TravelPlanner_AccessControl.xmi", "DFDC_TravelPlanner_AccessControl_WithIssue.xmi", null,
                "AccessControlQuery.pl", "TravelPlanner_AccessControl.svg");
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean isAcceptableViolation(Map<String, Object> violation) {
        var processId = violation.get("P")
            .toString();
        var requiredRoles = (Collection<String>) violation.get("REQ");
        var assignedRoles = (Collection<String>) violation.get("ROLES");
        var flowTree = (Collection<Object>) violation.get("S");
        var flattenedFlowTree = flattenFlowTree(flowTree);

        var flowTreeContainsMaliciousFlow = flattenedFlowTree.stream()
            .anyMatch(flow -> flow.contains("ccd direct"));
        var belongsToAirline = processId.contains("Airline.");
        var hasOnlyAirlineRole = assignedRoles.size() == 1 && assignedRoles.stream()
            .anyMatch(id -> id.contains("Airline "));
        var onlyAccessibleToUserRole = requiredRoles.size() == 1 && requiredRoles.stream()
            .anyMatch(id -> id.contains("User "));

        return flowTreeContainsMaliciousFlow && belongsToAirline && hasOnlyAirlineRole && onlyAccessibleToUserRole;
    }

}
