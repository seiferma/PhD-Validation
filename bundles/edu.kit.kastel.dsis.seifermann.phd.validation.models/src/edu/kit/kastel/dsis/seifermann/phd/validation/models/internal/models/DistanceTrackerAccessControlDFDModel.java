package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.models;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanism;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.DFDModel;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.DFDModelBase;

@Component(scope = ServiceScope.SINGLETON, service = { DFDModel.class })
public class DistanceTrackerAccessControlDFDModel extends DFDModelBase {

    public DistanceTrackerAccessControlDFDModel() {
        super(11, "DistanceTracker", ConfidentialityMechanism.RBAC, "distancetracker",
                "DFDC_DistanceTracker_AccessControl.xmi", "DFDC_DistanceTracker_AccessControl_Withissue.xmi",
                null, "AccessControlQuery.pl", "DistanceTracker-AccessControl.svg");
    }

    @Override
    protected boolean isAcceptableViolation(Map<String, Object> violation) {
        // TODO Auto-generated method stub
        return false;
    }

}
