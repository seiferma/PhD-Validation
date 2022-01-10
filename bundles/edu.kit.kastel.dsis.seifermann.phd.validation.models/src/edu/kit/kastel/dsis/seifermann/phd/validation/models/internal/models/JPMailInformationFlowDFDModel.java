package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.models;

import java.util.Collection;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanism;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.DFDModel;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.DFDModelBase;

@Component(scope = ServiceScope.SINGLETON, service = { DFDModel.class })
public class JPMailInformationFlowDFDModel extends DFDModelBase {

    public JPMailInformationFlowDFDModel() {
        super(8, "JPMail", ConfidentialityMechanism.NonInterferenceLinearWithEncryption, "jpmail",
                "DFDC_JPMail_Linear.xmi", "DFDC_JPMail_Linear_WithIssue.xmi", null, "InformationFlowQuery.pl",
                "JPMail.svg");
    }

    @Override
    public boolean isAcceptableViolation(Map<String, Object> violation) {
        var nodeId = violation.get("P")
            .toString();
        var classificationId = violation.get("V_LEVEL")
            .toString();
        var clearanceId = violation.get("V_CLEAR")
            .toString();
        @SuppressWarnings("unchecked")
        var flowTree = (Collection<Object>) violation.get("S");
        var flattenedFlowTree = flattenFlowTree(flowTree);

        var flowTreeContainsMaliciousFlow = flattenedFlowTree.stream()
            .anyMatch(flow -> flow.contains("direct flow"));
        var nodeIsMailServer = nodeId.contains("SMTP ") || nodeId.contains("POP3 ");
        var clearanceForLow = clearanceId.contains("Low ");
        var classificationForHigh = classificationId.contains("High ");

        return flowTreeContainsMaliciousFlow && nodeIsMailServer && clearanceForLow && classificationForHigh;
    }

}
