package edu.kit.kastel.dsis.seifermann.phd.validation.application;

import java.io.File;
import java.util.Optional;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.xtext.linking.impl.AbstractCleaningLinker;
import org.eclipse.xtext.linking.impl.DefaultLinkingService;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.resource.containers.ResourceSetBasedAllContainersStateProvider;
import org.palladiosimulator.dataflow.confidentiality.pcm.dddsl.DDDslStandaloneSetup;

import de.uka.ipd.sdq.workflow.WorkflowExceptionHandler;
import edu.kit.kastel.dsis.seifermann.phd.validation.application.workflow.ValidationWorkflow;
import tools.mdsd.library.standalone.initialization.StandaloneInitializationException;
import tools.mdsd.library.standalone.initialization.emfprofiles.EMFProfileInitializationTask;
import tools.mdsd.library.standalone.initialization.log4j.Log4jInitilizationTask;

public class CLIApplication implements IApplication {

    @FunctionalInterface
    protected interface ValidationResult {
        String getErrorMessage();

        default boolean hasError() {
            return getErrorMessage() != null;
        }
    }

    @Override
    public Object start(IApplicationContext context) throws Exception {

        final String[] args = Optional.ofNullable(context.getArguments()
            .get(IApplicationContext.APPLICATION_ARGS))
            .filter(String[].class::isInstance)
            .map(String[].class::cast)
            .orElseThrow(
                    () -> new IllegalStateException("Cannot access application arguments because of framework error."));

        if (args.length != 1) {
            System.err.println("The application requires exactly one argument, which is the output directory.");
            return 1;
        }

        final String outputDirArgument = args[0];
        final File outputDirectory = new File(outputDirArgument);

        var validator = validateOutputDirectory(outputDirectory);
        if (validator.hasError()) {
            System.err.println(validator.getErrorMessage());
            return 2;
        }

        runValidation(outputDirectory);

        return IApplication.EXIT_OK;
    }

    protected void runValidation(final File outputDirectory) throws StandaloneInitializationException {
        // initialization
        EcorePlugin.ExtensionProcessor.process(null);
        new EMFProfileInitializationTask("org.palladiosimulator.dataflow.confidentiality.pcm.model.profile",
                "profile.emfprofile_diagram").initilizationWithoutPlatform();
        DDDslStandaloneSetup.doSetup();
        new Log4jInitilizationTask().initilizationWithoutPlatform();
        BasicConfigurator.configure(new ConsoleAppender(new PatternLayout("%d{HH:mm:ss,SSS} %m%n")));
        Logger.getLogger(AbstractInternalAntlrParser.class)
            .setLevel(Level.WARN);
        Logger.getLogger(DefaultLinkingService.class)
            .setLevel(Level.WARN);
        Logger.getLogger(ResourceSetBasedAllContainersStateProvider.class)
            .setLevel(Level.WARN);
        Logger.getLogger(AbstractCleaningLinker.class)
            .setLevel(Level.WARN);

        final ValidationWorkflow workflow = ValidationWorkflow.build(new WorkflowExceptionHandler(true),
                outputDirectory);
        workflow.run();
    }

    protected ValidationResult validateOutputDirectory(final File outputDirectory) {
        if (outputDirectory.exists()) {
            if (outputDirectory.isDirectory()) {
                if (outputDirectory.listFiles().length != 0) {
                    return () -> "The output directory must be empty.";
                }
            } else {
                return () -> "The output directory must be a directory.";
            }
        } else {
            if (!outputDirectory.getParentFile()
                .exists()) {
                return () -> "The parent directory of the output directory must exist.";
            } else {
                if (!outputDirectory.getParentFile()
                    .isDirectory()) {
                    return () -> "The parent directory of the output directory must be a directory.";
                }
            }
        }
        return () -> null;
    }

    @Override
    public void stop() {
        throw new UnsupportedOperationException("Forcing to stop the application is not supported.");
    }

}
