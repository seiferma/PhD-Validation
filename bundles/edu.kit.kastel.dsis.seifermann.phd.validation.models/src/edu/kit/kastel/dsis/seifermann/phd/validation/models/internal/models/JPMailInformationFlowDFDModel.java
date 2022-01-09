package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.models;

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
    protected boolean isAcceptableViolation(Map<String, Object> violation) {
        // TODO Auto-generated method stub
        return false;
    }

}
