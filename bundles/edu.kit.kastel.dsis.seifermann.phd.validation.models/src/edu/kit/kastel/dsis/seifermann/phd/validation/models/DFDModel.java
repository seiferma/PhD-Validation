package edu.kit.kastel.dsis.seifermann.phd.validation.models;

import java.net.URL;

import org.eclipse.emf.common.util.URI;

public class DFDModel {

    protected final int caseStudySystemIdentifier;
    protected final String name;
    protected final ConfidentialityMechanism mechanism;
    protected final URI dfdWithoutViolation;
    protected final URI dfdWithViolation;
    protected final URL queryLocation;
    protected final URL visualizationLocation;
    protected final URL queryRulesLocation;

    public DFDModel(int caseStudySystemIdentifier, String name, ConfidentialityMechanism mechanism,
            URI dfdWithoutViolation, URI dfdWithViolation, URL queryRulesLocation, URL queryLocation,
            URL visualizationLocation) {
        super();
        this.caseStudySystemIdentifier = caseStudySystemIdentifier;
        this.name = name;
        this.mechanism = mechanism;
        this.dfdWithoutViolation = dfdWithoutViolation;
        this.dfdWithViolation = dfdWithViolation;
        this.queryRulesLocation = queryRulesLocation;
        this.queryLocation = queryLocation;
        this.visualizationLocation = visualizationLocation;
    }

    public int getCaseStudySystemIdentifier() {
        return caseStudySystemIdentifier;
    }

    public URI getDfdWithoutViolation() {
        return dfdWithoutViolation;
    }

    public URI getDfdWithViolation() {
        return dfdWithViolation;
    }

    public URL getVisualizationLocation() {
        return visualizationLocation;
    }

    public ConfidentialityMechanism getMechanism() {
        return mechanism;
    }

    public String getName() {
        return name;
    }

    public boolean hasModel() {
        return dfdWithoutViolation != null;
    }

    public URL getQueryLocation() {
        return queryLocation;
    }

    public URL getQueryRulesLocation() {
        return queryRulesLocation;
    }
    
    public boolean hasQuery() {
        return queryLocation != null;
    }

}
