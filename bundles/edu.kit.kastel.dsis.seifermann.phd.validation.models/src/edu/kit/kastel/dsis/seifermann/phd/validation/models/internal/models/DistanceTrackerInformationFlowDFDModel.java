package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.models;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanism;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.DFDModel;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.DFDModelBase;

@Component(scope = ServiceScope.SINGLETON, service = { DFDModel.class })
public class DistanceTrackerInformationFlowDFDModel extends DFDModelBase {

    public DistanceTrackerInformationFlowDFDModel() {
        super(2, "DistanceTracker", ConfidentialityMechanism.NonInterferenceLinear, "distancetracker",
                "DFDC_DistanceTracker_InformationFlow.xmi", "DFDC_DistanceTracker_InformationFlow_WithIssue.xmi", null,
                "InformationFlowQuery.pl", "DistanceTracker-InformationFlow.svg");
    }

    @Override
    protected boolean isAcceptableViolation(Map<String, Object> violation) {
        // TODO Auto-generated method stub
        return false;
    }

}
