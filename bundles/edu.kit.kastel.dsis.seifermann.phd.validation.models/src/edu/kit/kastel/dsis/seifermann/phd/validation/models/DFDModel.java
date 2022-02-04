package edu.kit.kastel.dsis.seifermann.phd.validation.models;

import java.net.URL;

import org.eclipse.emf.common.util.URI;

public interface DFDModel extends Model {

    URI getDfdWithViolation();

    URI getDfdWithoutViolation();

    URL getVisualizationLocation();

    @Override
    default boolean hasModel() {
        return getDfdWithoutViolation() != null;
    }

}
