package edu.kit.kastel.dsis.seifermann.phd.validation.models;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.internal.Activator;

public class DFDModelIndex {

    protected static Collection<DFDModel> MODELS = Collections.unmodifiableCollection(DFDModelIndex.createModelsList());

    public static Collection<DFDModel> getModelList(boolean onlyWithModel) {
        if (onlyWithModel) {
            return MODELS.stream()
                .filter(DFDModel::hasModel)
                .collect(Collectors.toList());
        }
        return MODELS;
    }

    protected static Collection<DFDModel> createModelsList() {
        return Arrays.asList(
                createDFDModel(1, "TravelPlanner", ConfidentialityMechanism.NonInterferenceLinear, "travelplanner",
                        "DFDC_TravelPlanner_InformationFlow.xmi", "DFDC_TravelPlanner_InformationFlow_WithIssue.xmi",
                        "TravelPlanner_InformationFlow.svg"),
                createDFDModel(2, "DistanceTracker", ConfidentialityMechanism.NonInterferenceLinear, "distancetracker",
                        "DFDC_DistanceTracker_InformationFlow.xmi",
                        "DFDC_DistanceTracker_InformationFlow_WithIssue.xmi", "DistanceTracker-InformationFlow.svg"),
                createDFDModel(3, "ContactSMS", ConfidentialityMechanism.NonInterferenceLinear, "contactsms",
                        "DFDC_ContactSMS_InformationFlow.xmi", "DFDC_ContactSMS_InformationFlow_WithIssue.xmi",
                        "ContactSMS-InformationFlow.svg"),
                createDFDModel(4, "PrivateTaxi", ConfidentialityMechanism.NonInterferenceArbitraryWithEncryption,
                        "privatetaxi", "privatetaxi_dfd.xmi", "privatetaxi_dfd_withIssue.xmi", "PrivateTaxi.svg"),
                createDFDModel(5, "BankingApp", ConfidentialityMechanism.NonInterferenceTenant),
                createDFDModel(6, "FriendMap", ConfidentialityMechanism.NonInterferenceLinearWithEncryption,
                        "friendmap", "DFDC_FriendMapAlternative.xmi", "DFDC_FriendMap.xmi", "FriendMap.svg"),
                createDFDModel(7, "Hospital", ConfidentialityMechanism.NonInterferenceLinearWithEncryption, "hospital",
                        "DFDC_HospitalAlternative.xmi", "DFDC_Hospital.xmi", "Hospital.svg"),
                createDFDModel(8, "JPMail", ConfidentialityMechanism.NonInterferenceLinearWithEncryption, "jpmail",
                        "DFDC_JPMail.xmi", "DFDC_JPMail_WithIssue.xmi", "JPMail.svg"),
                createDFDModel(9, "WebRTC", ConfidentialityMechanism.NonInterferenceLinearWithEncryption, "webrtc",
                        "DFDC_WebRTC.xmi", "DFDC_WebRTC_WithIssue.xmi", "WebRTC.svg"),
                createDFDModel(10, "TravelPlanner", ConfidentialityMechanism.RBAC, "travelplanner",
                        "DFDC_TravelPlanner_AccessControl.xmi", "DFDC_TravelPlanner_AccessControl_WithIssue.xmi",
                        "TravelPlanner_AccessControl.svg"),
                createDFDModel(11, "DistanceTracker", ConfidentialityMechanism.RBAC, "distancetracker",
                        "DFDC_DistanceTracker_AccessControl.xmi", "DFDC_DistanceTracker_AccessControl_Withissue.xmi",
                        "DistanceTracker-AccessControl.svg"),
                createDFDModel(12, "ContactSMS", ConfidentialityMechanism.RBAC, "contactsms",
                        "DFDC_ContactSMS_AccessControl.xmi", "DFDC_ContactSMS_AccessControl_WithIssue.xmi",
                        "ContactSMS-RBAC.svg"),
                createDFDModel(13, "ImageSharing", ConfidentialityMechanism.DAC, "dac_delegation", "dac_dfd.xmi",
                        "dac_dfd_withIssue.xmi", "DAC.svg"),
                createDFDModel(14, "FlightControl", ConfidentialityMechanism.MAC_Military, "mac", "mac_dfd.xmi",
                        "mac_dfd_readViolation.xmi", "MAC-ReadViolation.svg"),
                createDFDModel(15, "HealthRecord", ConfidentialityMechanism.MAC_NTK, "mac_needtoknow", "mac_dfd.xmi",
                        "mac_dfd_withIssue.xmi", "MAC.svg"),
                createDFDModel(16, "BankBranches", ConfidentialityMechanism.ABAC, "abac", "abac_dfd.xmi",
                        "abac_dfd_withIssue.xmi", "ABAC.svg"),
                createDFDModel(17, "TravelPlanner", ConfidentialityMechanism.RBAC_TAINT, "travelplanner",
                        "DFDC_TravelPlanner_TMAC.xmi", "DFDC_TravelPlanner_TMAC_WithIssue.xmi",
                        "TravelPlanner_TMAC.svg"));
    }

    protected static DFDModel createDFDModel(int id, String name, ConfidentialityMechanism mechanism) {
        return new DFDModel(id, name, mechanism, null, null, null);
    }

    protected static DFDModel createDFDModel(int id, String name, ConfidentialityMechanism mechanism, String folderName,
            String dfdModelName, String dfdModelWithViolationsName, String visualizationName) {
        URI dfdURI = createPluginURI(String.format("dfdModels/%s/%s", folderName, dfdModelName));
        URI dfdWithViolationURI = createPluginURI(
                String.format("dfdModels/%s/%s", folderName, dfdModelWithViolationsName));
        URL visualizationURL = getResourceLocation(String.format("dfdModels/%s/%s", folderName, visualizationName));

        // validation (only necessary during development time)
        var uriConverter = new ResourceSetImpl().getURIConverter();
        if (!uriConverter.exists(dfdURI, Collections.emptyMap())) {
            throw new IllegalStateException();
        }
        if (!uriConverter.exists(dfdWithViolationURI, Collections.emptyMap())) {
            throw new IllegalStateException();
        }
        try {
            visualizationURL.openConnection();
        } catch (IOException e) {
            throw new IllegalStateException();
        }

        return new DFDModel(id, name, mechanism, dfdURI, dfdWithViolationURI, visualizationURL);
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
