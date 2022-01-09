package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.models;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanism;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.DFDModel;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.DFDModelBase;

@Component(scope = ServiceScope.SINGLETON, service = { DFDModel.class })
public class BankBranchesAccessControlDFDModel extends DFDModelBase {

    public BankBranchesAccessControlDFDModel() {
        super(16, "BankBranches", ConfidentialityMechanism.ABAC, "abac", "abac_dfd.xmi",
                "abac_dfd_withIssue.xmi", "AccessControlRules.pl", "AccessControlQuery.pl", "ABAC.svg");
    }

    @Override
    protected boolean isAcceptableViolation(Map<String, Object> violation) {
        // TODO Auto-generated method stub
        return false;
    }

}
