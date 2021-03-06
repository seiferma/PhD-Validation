package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.models.dfd;

import java.util.Collection;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanism;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.DFDModel;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.DFDModelBase;

@Component(scope = ServiceScope.SINGLETON, service = { DFDModel.class })
public class ImageSharingAccessControlDFDModel extends DFDModelBase {

    public ImageSharingAccessControlDFDModel() {
        super(13, "ImageSharing", ConfidentialityMechanism.DAC, "dac_delegation", "dac_dfd.xmi",
                "dac_dfd_withIssue.xmi", "AccessControlRules.pl", "AccessControlQuery.pl", "DAC.svg");
    }

    @Override
    public boolean isAcceptableViolation(Map<String, Object> violation) {
        var actorId = violation.get("A")
            .toString();
        var storeId = violation.get("STORE")
            .toString();
        @SuppressWarnings("unchecked")
        var flowTree = (Collection<Object>) violation.get("S");
        var flattenedFlowTree = flattenFlowTree(flowTree);

        var isAunt = actorId.contains("Aunt ");
        var isFamilityPicturesStore = storeId.contains("Family Pictures ");
        var flowToActorContainsPictures = flattenedFlowTree.iterator()
            .next()
            .contains("pictures ");

        return isAunt && isFamilityPicturesStore && flowToActorContainsPictures;
    }

}
