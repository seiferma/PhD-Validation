package edu.kit.kastel.dsis.seifermann.phd.validation.models;

import java.util.Collection;
import java.util.function.Predicate;

public interface DFDModelIndex {

    public default Collection<DFDModel> getModelList() {
        return getModelList(m -> true);
    }
    
    public Collection<DFDModel> getModelList(Predicate<DFDModel> filterPredicate);
    
}
