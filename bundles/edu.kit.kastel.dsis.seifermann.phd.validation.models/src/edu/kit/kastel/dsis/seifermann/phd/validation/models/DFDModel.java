package edu.kit.kastel.dsis.seifermann.phd.validation.models;

import java.net.URL;
import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.common.util.URI;

public interface DFDModel {

    URL getQueryRulesLocation();

    URL getQueryLocation();

    boolean hasModel();

    boolean hasQuery();
    
    boolean isAcceptableViolation(Map<String, Object> violation);

    boolean areViolationsAcceptable(Collection<Map<String, Object>> violations);

    String getName();

    ConfidentialityMechanism getMechanism();

    URL getVisualizationLocation();

    URI getDfdWithViolation();

    URI getDfdWithoutViolation();

    int getCaseStudySystemIdentifier();

}
