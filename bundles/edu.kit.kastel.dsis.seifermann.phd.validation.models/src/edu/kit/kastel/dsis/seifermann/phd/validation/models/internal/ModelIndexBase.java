package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.Model;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.ModelIndex;

public class ModelIndexBase<T extends Model> implements ModelIndex<T> {

    private final Collection<T> models = new ArrayList<>();

    public void bindModel(T model, Map<String, String> serviceProperties) {
        models.add(model);
    }

    public void unbindModel(T model) {
        models.remove(model);
    }

    public void updateModel(T model, Map<String, String> serviceProperties) {
        unbindModel(model);
        bindModel(model, serviceProperties);
    }

    @Override
    public Collection<T> getModelList(Predicate<T> filterPredicate) {
        return models.stream()
            .filter(filterPredicate::test)
            .collect(Collectors.toList());
    }

}
