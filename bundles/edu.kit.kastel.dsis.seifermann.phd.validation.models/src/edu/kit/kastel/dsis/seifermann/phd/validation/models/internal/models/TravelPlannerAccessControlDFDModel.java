package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.models;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanism;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.DFDModel;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.DFDModelBase;

@Component(scope = ServiceScope.SINGLETON, service = { DFDModel.class })
public class TravelPlannerAccessControlDFDModel extends DFDModelBase {

    public TravelPlannerAccessControlDFDModel() {
        super(10, "TravelPlanner", ConfidentialityMechanism.RBAC, "travelplanner",
                "DFDC_TravelPlanner_AccessControl.xmi", "DFDC_TravelPlanner_AccessControl_WithIssue.xmi", null,
                "AccessControlQuery.pl", "TravelPlanner_AccessControl.svg");
    }

    @Override
    protected boolean isAcceptableViolation(Map<String, Object> violation) {
        // TODO Auto-generated method stub
        return false;
    }

}
