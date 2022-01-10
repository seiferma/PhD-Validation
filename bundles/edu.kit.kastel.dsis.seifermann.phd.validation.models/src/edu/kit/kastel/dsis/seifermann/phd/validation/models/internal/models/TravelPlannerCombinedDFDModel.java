package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.models;

import java.util.Collection;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanism;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.DFDModel;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.DFDModelBase;

@Component(scope = ServiceScope.SINGLETON, service = { DFDModel.class })
public class TravelPlannerCombinedDFDModel extends DFDModelBase {

    public TravelPlannerCombinedDFDModel() {
        super(17, "TravelPlanner", ConfidentialityMechanism.RBAC_TAINT, "travelplanner", "DFDC_TravelPlanner_TMAC.xmi",
                "DFDC_TravelPlanner_TMAC_WithIssue.xmi", null, "TMACQuery.pl", "TravelPlanner_TMAC.svg");
    }

    @Override
    public boolean isAcceptableViolation(Map<String, Object> violation) {
        var processId = violation.get("P")
            .toString();
        var criticalityId = violation.get("C")
            .toString();
        var validationId = violation.get("V")
            .toString();
        @SuppressWarnings("unchecked")
        var flowTree = (Collection<Object>) violation.get("S");
        var flattenedFlowTree = flattenFlowTree(flowTree);

        var flowTreeContainsMaliciousFlow = flattenedFlowTree.stream()
            .anyMatch(id -> id.contains("ccd direct"));
        var notValidated = validationId.contains("NotValidated ");
        var criticalityHigh = criticalityId.contains("High ");
        // everything after receiving not validated ccd is affected: further processing in CCD and
        // in Ariline
        var belongsToAirline = processId.contains("Airline.");
        var belongsToCCC = processId.contains("CCD.");

        return flowTreeContainsMaliciousFlow && notValidated && criticalityHigh && (belongsToAirline || belongsToCCC);
    }

}
