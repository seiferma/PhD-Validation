package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.models;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanism;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.DFDModel;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.DFDModelBase;

@Component(scope = ServiceScope.SINGLETON, service = { DFDModel.class })
public class HospitalInformationFlowDFDModel extends DFDModelBase {

    public HospitalInformationFlowDFDModel() {
        super(7, "Hospital", ConfidentialityMechanism.NonInterferenceLinearWithEncryption, "hospital",
                "DFDC_HospitalAlternative_Linear.xmi", "DFDC_HospitalLoopful_Linear.xmi", null,
                "InformationFlowQuery.pl", "Hospital.svg");
    }

    @Override
    public boolean isAcceptableViolation(Map<String, Object> violation) {
        var nodeId = violation.get("P")
            .toString();
        var classificationId = violation.get("V_LEVEL")
            .toString();
        var clearanceId = violation.get("V_CLEAR")
            .toString();

        var nodeBelongsToAttacker = nodeId.contains("Attacker ");
        var clearanceForLow = clearanceId.contains("Low ");
        var classificationForHigh = classificationId.contains("High ");

        return nodeBelongsToAttacker && clearanceForLow && classificationForHigh;
    }

}
