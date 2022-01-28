package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal;

import java.net.URL;

import org.eclipse.emf.common.util.URI;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanism;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.DFDModel;

public abstract class DFDModelBase extends ModelBase implements DFDModel {

    private final URI dfdWithoutViolation;
    private final URI dfdWithViolation;
    private final URL visualizationLocation;

    public DFDModelBase(int id, String name, ConfidentialityMechanism mechanism, String folderName, String dfdModelName,
            String dfdModelWithViolationsName, String queryRulesName, String queryName, String visualizationName) {
        super("dfdModels", id, name, mechanism, folderName, queryRulesName, queryName);
        this.dfdWithoutViolation = getModelURI(folderName, dfdModelName);
        this.dfdWithViolation = getModelURI(folderName, dfdModelWithViolationsName);
        this.visualizationLocation = getResourceURL(folderName, visualizationName);
    }

    public DFDModelBase(int id, String name, ConfidentialityMechanism mechanism) {
        this(id, name, mechanism, null, null, null, null, null, null);
    }

    @Override
    public URI getDfdWithoutViolation() {
        return dfdWithoutViolation;
    }

    @Override
    public URI getDfdWithViolation() {
        return dfdWithViolation;
    }

    @Override
    public URL getVisualizationLocation() {
        return visualizationLocation;
    }

}
