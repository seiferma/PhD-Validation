package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.models.pcm;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.CommunicationParadigm;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanism;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.PCMModel;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.PCMModelBase;

@Component(scope = ServiceScope.SINGLETON, service = { PCMModel.class })
public class ImageSharing_DAC_CF extends PCMModelBase {

    public ImageSharing_DAC_CF() {
        super(13, "ImageSharing", ConfidentialityMechanism.DAC, CommunicationParadigm.CONTROL_FLOW,
                "ImageSharing_CallReturn_DAC", "newUsageModel.usagemodel", "newUsageModel_withIssue.usagemodel",
                "newAllocation.allocation", "newAllocation_withIssue.allocation", "additionalClauses.pl", "query.pl");
    }

    @Override
    public boolean isAcceptableViolation(Map<String, Object> violation) {
        var storeId = (String) violation.get("STORE");
        var actorId = (String) violation.get("A");

        var isImageStore = storeId.contains("ImageStorage ");
        var isIndexingBot = actorId.contains("IndexingBot");
        
        return isImageStore && isIndexingBot;
    }

}
