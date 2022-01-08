package edu.kit.kastel.dsis.seifermann.phd.validation.models;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.Activator;

public class DFDModelIndex {

    protected static Collection<DFDModel> MODELS = Collections.unmodifiableCollection(DFDModelIndex.createModelsList());

    public static Collection<DFDModel> getModelList() {
        return getModelList(m -> true);
    }
    
    public static Collection<DFDModel> getModelList(Predicate<DFDModel> filterPredicate) {
        return MODELS.stream()
            .filter(filterPredicate::test)
            .collect(Collectors.toList());
    }

    protected static Collection<DFDModel> createModelsList() {
        return Arrays.asList(
                createDFDModel(1, "TravelPlanner", ConfidentialityMechanism.NonInterferenceLinear, "travelplanner",
                        "DFDC_TravelPlanner_InformationFlow.xmi", "DFDC_TravelPlanner_InformationFlow_WithIssue.xmi",
                        null, "InformationFlowQuery.pl", "TravelPlanner_InformationFlow.svg"),
                createDFDModel(2, "DistanceTracker", ConfidentialityMechanism.NonInterferenceLinear, "distancetracker",
                        "DFDC_DistanceTracker_InformationFlow.xmi",
                        "DFDC_DistanceTracker_InformationFlow_WithIssue.xmi", null, "InformationFlowQuery.pl",
                        "DistanceTracker-InformationFlow.svg"),
                createDFDModel(3, "ContactSMS", ConfidentialityMechanism.NonInterferenceLinear, "contactsms",
                        "DFDC_ContactSMS_InformationFlow.xmi", "DFDC_ContactSMS_InformationFlow_WithIssue.xmi", null,
                        "InformationFlowQuery.pl", "ContactSMS-InformationFlow.svg"),
                createDFDModel(4, "PrivateTaxi", ConfidentialityMechanism.NonInterferenceArbitraryWithEncryption,
                        "privatetaxi", "privatetaxi_arbitrary_dfd.xmi", "privatetaxi_arbitrary_dfd_withIssue.xmi",
                        "InformationFlowRules.pl", "InformationFlowQuery.pl", "PrivateTaxi.svg"),
                createDFDModel(5, "BankingApp", ConfidentialityMechanism.NonInterferenceTenant),
                createDFDModel(6, "FriendMap", ConfidentialityMechanism.NonInterferenceLinearWithEncryption,
                        "friendmap", "DFDC_FriendMapAlternative_Linear.xmi", "DFDC_FriendMap_Linear.xmi", null,
                        "InformationFlowQuery.pl", "FriendMap.svg"),
                createDFDModel(7, "Hospital", ConfidentialityMechanism.NonInterferenceLinearWithEncryption, "hospital",
                        "DFDC_HospitalAlternative_Linear.xmi", "DFDC_HospitalLoopful_Linear.xmi", null,
                        "InformationFlowQuery.pl", "Hospital.svg"),
                createDFDModel(8, "JPMail", ConfidentialityMechanism.NonInterferenceLinearWithEncryption, "jpmail",
                        "DFDC_JPMail_Linear.xmi", "DFDC_JPMail_Linear_WithIssue.xmi", null, "InformationFlowQuery.pl",
                        "JPMail.svg"),
                createDFDModel(9, "WebRTC", ConfidentialityMechanism.NonInterferenceLinearWithEncryption, "webrtc",
                        "DFDC_WebRTC_Linear.xmi", "DFDC_WebRTC_Linear_WithIssue.xmi", null, "InformationFlowQuery.pl",
                        "WebRTC.svg"),
                createDFDModel(10, "TravelPlanner", ConfidentialityMechanism.RBAC, "travelplanner",
                        "DFDC_TravelPlanner_AccessControl.xmi", "DFDC_TravelPlanner_AccessControl_WithIssue.xmi", null,
                        "AccessControlQuery.pl", "TravelPlanner_AccessControl.svg"),
                createDFDModel(11, "DistanceTracker", ConfidentialityMechanism.RBAC, "distancetracker",
                        "DFDC_DistanceTracker_AccessControl.xmi", "DFDC_DistanceTracker_AccessControl_Withissue.xmi",
                        null, "AccessControlQuery.pl", "DistanceTracker-AccessControl.svg"),
                createDFDModel(12, "ContactSMS", ConfidentialityMechanism.RBAC, "contactsms",
                        "DFDC_ContactSMS_AccessControl.xmi", "DFDC_ContactSMS_AccessControl_WithIssue.xmi", null,
                        "AccessControlQuery.pl", "ContactSMS-RBAC.svg"),
                createDFDModel(13, "ImageSharing", ConfidentialityMechanism.DAC, "dac_delegation", "dac_dfd.xmi",
                        "dac_dfd_withIssue.xmi", "AccessControlRules.pl", "AccessControlQuery.pl", "DAC.svg"),
                createDFDModel(14, "FlightControl", ConfidentialityMechanism.MAC_Military, "mac", "mac_dfd.xmi",
                        "mac_dfd_readViolation.xmi", null, "AccessControlQuery.pl", "MAC-ReadViolation.svg"),
                createDFDModel(15, "HealthRecord", ConfidentialityMechanism.MAC_NTK, "mac_needtoknow", "mac_dfd.xmi",
                        "mac_dfd_withIssue.xmi", null, "AccessControlQuery.pl", "MAC.svg"),
                createDFDModel(16, "BankBranches", ConfidentialityMechanism.ABAC, "abac", "abac_dfd.xmi",
                        "abac_dfd_withIssue.xmi", "AccessControlRules.pl", "AccessControlQuery.pl", "ABAC.svg"),
                createDFDModel(17, "TravelPlanner", ConfidentialityMechanism.RBAC_TAINT, "travelplanner",
                        "DFDC_TravelPlanner_TMAC.xmi", "DFDC_TravelPlanner_TMAC_WithIssue.xmi", null, "TMACQuery.pl",
                        "TravelPlanner_TMAC.svg"));
    }

