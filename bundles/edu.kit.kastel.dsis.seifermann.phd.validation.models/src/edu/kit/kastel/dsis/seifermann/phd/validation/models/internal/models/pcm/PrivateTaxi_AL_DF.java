package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.models.pcm;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.CommunicationParadigm;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanism;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.PCMModel;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.PCMModelBase;

@Component(scope = ServiceScope.SINGLETON, service = { PCMModel.class })
public class PrivateTaxi_AL_DF extends PCMModelBase {

    public PrivateTaxi_AL_DF() {
        super(4, "PrivateTaxi", ConfidentialityMechanism.NonInterferenceArbitraryWithEncryption,
                CommunicationParadigm.DATA_FLOW, "PrivateTaxi_DataFlow_AL", "newUsageModel.usagemodel",
                "newUsageModel_withIssue.usagemodel", "newAllocation.allocation", "newAllocation.allocation",
                "additionalClauses.pl", "query.pl");
    }

    @Override
    public boolean isAcceptableViolation(Map<String, Object> violation) {
        var providedKeys = (String) violation.get("PROV");
        var requiredKeys = (String) violation.get("REQ");
        var keysAreUnset = providedKeys.matches("_[0-9]+") && requiredKeys.matches("_[0-9]+");

        var nodeId = (String) violation.get("N");
        var pinId = (String) violation.get("PIN");
        var clearance = (String) violation.get("V_CLEAR");
        var classification = (String) violation.get("V_CLASS");

        var isPrivateTaxiNode = nodeId.contains("PrivateTaxiLogic") || nodeId.contains("PrivateTaxisLogic");
        var isRouteStorage = nodeId.contains("Assembly_RouteStorage");
        var isRouteMultiplexer = nodeId.contains("RouteMultiplexer");
        var isRoutePin = pinId.contains("route ") || pinId.contains("Route ");
        var isReturnPin = pinId.contains("RETURN ");
        var isRouteClassification = classification.contains("Route ");
        var isPrivateTaxiClearance = clearance.contains("PrivateTaxi ");

        return (isRouteStorage || ((isRouteMultiplexer || isPrivateTaxiNode) && (isRoutePin || isReturnPin)))
                && isRouteClassification && isPrivateTaxiClearance && keysAreUnset;
    }

}
