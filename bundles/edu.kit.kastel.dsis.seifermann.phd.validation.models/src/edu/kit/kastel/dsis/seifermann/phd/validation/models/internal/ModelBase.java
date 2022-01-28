package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanism;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.Model;

public abstract class ModelBase implements Model {

    private String baseFolderName;
    private final int caseStudySystemIdentifier;
    private final String name;
    private final ConfidentialityMechanism mechanism;
    private final URL queryLocation;
    private final URL queryRulesLocation;

    public ModelBase(String baseFolderName, int id, String name, ConfidentialityMechanism mechanism, String folderName,
            String queryRulesName, String queryName) {
        super();
        this.baseFolderName = baseFolderName;
        this.caseStudySystemIdentifier = id;
        this.name = name;
        this.mechanism = mechanism;
        this.queryRulesLocation = getResourceURL(folderName, queryRulesName);
        this.queryLocation = getResourceURL(folderName, queryName);
    }

    public abstract boolean isAcceptableViolation(Map<String, Object> violation);

    @Override
    public int getCaseStudySystemIdentifier() {
        return caseStudySystemIdentifier;
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
    public URL getQueryLocation() {
        return queryLocation;
    }

    @Override
    public URL getQueryRulesLocation() {
        return queryRulesLocation;
    }

    protected static Collection<String> flattenFlowTree(Collection<Object> collection) {
        var result = new ArrayList<String>();
        var queue = new LinkedList<>();
        queue.add(collection);
        while (!queue.isEmpty()) {
            var current = queue.pop();
            if (current instanceof Collection) {
                @SuppressWarnings("unchecked")
                var currentCollection = (Collection<Object>) current;
                queue.addAll(currentCollection);
            } else {
                result.add(current.toString());
            }
        }
        return result;
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
    
    protected URI getModelURI(String folderName, String fileName) {
        if (fileName == null) {
            return null;
        }
        URI uri = createPluginURI(getRelativePath(folderName, fileName));
        throwIfUriButInvalid(uri);
        return uri;
    }
    
    protected URL getResourceURL(String folderName, String fileName) {
        if (fileName == null) {
            return null;
        }
        URL url = getResourceLocation(getRelativePath(folderName, fileName));
        throwIfUrlButInvalid(url);
        return url;
    }

    private String getRelativePath(String folderName, String fileName) {
        return String.format("%s/%s/%s", baseFolderName, folderName, fileName);
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
