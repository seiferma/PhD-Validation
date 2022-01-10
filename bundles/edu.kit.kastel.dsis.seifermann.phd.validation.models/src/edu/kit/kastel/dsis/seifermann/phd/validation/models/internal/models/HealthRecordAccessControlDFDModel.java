package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.models;

import java.util.Collection;
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
    @SuppressWarnings("unchecked")
    public boolean isAcceptableViolation(Map<String, Object> violation) {
        var processId = violation.get("N")
            .toString();
        var compartmentIds = (Collection<String>) violation.get("COMP");
        var needsToKnowIds = (Collection<String>) violation.get("NTK");
        var flowTree = (Collection<Object>) violation.get("S");
        var flattenedFlowTree = flattenFlowTree(flowTree);

        var flowTreeContainsMaliciousFlow = flattenedFlowTree.stream()
            .anyMatch(id -> id.contains("_YH0QUG7OEey4gY6fX422dQ"));
        var isInvoiceProcess = processId.contains("create invoice ");
        var compartmentIsMedicalOnly = compartmentIds.size() == 1 && compartmentIds.iterator()
            .next()
            .contains("Medical ");
        var needsToKnowDoesNotContainMedical = needsToKnowIds.stream()
            .noneMatch(id -> id.contains("Medical "));

        return flowTreeContainsMaliciousFlow && isInvoiceProcess && compartmentIsMedicalOnly
                && needsToKnowDoesNotContainMedical;
    }

}
