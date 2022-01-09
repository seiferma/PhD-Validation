package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.models;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanism;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.DFDModel;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.DFDModelBase;

@Component(scope = ServiceScope.SINGLETON, service = { DFDModel.class })
public class TravelPlannerCombinedDFDModel extends DFDModelBase {

    public TravelPlannerCombinedDFDModel() {
        super(17, "TravelPlanner", ConfidentialityMechanism.RBAC_TAINT, "travelplanner", "DFDC_TravelPlanner_TMAC.xmi",
                "DFDC_TravelPlanner_TMAC_WithIssue.xmi", null, "TMACQuery.pl", "TravelPlanner_TMAC.svg");
    }

    @Override
    protected boolean isAcceptableViolation(Map<String, Object> violation) {
        // TODO Auto-generated method stub
        return false;
    }

}
