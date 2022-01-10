package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.models;

import java.util.Collection;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanism;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.DFDModel;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.DFDModelBase;

@Component(scope = ServiceScope.SINGLETON, service = { DFDModel.class })
public class FlightControlAccessControlDFDModel extends DFDModelBase {

    public FlightControlAccessControlDFDModel() {
        super(14, "FlightControl", ConfidentialityMechanism.MAC_Military, "mac", "mac_dfd.xmi",
                "mac_dfd_readViolation.xmi", null, "AccessControlQuery.pl", "MAC-ReadViolation.svg");
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
            .anyMatch(flow -> flow.contains("_SNgf-9SkEeqakq6offlGZg"));
        var belongsToFlightController = processId.contains("FlightController.")
                || processId.contains("Flight Controller ");
        var clearanceForClassified = clearanceId.contains("Classified ");
        var classificationForSecret = classificationId.contains("Secret ");

        return flowTreeContainsMaliciousFlow && belongsToFlightController && clearanceForClassified
                && classificationForSecret;
    }

}
