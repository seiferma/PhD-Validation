package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ServiceScope;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.DFDModel;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.DFDModelIndex;

@Component(scope = ServiceScope.SINGLETON)
public class DFDModelIndexImpl extends ModelIndexBase<DFDModel> implements DFDModelIndex {

    @Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
    public void bindModel(DFDModel model, Map<String, String> serviceProperties) {
        super.bindModel(model, serviceProperties);
    }

    public void unbindModel(DFDModel model) {
        super.unbindModel(model);
    }
}
