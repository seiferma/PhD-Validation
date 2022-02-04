package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.models.pcm;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.CommunicationParadigm;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanism;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.PCMModel;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.PCMModelBase;

@Component(scope = ServiceScope.SINGLETON, service = { PCMModel.class })
public class ContactSMS_HL_CF extends PCMModelBase {

    public ContactSMS_HL_CF() {
        super(3, "ContactSMS", ConfidentialityMechanism.NonInterferenceLinear, CommunicationParadigm.CONTROL_FLOW,
                "ContactSMS_CallReturn_HL", "newUsageModel.usagemodel", "newUsageModel_withIssue.usagemodel",
                "newAllocation.allocation", "newAllocation_withIssue.allocation", null, "query.pl");
    }

    @Override
    public boolean isAcceptableViolation(Map<String, Object> violation) {
        var nodeId = (String)violation.get("P");
        var pinId = (String)violation.get("PIN");
        var clearance = (String)violation.get("V_CLEAR");
        var classification = (String)violation.get("V_LEVEL");
        
        var isSMSGateway = nodeId.contains("Assembly_SMSGateway");
        var isNumberPin = pinId.contains("number ");
        var isUserClassification = classification.contains("User ");
        var isUserReceiverClearance = clearance.contains("User,Receiver ");
        
        return isSMSGateway && isNumberPin && isUserClassification && isUserReceiverClearance;
    }

}
