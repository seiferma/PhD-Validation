package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.models;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanism;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.DFDModel;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.DFDModelBase;

@Component(scope = ServiceScope.SINGLETON, service = { DFDModel.class })
public class WebRTCInformationFlowDFDModel extends DFDModelBase {

    public WebRTCInformationFlowDFDModel() {
        super(9, "WebRTC", ConfidentialityMechanism.NonInterferenceLinearWithEncryption, "webrtc",
                "DFDC_WebRTC_Linear.xmi", "DFDC_WebRTC_Linear_WithIssue.xmi", null, "InformationFlowQuery.pl",
                "WebRTC.svg");
    }

    @Override
    protected boolean isAcceptableViolation(Map<String, Object> violation) {
        // TODO Auto-generated method stub
        return false;
    }

}
