package edu.kit.kastel.dsis.seifermann.phd.validation.models;

import java.util.Collection;
import java.util.function.Predicate;

public interface ModelIndex<T extends Model> {

    public default Collection<T> getModelList() {
        return getModelList(m -> true);
    }

    public Collection<T> getModelList(Predicate<T> filterPredicate);
}
