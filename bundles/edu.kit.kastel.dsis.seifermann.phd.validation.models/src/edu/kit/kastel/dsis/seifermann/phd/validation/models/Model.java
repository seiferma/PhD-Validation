package edu.kit.kastel.dsis.seifermann.phd.validation.models;

import java.net.URL;
import java.util.Collection;
import java.util.Map;

public interface Model {

    URL getQueryRulesLocation();

    URL getQueryLocation();

    boolean isAcceptableViolation(Map<String, Object> violation);

    String getName();

    ConfidentialityMechanism getMechanism();

    int getCaseStudySystemIdentifier();

    default boolean hasQuery() {
        return getQueryLocation() != null;
    }

    default boolean areViolationsAcceptable(Collection<Map<String, Object>> violations) {
        return violations.stream()
            .allMatch(this::isAcceptableViolation);
    }

}
