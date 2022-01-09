package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.models;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanism;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.DFDModel;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.DFDModelBase;

@Component(scope = ServiceScope.SINGLETON, service = { DFDModel.class })
public class TravelPlannerInformationFlowDFDModel extends DFDModelBase {

    public TravelPlannerInformationFlowDFDModel() {
        super(1, "TravelPlanner", ConfidentialityMechanism.NonInterferenceLinear, "travelplanner",
                "DFDC_TravelPlanner_InformationFlow.xmi", "DFDC_TravelPlanner_InformationFlow_WithIssue.xmi", null,
                "InformationFlowQuery.pl", "TravelPlanner_InformationFlow.svg");
    }

    @Override
    protected boolean isAcceptableViolation(Map<String, Object> violation) {
        // TODO Auto-generated method stub
        return false;
    }

}
