package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.models.dfd;

import java.util.Collection;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanism;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.DFDModel;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.DFDModelBase;

@Component(scope = ServiceScope.SINGLETON, service = { DFDModel.class })
public class PrivateTaxiInformationFlowDFDModel extends DFDModelBase {

    public PrivateTaxiInformationFlowDFDModel() {
        super(4, "PrivateTaxi", ConfidentialityMechanism.NonInterferenceArbitraryWithEncryption, "privatetaxi",
                "privatetaxi_arbitrary_dfd.xmi", "privatetaxi_arbitrary_dfd_withIssue.xmi", "InformationFlowRules.pl",
                "InformationFlowQuery.pl", "PrivateTaxi.svg");
    }

    @Override
    public boolean isAcceptableViolation(Map<String, Object> violation) {
        var nodeId = violation.get("N")
            .toString();
        var classificationId = violation.get("V_CLASS")
            .toString();
        var clearanceId = violation.get("V_CLEAR")
            .toString();
        @SuppressWarnings("unchecked")
        var flowTree = (Collection<Object>) violation.get("S");
        var flattenedFlowTree = flattenFlowTree(flowTree);

        var flowTreeContainsMaliciousFlow = flattenedFlowTree.stream()
            .anyMatch(flow -> flow.contains("route direct"));
        var nodeBelongsToTaxiService = nodeId.contains("Taxi.");
        var clearanceForTaxi = clearanceId.contains("PrivateTaxi ");
        var classificationAsRoute = classificationId.contains("Route ");

        return flowTreeContainsMaliciousFlow && nodeBelongsToTaxiService && clearanceForTaxi && classificationAsRoute;
    }

}
