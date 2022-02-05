package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.models.pcm;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.CommunicationParadigm;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanism;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.PCMModel;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.PCMModelBase;

@Component(scope = ServiceScope.SINGLETON, service = { PCMModel.class })
public class WebRTC_HL_DF extends PCMModelBase {

    public WebRTC_HL_DF() {
        super(9, "WebRTC", ConfidentialityMechanism.NonInterferenceLinearWithEncryption,
                CommunicationParadigm.DATA_FLOW, "WebRTC_DataFlow_HL", "newUsageModel.usagemodel",
                "newUsageModel_withIssue.usagemodel", "newAllocation.allocation", "newAllocation_withIssue.allocation",
                null, "query.pl");
    }

    @Override
    public boolean isAcceptableViolation(Map<String, Object> violation) {
        var nodeId = (String) violation.get("P");
        var clearance = (String) violation.get("V_CLEAR");
        var classification = (String) violation.get("V_LEVEL");

        var isSignaling = nodeId.contains("Signaling");
        var isHighClassification = classification.contains("High ");
        var isAttackClearance = clearance.contains("Attack ");

        return isSignaling && isHighClassification && isAttackClearance;
    }

}
