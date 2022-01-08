package edu.kit.kastel.dsis.seifermann.phd.validation.application.dto;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanism;

public class DFDAnalysesValidationResult implements Serializable {

    private static final long serialVersionUID = 8635130800146662651L;
    private double vm51;
    private double vm52;
    private double vm53;
    private Map<ConfidentialityMechanism, RatioDTO> vm51_raw;
    private Map<ConfidentialityMechanism, RatioDTO> vm52_raw;
    private Map<ConfidentialityMechanism, RatioDTO> vm53_raw;

    public double getVm51() {
        return vm51;
    }

    public void setVm51(double vm51) {
        this.vm51 = vm51;
    }

    public double getVm52() {
        return vm52;
    }

    public void setVm52(double vm52) {
        this.vm52 = vm52;
    }

    public double getVm53() {
        return vm53;
    }

    public void setVm53(double vm53) {
        this.vm53 = vm53;
    }

    public Map<ConfidentialityMechanism, RatioDTO> getVm51_raw() {
        return Collections.unmodifiableMap(vm51_raw);
    }

    public void setVm51_raw(Map<ConfidentialityMechanism, RatioDTO> vm51_raw) {
        this.vm51_raw = getSorted(vm51_raw);
    }

    public Map<ConfidentialityMechanism, RatioDTO> getVm52_raw() {
        return Collections.unmodifiableMap(vm52_raw);
    }

    public void setVm52_raw(Map<ConfidentialityMechanism, RatioDTO> vm52_raw) {
        this.vm52_raw = getSorted(vm52_raw);
    }

    public Map<ConfidentialityMechanism, RatioDTO> getVm53_raw() {
        return Collections.unmodifiableMap(vm53_raw);
    }

    public void setVm53_raw(Map<ConfidentialityMechanism, RatioDTO> vm53_raw) {
        this.vm53_raw = getSorted(vm53_raw);
    }

    protected static Map<ConfidentialityMechanism, RatioDTO> getSorted(Map<ConfidentialityMechanism, RatioDTO> input) {
        var result = new LinkedHashMap<ConfidentialityMechanism, RatioDTO>();
        input.entrySet()
            .stream()
            .sorted((e1, e2) -> {
                var e1Ratio = e1.getValue()
                    .getRatio();
                var e2Ratio = e2.getValue()
                    .getRatio();
                if (e1Ratio != e2Ratio) {
                    return Double.compare(e1Ratio, e2Ratio);
                }
                var e1Mechanism = e1.getKey()
                    .getName();
                var e2Mechanism = e2.getKey()
                    .getName();
                return e1Mechanism.compareTo(e2Mechanism);
            })
            .forEach(e -> result.put(e.getKey(), e.getValue()));
        return result;
    }
}
