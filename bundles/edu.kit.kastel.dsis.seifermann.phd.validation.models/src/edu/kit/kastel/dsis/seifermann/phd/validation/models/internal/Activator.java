package edu.kit.kastel.dsis.seifermann.phd.validation.models.internal;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

public class Activator extends Plugin {

    protected static Activator instance;

    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        Activator.setInstance(this);
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        Activator.setInstance(null);
        super.stop(context);
    }

    protected static void setInstance(Activator instance) {
        Activator.instance = instance;
    }

    public static Activator getInstance() {
        return Activator.instance;
    }

}
