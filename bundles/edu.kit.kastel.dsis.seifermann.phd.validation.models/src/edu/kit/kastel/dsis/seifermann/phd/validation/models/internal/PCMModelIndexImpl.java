package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ServiceScope;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.PCMModel;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.PCMModelIndex;

@Component(scope = ServiceScope.SINGLETON)
public class PCMModelIndexImpl extends ModelIndexBase<PCMModel> implements PCMModelIndex {

    @Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
    public void bindModel(PCMModel model, Map<String, String> serviceProperties) {
        super.bindModel(model, serviceProperties);
    }

    public void unbindModel(PCMModel model) {
        super.unbindModel(model);
    }
}
