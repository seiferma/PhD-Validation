package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.models;

import java.util.Collection;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanism;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.DFDModel;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.DFDModelBase;

@Component(scope = ServiceScope.SINGLETON, service = { DFDModel.class })
public class WebRTCInformationFlowDFDModel extends DFDModelBase {

    public WebRTCInformationFlowDFDModel() {
        super(9, "WebRTC", ConfidentialityMechanism.NonInterferenceLinearWithEncryption, "webrtc",
                "DFDC_WebRTC_Linear.xmi", "DFDC_WebRTC_Linear_WithIssue.xmi", null, "InformationFlowQuery.pl",
                "WebRTC.svg");
    }

    @Override
    public boolean isAcceptableViolation(Map<String, Object> violation) {
        var nodeId = violation.get("P")
            .toString();
        var classificationId = violation.get("V_LEVEL")
            .toString();
        var clearanceId = violation.get("V_CLEAR")
            .toString();
        @SuppressWarnings("unchecked")
        var flowTree = (Collection<Object>) violation.get("S");
        var flattenedFlowTree = flattenFlowTree(flowTree);

        var flowTreeContainsMaliciousFlow = flattenedFlowTree.stream()
            .anyMatch(flow -> flow.contains("direct flow"));
        var nodeBelongsToSignalingServer = nodeId.contains("SignalingServer.");
        var clearanceForLow = clearanceId.contains("Low ");
        var classificationForHigh = classificationId.contains("High ");

        return flowTreeContainsMaliciousFlow && nodeBelongsToSignalingServer && clearanceForLow && classificationForHigh;
    }

}