    protected static DFDModel createDFDModel(int id, String name, ConfidentialityMechanism mechanism) {
        return new DFDModel(id, name, mechanism, null, null, null, null, null);
    }

    protected static DFDModel createDFDModel(int id, String name, ConfidentialityMechanism mechanism, String folderName,
            String dfdModelName, String dfdModelWithViolationsName, String queryRulesName, String queryName,
            String visualizationName) {
        URI dfdURI = createPluginURI(getRelativePath(folderName, dfdModelName));
        URI dfdWithViolationURI = createPluginURI(getRelativePath(folderName, dfdModelWithViolationsName));
        URL visualizationURL = getResourceLocation(getRelativePath(folderName, visualizationName));
        URL queryRulesURL = getResourceLocation(getRelativePath(folderName, queryRulesName));
        URL queryURL = getResourceLocation(getRelativePath(folderName, queryName));

        // validation (only necessary during development time)
        throwIfUriButInvalid(dfdURI);
        throwIfUriButInvalid(dfdWithViolationURI);
        throwIfUrlButInvalid(visualizationURL);
        throwIfUrlButInvalid(queryRulesURL);
        throwIfUrlButInvalid(queryURL);

        return new DFDModel(id, name, mechanism, dfdURI, dfdWithViolationURI, queryRulesURL, queryURL,
                visualizationURL);
    }

    protected static void throwIfUriButInvalid(URI uri) {
        if (uri == null) {
            return;
        }
        var rs = new ResourceSetImpl();
        if (rs.getResource(uri, true) == null) {
            throw new IllegalArgumentException();
        }
    }

    protected static void throwIfUrlButInvalid(URL url) {
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

    protected static String getRelativePath(String folderName, String fileName) {
        return String.format("dfdModels/%s/%s", folderName, fileName);
    }

    protected static URL getResourceLocation(String relativePath) {
        return Activator.getInstance()
            .getBundle()
            .getResource(relativePath);
    }

    protected static URI createPluginURI(String relativePath) {
        String bundleName = Activator.getInstance()
            .getBundle()
            .getSymbolicName();
        String uriString = String.format("/%s/%s", bundleName, relativePath);
        return URI.createPlatformPluginURI(uriString, true);
    }
}
