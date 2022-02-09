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
public class TravelPlanner_HL_DF extends PCMModelBase {

    public TravelPlanner_HL_DF() {
        super(1, "TravelPlanner", ConfidentialityMechanism.NonInterferenceLinear, CommunicationParadigm.DATA_FLOW,
                "TravelPlanner_DataFlow_HL", "newUsageModel.usagemodel", "newUsageModel_withIssue.usagemodel",
                "newAllocation.allocation", "newAllocation.allocation", null, "query.pl");
    }

    @Override
    public boolean isAcceptableViolation(Map<String, Object> violation) {
        var nodeId = (String) violation.get("P");
        var pinId = (String) violation.get("PIN");
        var clearance = (String) violation.get("V_CLEAR");
        var classification = (String) violation.get("V_LEVEL");

        var isAirlineNode = nodeId.contains("AirlineCreateBooking");
        var isBookingStore = nodeId.contains("BookingStorage");
        var isCcdPin = pinId.contains("ccd ");
        var isUserClassification = classification.contains("User ");
        var isUserAirlineClearance = clearance.contains("User,Airline ");

        return ((isAirlineNode && isCcdPin) || isBookingStore) && isUserClassification && isUserAirlineClearance;
    }

    @Reference(cardinality = ReferenceCardinality.MULTIPLE)
    public void bindPlainPCMModel(PlainPCMModel plainModel) {
        super.bindPlainPCMModel(plainModel);
    }
}
