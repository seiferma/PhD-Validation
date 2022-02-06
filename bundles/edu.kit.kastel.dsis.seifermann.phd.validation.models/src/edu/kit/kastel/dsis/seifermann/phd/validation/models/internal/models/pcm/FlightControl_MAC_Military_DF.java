package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.models.pcm;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.CommunicationParadigm;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanism;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.PCMModel;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.PCMModelBase;

@Component(scope = ServiceScope.SINGLETON, service = { PCMModel.class })
public class FlightControl_MAC_Military_DF extends PCMModelBase {

    public FlightControl_MAC_Military_DF() {
        super(14, "FlightControl", ConfidentialityMechanism.MAC_Military, CommunicationParadigm.DATA_FLOW,
                "FlightControl_DataFlow_MAC_Military", "newUsageModel.usagemodel", "newUsageModel_withIssue.usagemodel",
                "newAllocation.allocation", "newAllocation_withIssue.allocation", null, "query.pl");
    }

    @Override
    public boolean isAcceptableViolation(Map<String, Object> violation) {
        var nodeId = (String) violation.get("P");
        var clearance = (String) violation.get("V_CLEAR");
        var classification = (String) violation.get("V_LEVEL");

        var nodeIsCivilFlightPlanner = nodeId.contains("CivilFlightPlanner") || nodeId.contains("RouteControllerFacade")
                || nodeId.contains("PlaneRouteCreator") || nodeId.contains("PlaneListCreator")
                || nodeId.contains("PlaneMultiplexer");
        var classificationIsMilitary = classification.contains("Military ");
        var clearanceIsCivil = clearance.contains("Civil ");

        return nodeIsCivilFlightPlanner && classificationIsMilitary && clearanceIsCivil;
    }
}
