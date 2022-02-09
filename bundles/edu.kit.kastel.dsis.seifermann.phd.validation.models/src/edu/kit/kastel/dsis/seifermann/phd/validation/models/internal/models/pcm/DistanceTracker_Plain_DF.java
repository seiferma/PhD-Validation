package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.models.pcm;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.CommunicationParadigm;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.PlainPCMModel;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.PlainPCMModelImpl;

@Component(scope = ServiceScope.SINGLETON, service = PlainPCMModel.class)
public class DistanceTracker_Plain_DF extends PlainPCMModelImpl {

    public DistanceTracker_Plain_DF() {
        super("DistanceTracker", CommunicationParadigm.DATA_FLOW, "DistanceTracker_DataFlow_Plain",
                "newUsageModel.usagemodel", "newAllocation.allocation");
    }

}
