package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ServiceScope;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.DFDModel;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.DFDModelIndex;

@Component(scope = ServiceScope.SINGLETON)
public class DFDModelIndexImpl implements DFDModelIndex {

    private final Collection<DFDModel> dfdModels = new ArrayList<>();

    @Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
    public void bindDFDModel(DFDModel model, Map<String, String> serviceProperties) {
        dfdModels.add(model);
    }

    public void unbindDFDModel(DFDModel model) {
        dfdModels.remove(model);
    }

    public void updatedProverFactory(DFDModel model, Map<String, String> serviceProperties) {
        unbindDFDModel(model);
        bindDFDModel(model, serviceProperties);
    }

    @Override
    public Collection<DFDModel> getModelList(Predicate<DFDModel> filterPredicate) {
        return dfdModels.stream()
            .filter(filterPredicate::test)
            .collect(Collectors.toList());
    }

}
