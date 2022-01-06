package edu.kit.kastel.dsis.seifermann.phd.validation.models;

public enum ConfidentialityMechanismCategory {
    InformationFlow("Information Flow"), AccessControl("Access Control"), Mixed("Combined");

    private final String name;

    ConfidentialityMechanismCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return name;
    }

}
