package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.models.pcm;

import java.util.Collection;
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
public class TravelPlanner_RBAC_DF extends PCMModelBase {

    public TravelPlanner_RBAC_DF() {
        super(10, "TravelPlanner", ConfidentialityMechanism.RBAC, CommunicationParadigm.DATA_FLOW,
                "TravelPlanner_DataFlow_RBAC", "newUsageModel.usagemodel", "newUsageModel_withIssue.usagemodel",
                "newAllocation.allocation", "newAllocation.allocation", null, "query.pl");
    }

    @Override
    public boolean isAcceptableViolation(Map<String, Object> violation) {
        var nodeId = (String) violation.get("N");
        var pinId = (String) violation.get("PIN");
        @SuppressWarnings("unchecked")
        var assignedRoleIds = (Collection<String>) violation.get("ROLES");
        @SuppressWarnings("unchecked")
        var requiredRoleIds = (Collection<String>) violation.get("REQ");

        var isBookingNode = nodeId.contains("AirlineCreateBooking");
        var isBookingStorageNode = nodeId.contains("BookingStorage ");
        var isOnlyAirlineRoleAssigned = assignedRoleIds.size() == 1 && assignedRoleIds.iterator()
            .next()
            .contains("Airline ");
        var isOnlyUserRoleRequired = requiredRoleIds.size() == 1 && requiredRoleIds.iterator()
            .next()
            .contains("User ");
        var isCcdPin = pinId.contains("ccd ");

        return ((isBookingNode && isCcdPin) || isBookingStorageNode) && isOnlyAirlineRoleAssigned
                && isOnlyUserRoleRequired;
    }

    @Reference(cardinality = ReferenceCardinality.MULTIPLE)
    public void bindPlainPCMModel(PlainPCMModel plainModel) {
        super.bindPlainPCMModel(plainModel);
    }
}
