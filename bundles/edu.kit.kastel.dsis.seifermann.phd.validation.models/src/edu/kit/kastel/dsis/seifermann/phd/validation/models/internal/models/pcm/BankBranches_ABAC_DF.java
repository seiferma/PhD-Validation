package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.models.pcm;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.CommunicationParadigm;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanism;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.PCMModel;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.PCMModelBase;

@Component(scope = ServiceScope.SINGLETON, service = { PCMModel.class })
public class BankBranches_ABAC_DF extends PCMModelBase {

    public BankBranches_ABAC_DF() {
        super(16, "BankBranches", ConfidentialityMechanism.ABAC, CommunicationParadigm.DATA_FLOW,
                "BankBranches_DataFlow_ABAC", "newUsageModel.usagemodel", "newUsageModel_withIssue.usagemodel",
                "newAllocation.allocation", "newAllocation.allocation", "additionalClauses.pl", "query.pl");
    }

    @Override
    public boolean isAcceptableViolation(Map<String, Object> violation) {
        var nodeId = (String) violation.get("A");

        var isFindCustomerActionUSA = nodeId.contains("UserAction Exit ClerkUSA.findCustomer.RegularUSA");

        return isFindCustomerActionUSA;
    }
}
