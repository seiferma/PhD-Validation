package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.models.pcm;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.CommunicationParadigm;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.PlainPCMModel;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.PlainPCMModelImpl;

@Component(scope = ServiceScope.SINGLETON, service = PlainPCMModel.class)
public class ContactSMS_Plain_CF extends PlainPCMModelImpl {

    public ContactSMS_Plain_CF() {
        super("ContactSMS", CommunicationParadigm.CONTROL_FLOW, "ContactSMS_CallReturn_Plain",
                "newUsageModel.usagemodel", "newAllocation.allocation");
    }

}
