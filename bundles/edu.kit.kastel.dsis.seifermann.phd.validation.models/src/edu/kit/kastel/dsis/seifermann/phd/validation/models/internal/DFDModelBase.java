package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanism;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.DFDModel;

public abstract class DFDModelBase implements DFDModel {

    protected final int caseStudySystemIdentifier;
    protected final String name;
    protected final ConfidentialityMechanism mechanism;
    protected final URI dfdWithoutViolation;
    protected final URI dfdWithViolation;
    protected final URL queryLocation;
    protected final URL visualizationLocation;
    protected final URL queryRulesLocation;

    public DFDModelBase(int id, String name, ConfidentialityMechanism mechanism, String folderName, String dfdModelName,
            String dfdModelWithViolationsName, String queryRulesName, String queryName, String visualizationName) {
        super();

        // build URIs and URLs
        URI dfdURI = dfdModelName == null ? null : createPluginURI(getRelativePath(folderName, dfdModelName));
        URI dfdWithViolationURI = dfdModelWithViolationsName == null ? null
                : createPluginURI(getRelativePath(folderName, dfdModelWithViolationsName));
        URL queryRulesURL = queryRulesName == null ? null
                : getResourceLocation(getRelativePath(folderName, queryRulesName));
        URL queryURL = queryName == null ? null : getResourceLocation(getRelativePath(folderName, queryName));
        URL visualizationURL = visualizationName == null ? null
                : getResourceLocation(getRelativePath(folderName, visualizationName));

        // validation (only necessary during development time)
        throwIfUriButInvalid(dfdURI);
        throwIfUriButInvalid(dfdWithViolationURI);
        throwIfUrlButInvalid(visualizationURL);
        throwIfUrlButInvalid(queryRulesURL);
        throwIfUrlButInvalid(queryURL);

        // init attributes
        this.caseStudySystemIdentifier = id;
        this.name = name;
        this.mechanism = mechanism;
        this.dfdWithoutViolation = dfdURI;
        this.dfdWithViolation = dfdWithViolationURI;
        this.queryRulesLocation = queryRulesURL;
        this.queryLocation = queryURL;
        this.visualizationLocation = visualizationURL;
    }

    public DFDModelBase(int id, String name, ConfidentialityMechanism mechanism) {
        this(id, name, mechanism, null, null, null, null, null, null);
    }

    protected abstract boolean isAcceptableViolation(Map<String, Object> violation);

    public boolean areViolationsAcceptable(Collection<Map<String, Object>> violations) {
        return violations.stream()
            .allMatch(this::isAcceptableViolation);
    }

    @Override
    public int getCaseStudySystemIdentifier() {
        return caseStudySystemIdentifier;
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

    @Override
    public ConfidentialityMechanism getMechanism() {
        return mechanism;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean hasModel() {
        return dfdWithoutViolation != null;
    }

    @Override
    public URL getQueryLocation() {
        return queryLocation;
    }

    @Override
    public URL getQueryRulesLocation() {
        return queryRulesLocation;
    }

    @Override
    public boolean hasQuery() {
        return queryLocation != null;
    }

    private static void throwIfUriButInvalid(URI uri) {
        if (uri == null) {
            return;
        }
        var rs = new ResourceSetImpl();
        if (rs.getResource(uri, true) == null) {
            throw new IllegalArgumentException();
        }
    }

    private static void throwIfUrlButInvalid(URL url) {
        if (url == null) {
            return;
        }
        try (var s = url.openStream()) {
            if (s.read() == -1) {
                throw new IllegalArgumentException();
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static String getRelativePath(String folderName, String fileName) {
        return String.format("dfdModels/%s/%s", folderName, fileName);
    }

    private static URL getResourceLocation(String relativePath) {
        return Activator.getInstance()
            .getBundle()
            .getResource(relativePath);
    }

    private static URI createPluginURI(String relativePath) {
        String bundleName = Activator.getInstance()
            .getBundle()
            .getSymbolicName();
        String uriString = String.format("/%s/%s", bundleName, relativePath);
        return URI.createPlatformPluginURI(uriString, true);
    }

}
