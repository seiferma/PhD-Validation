package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.models.dfd;

import java.util.Collection;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanism;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.DFDModel;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.DFDModelBase;

@Component(scope = ServiceScope.SINGLETON, service = { DFDModel.class })
public class ContactSMSInformationFlowDFDModel extends DFDModelBase {

    public ContactSMSInformationFlowDFDModel() {
        super(3, "ContactSMS", ConfidentialityMechanism.NonInterferenceLinear, "contactsms",
                "DFDC_ContactSMS_InformationFlow.xmi", "DFDC_ContactSMS_InformationFlow_WithIssue.xmi", null,
                "InformationFlowQuery.pl", "ContactSMS-InformationFlow.svg");
    }

    @Override
    public boolean isAcceptableViolation(Map<String, Object> violation) {
        var classificationId = violation.get("V_LEVEL")
            .toString();
        var clearanceId = violation.get("V_CLEAR")
            .toString();
        @SuppressWarnings("unchecked")
        var flowTree = (Collection<Object>) violation.get("S");
        var flattenedFlowTree = flattenFlowTree(flowTree);

        var flowTreeContainsMaliciousFlow = flattenedFlowTree.stream()
            .anyMatch(flow -> flow.contains("contact direct"));
        var clearanceForUserAndReceiver = clearanceId.contains("User,Receiver ");
        var classificationForUser = classificationId.contains("User ");

        return flowTreeContainsMaliciousFlow && clearanceForUserAndReceiver && classificationForUser;
    }

}
