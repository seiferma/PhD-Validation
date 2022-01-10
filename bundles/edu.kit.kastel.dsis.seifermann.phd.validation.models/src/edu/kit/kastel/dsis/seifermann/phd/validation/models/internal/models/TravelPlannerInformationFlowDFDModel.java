package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.models;

import java.util.Collection;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanism;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.DFDModel;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.DFDModelBase;

@Component(scope = ServiceScope.SINGLETON, service = { DFDModel.class })
public class TravelPlannerInformationFlowDFDModel extends DFDModelBase {

    public TravelPlannerInformationFlowDFDModel() {
        super(1, "TravelPlanner", ConfidentialityMechanism.NonInterferenceLinear, "travelplanner",
                "DFDC_TravelPlanner_InformationFlow.xmi", "DFDC_TravelPlanner_InformationFlow_WithIssue.xmi", null,
                "InformationFlowQuery.pl", "TravelPlanner_InformationFlow.svg");
    }

    @Override
    public boolean isAcceptableViolation(Map<String, Object> violation) {
        var processId = violation.get("P")
            .toString();
        var classificationId = violation.get("V_LEVEL")
            .toString();
        var clearanceId = violation.get("V_CLEAR")
            .toString();
        @SuppressWarnings("unchecked")
        var flowTree = (Collection<Object>) violation.get("S");
        var flattenedFlowTree = flattenFlowTree(flowTree);

        var flowTreeContainsMaliciousFlow = flattenedFlowTree.stream()
            .anyMatch(flow -> flow.contains("ccd direct"));
        var belongsToAirline = processId.contains("Airline.");
        var clearanceForAirline = clearanceId.contains("User,Airline ");
        var classificationForUser = classificationId.contains("User ");

        return flowTreeContainsMaliciousFlow && belongsToAirline && clearanceForAirline && classificationForUser;
    }

}
