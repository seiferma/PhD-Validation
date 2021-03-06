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
public class DistanceTracker_HL_DF extends PCMModelBase {

    public DistanceTracker_HL_DF() {
        super(2, "DistanceTracker", ConfidentialityMechanism.NonInterferenceLinear, CommunicationParadigm.DATA_FLOW,
                "DistanceTracker_DataFlow_HL", "newUsageModel.usagemodel", "newUsageModel_withIssue.usagemodel",
                "newAllocation.allocation", "newAllocation_withIssue.allocation", null, "query.pl");
    }

    @Override
    public boolean isAcceptableViolation(Map<String, Object> violation) {
        var nodeId = (String) violation.get("P");
        var clearance = (String) violation.get("V_CLEAR");
        var classification = (String) violation.get("V_LEVEL");

        var isTrackingService = nodeId.contains("Assembly_TrackingService") || nodeId.contains("TrackingServiceLogic");
        var isUserDistanceTrackerClassification = classification.contains("User,DistanceTracker ");
        var isOnlyDistanceClearance = clearance.contains("OnlyDistance ");

        return isTrackingService && isUserDistanceTrackerClassification && isOnlyDistanceClearance;
    }

    @Reference(cardinality = ReferenceCardinality.MULTIPLE)
    public void bindPlainPCMModel(PlainPCMModel plainModel) {
        super.bindPlainPCMModel(plainModel);
    }
}
