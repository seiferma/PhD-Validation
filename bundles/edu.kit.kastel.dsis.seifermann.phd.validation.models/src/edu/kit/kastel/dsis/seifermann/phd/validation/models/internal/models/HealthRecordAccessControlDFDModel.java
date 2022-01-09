package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.models;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanism;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.DFDModel;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.DFDModelBase;

@Component(scope = ServiceScope.SINGLETON, service = { DFDModel.class })
public class HealthRecordAccessControlDFDModel extends DFDModelBase {

    public HealthRecordAccessControlDFDModel() {
        super(15, "HealthRecord", ConfidentialityMechanism.MAC_NTK, "mac_needtoknow", "mac_dfd.xmi",
                "mac_dfd_withIssue.xmi", null, "AccessControlQuery.pl", "MAC.svg");
    }

    @Override
    protected boolean isAcceptableViolation(Map<String, Object> violation) {
        // TODO Auto-generated method stub
        return false;
    }

}
