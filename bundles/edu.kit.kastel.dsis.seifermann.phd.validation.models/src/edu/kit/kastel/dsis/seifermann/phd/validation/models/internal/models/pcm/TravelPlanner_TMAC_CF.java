package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.models.pcm;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ServiceScope;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.CommunicationParadigm;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanism;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.PCMModel;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.PlainPCMModel;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.PCMModelBase;

@Component(scope = ServiceScope.SINGLETON, service = { PCMModel.class })
public class TravelPlanner_TMAC_CF extends PCMModelBase {

    public TravelPlanner_TMAC_CF() {
        super(17, "TravelPlanner", ConfidentialityMechanism.RBAC_TAINT, CommunicationParadigm.CONTROL_FLOW,
                "TravelPlanner_CallReturn_TMAC", "newUsageModel.usagemodel", "newUsageModel_withIssue.usagemodel",
                "newAllocation.allocation", "newAllocation_withIssue.allocation", null, "query.pl");
    }

    @Override
    public boolean isAcceptableViolation(Map<String, Object> violation) {
        var nodeId = (String) violation.get("P");
        var pinId = (String) violation.get("PIN");
        var criticalityId = (String) violation.get("C");
        var validationId = (String) violation.get("V");

        var isFindFlights = nodeId.contains("findFlights");
        var isCriteria = pinId.contains("criteria ");
        var isQuery = pinId.contains("query ");
        var isHighCriticality = criticalityId.contains("High ");
        var isNotValidated = validationId.contains("NotValidated ");

        return isFindFlights && (isCriteria || isQuery) && isHighCriticality && isNotValidated;
    }

    @Reference(cardinality = ReferenceCardinality.MULTIPLE)
    public void bindPlainPCMModel(PlainPCMModel plainModel) {
        super.bindPlainPCMModel(plainModel);
    }
}
