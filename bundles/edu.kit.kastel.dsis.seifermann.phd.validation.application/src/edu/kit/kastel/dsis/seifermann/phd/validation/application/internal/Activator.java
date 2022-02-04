package edu.kit.kastel.dsis.seifermann.phd.validation.application.internal;

import java.util.Optional;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.palladiosimulator.supporting.prolog.api.PrologAPI;
import org.prolog4j.manager.IProverManager;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.DFDModelIndex;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.PCMModelIndex;

public class Activator extends Plugin {

    protected static class ServiceProvider<S> {
        protected final Class<S> serviceClass;
        protected final BundleContext context;
        protected ServiceReference<S> serviceReference;
        protected volatile S service;

        public ServiceProvider(Class<S> serviceClass, BundleContext context) {
            this.serviceClass = serviceClass;
            this.context = context;
        }

        protected S init() {
            serviceReference = context.getServiceReference(serviceClass);
            service = context.getService(serviceReference);
            return service;
        }

        public S get() {
            var localRef = service;
            if (localRef == null) {
                synchronized (this) {
                    localRef = service;
                    if (localRef == null) {
                        localRef = init();
                    }
                }
            }
            return localRef;
        }

        public void uninit() {
            synchronized (this) {
                if (serviceReference != null) {
                    context.ungetService(serviceReference);
                    serviceReference = null;
                    service = null;
                }
            }
        }
    }

    private static Activator instance;
    private ServiceProvider<IProverManager> proverManager;
    private ServiceProvider<PrologAPI> prologApi;
    private ServiceProvider<DFDModelIndex> dfdModelIndex;
    private ServiceProvider<PCMModelIndex> pcmModelIndex;

    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        setInstance(this);
        proverManager = new ServiceProvider<>(IProverManager.class, context);
        prologApi = new ServiceProvider<>(PrologAPI.class, context);
        dfdModelIndex = new ServiceProvider<>(DFDModelIndex.class, context);
        pcmModelIndex = new ServiceProvider<>(PCMModelIndex.class, context);
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        proverManager.uninit();
        proverManager = null;
        prologApi.uninit();
        prologApi = null;
        dfdModelIndex.uninit();
        dfdModelIndex = null;
        pcmModelIndex.uninit();
        pcmModelIndex = null;
        setInstance(null);
        super.stop(context);
    }

    private static void setInstance(Activator instance) {
        Activator.instance = instance;
    }

    public static Activator getInstance() {
        return Activator.instance;
    }

    public IProverManager getProverManager() {
        return Optional.ofNullable(proverManager)
            .map(ServiceProvider::get)
            .orElse(null);
    }

    public PrologAPI getPrologAPI() {
        return Optional.ofNullable(prologApi)
            .map(ServiceProvider::get)
            .orElse(null);
    }

    public DFDModelIndex getDFDModelIndex() {
        return Optional.ofNullable(dfdModelIndex)
            .map(ServiceProvider::get)
            .orElse(null);
    }

    public PCMModelIndex getPCMModelIndex() {
        return Optional.ofNullable(pcmModelIndex)
            .map(ServiceProvider::get)
            .orElse(null);
    }

}
