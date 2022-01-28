package edu.kit.kastel.dsis.seifermann.phd.validation.models;

public enum CommunicationParadigm {

    CONTROL_FLOW("Control Flow"), DATA_FLOW("Data Flow");

    private final String name;

    private CommunicationParadigm(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
