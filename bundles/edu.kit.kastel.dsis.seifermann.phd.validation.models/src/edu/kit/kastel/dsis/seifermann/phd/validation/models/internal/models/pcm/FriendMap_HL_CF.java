package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.models.pcm;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.CommunicationParadigm;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanism;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.PCMModel;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.PCMModelBase;

@Component(scope = ServiceScope.SINGLETON, service = { PCMModel.class })
public class FriendMap_HL_CF extends PCMModelBase {

    public FriendMap_HL_CF() {
        super(6, "FriendMap", ConfidentialityMechanism.NonInterferenceLinearWithEncryption,
                CommunicationParadigm.CONTROL_FLOW, "FriendMap_CallReturn_HL", "newUsageModel.usagemodel",
                "newUsageModel_withIssue.usagemodel", "newAllocation.allocation", "newAllocation_withIssue.allocation",
                null, "query.pl");
    }

    @Override
    public boolean isAcceptableViolation(Map<String, Object> violation) {
        var nodeId = (String) violation.get("P");
        var clearance = (String) violation.get("V_CLEAR");
        var classification = (String) violation.get("V_LEVEL");

        var isGoogle = nodeId.contains("Google");
        var isHighClassification = classification.contains("High ");
        var isAttackClearance = clearance.contains("Attack ");

        return isGoogle && isHighClassification && isAttackClearance;
    }

}
