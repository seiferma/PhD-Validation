package edu.kit.kastel.dsis.seifermann.phd.validation.models;

public enum ConfidentialityMechanism {
    NonInterferenceLinear("Non-Interference Linear", ConfidentialityMechanismCategory.InformationFlow),
    NonInterferenceArbitrary("Non-Interference Arbitrary", ConfidentialityMechanismCategory.InformationFlow),
    NonInterferenceTenant("Non-Interference Tenant", ConfidentialityMechanismCategory.InformationFlow),
    DAC("DAC", ConfidentialityMechanismCategory.AccessControl),
    MAC_Military("MAC Military Model", ConfidentialityMechanismCategory.AccessControl),
    MAC_NTK("MAC Need-to-Know", ConfidentialityMechanismCategory.AccessControl),
    RBAC("RBAC", ConfidentialityMechanismCategory.AccessControl),
    ABAC("ABAC", ConfidentialityMechanismCategory.AccessControl),
    RBAC_TAINT("RBAC + Tainting", ConfidentialityMechanismCategory.Mixed);

    private final String name;
    private final ConfidentialityMechanismCategory category;

    private ConfidentialityMechanism(String name, ConfidentialityMechanismCategory category) {
        this.name = name;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public ConfidentialityMechanismCategory getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return name;
    }
    
}
