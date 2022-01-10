package edu.kit.kastel.dsis.seifermann.phd.validation.application.dto;

import java.util.Collection;
import java.util.Map;

public class DFDModelAnalysisResultDTO {

    private Collection<Map<String, Object>> violations;
    private Collection<Map<String, Object>> wrongViolations;

    public Collection<Map<String, Object>> getViolations() {
        return violations;
    }

    public void setViolations(Collection<Map<String, Object>> violations) {
        this.violations = violations;
    }

    public Collection<Map<String, Object>> getWrongViolations() {
        return wrongViolations;
    }

    public void setWrongViolations(Collection<Map<String, Object>> wrongViolations) {
        this.wrongViolations = wrongViolations;
    }

}
