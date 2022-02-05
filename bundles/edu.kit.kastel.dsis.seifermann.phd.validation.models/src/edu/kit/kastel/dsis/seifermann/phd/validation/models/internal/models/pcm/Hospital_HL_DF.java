package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.models.pcm;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.CommunicationParadigm;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanism;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.PCMModel;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.PCMModelBase;

@Component(scope = ServiceScope.SINGLETON, service = { PCMModel.class })
public class Hospital_HL_DF extends PCMModelBase {

    public Hospital_HL_DF() {
        super(7, "Hospital", ConfidentialityMechanism.NonInterferenceLinearWithEncryption,
                CommunicationParadigm.DATA_FLOW, "Hospital_DataFlow_HL", "newUsageModel.usagemodel",
                "newUsageModel_withIssue.usagemodel", "newAllocation.allocation", "newAllocation_withIssue.allocation",
                null, "query.pl");
    }

    @Override
    public boolean isAcceptableViolation(Map<String, Object> violation) {
        var nodeId = (String) violation.get("P");
        var clearance = (String) violation.get("V_CLEAR");
        var classification = (String) violation.get("V_LEVEL");

        var isAttacker = nodeId.contains("Attacker");
        var isHighClassification = classification.contains("High ");
        var isAttackClearance = clearance.contains("Attack ");

        return isAttacker && isHighClassification && isAttackClearance;
    }

}
