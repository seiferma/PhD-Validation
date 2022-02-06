package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.models.pcm;

import java.util.Collection;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.CommunicationParadigm;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanism;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.PCMModel;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.PCMModelBase;

@Component(scope = ServiceScope.SINGLETON, service = { PCMModel.class })
public class HealthRecord_MAC_NTK_DF extends PCMModelBase {

    public HealthRecord_MAC_NTK_DF() {
        super(15, "HealthRecord", ConfidentialityMechanism.MAC_NTK, CommunicationParadigm.DATA_FLOW,
                "HealthRecord_DataFlow_MAC_NTK", "newUsageModel.usagemodel", "newUsageModel_withIssue.usagemodel",
                "newAllocation.allocation", "newAllocation.allocation", null, "query.pl");
    }

    @Override
    public boolean isAcceptableViolation(Map<String, Object> violation) {
        var nodeId = (String)violation.get("N");
        @SuppressWarnings("unchecked")
        var compartments = (Collection<String>)violation.get("COMP");
        @SuppressWarnings("unchecked")
        var needsToKnow = (Collection<String>)violation.get("NTK");

        var nodeIsClerk = nodeId.contains("Clerk."); 
        var needsToKnowAreFinancialAndPersonal = needsToKnow.size() == 2 && needsToKnow.stream().anyMatch(s -> s.contains("Personal ")) && needsToKnow.stream().anyMatch(s -> s.contains("Financial "));
        var compartmentsAreMedical = compartments.size() == 1 && compartments.iterator().next().contains("Medical ");
        
        return nodeIsClerk && needsToKnowAreFinancialAndPersonal && compartmentsAreMedical;
    }
}
