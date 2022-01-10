package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.models;

import java.util.Collection;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanism;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.DFDModel;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.DFDModelBase;

@Component(scope = ServiceScope.SINGLETON, service = { DFDModel.class })
public class DistanceTrackerInformationFlowDFDModel extends DFDModelBase {

    public DistanceTrackerInformationFlowDFDModel() {
        super(2, "DistanceTracker", ConfidentialityMechanism.NonInterferenceLinear, "distancetracker",
                "DFDC_DistanceTracker_InformationFlow.xmi", "DFDC_DistanceTracker_InformationFlow_WithIssue.xmi", null,
                "InformationFlowQuery.pl", "DistanceTracker-InformationFlow.svg");
    }

    @Override
    public boolean isAcceptableViolation(Map<String, Object> violation) {
        var classificationId = violation.get("V_LEVEL")
            .toString();
        var clearanceId = violation.get("V_CLEAR")
            .toString();
        @SuppressWarnings("unchecked")
        var flowTree = (Collection<Object>) violation.get("S");
        var flattenedFlowTree = flattenFlowTree(flowTree);

        var flowTreeContainsMaliciousFlow = flattenedFlowTree.stream()
            .anyMatch(flow -> flow.contains("direct distance"));
        var clearanceForTrackingService = clearanceId.contains("OnlyDistance ");
        var classificationForUserAndDistanceTracker = classificationId.contains("User,DistanceTracker ");

        return flowTreeContainsMaliciousFlow && clearanceForTrackingService && classificationForUserAndDistanceTracker;
    }

}
