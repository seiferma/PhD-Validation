package edu.kit.kastel.dsis.seifermann.phd.validation.application.dto;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanism;

public class DFDSyntaxValidationResult implements Serializable {

    private static final long serialVersionUID = 3527729162663320862L;

    private double vm11;
    private double vm12;
    private double vm21;
    private Collection<String> vm31;
    private Map<ConfidentialityMechanism, RatioDTO> vm11_raw;
    private Map<ConfidentialityMechanism, RatioDTO> vm12_raw;
    private Map<ConfidentialityMechanism, RatioDTO> vm21_raw;
    private Map<String, Integer> vm31_raw;

    public double getVm11() {
        return vm11;
    }

    public void setVm11(double vm11) {
        this.vm11 = vm11;
    }

    public double getVm12() {
        return vm12;
    }

    public void setVm12(double vm12) {
        this.vm12 = vm12;
    }

    public double getVm21() {
        return vm21;
    }

    public void setVm21(double vm21) {
        this.vm21 = vm21;
    }

    public Collection<String> getVm31() {
        return vm31;
    }

    public void setVm31(Collection<String> failedUsageMetric) {
        this.vm31 = failedUsageMetric;
    }

    public Map<String, Integer> getVm31_raw() {
        return Collections.unmodifiableMap(vm31_raw);
    }

    public void setVm31_raw(final Map<String, Collection<ConfidentialityMechanism>> metaModelUsage) {
        var sortedMap = new LinkedHashMap<String, Integer>();
        metaModelUsage.entrySet()
            .stream()
            .sorted((e1, e2) -> {
                var vDiff = e1.getValue()
                    .size()
                        - e2.getValue()
                            .size();
                if (vDiff != 0) {
                    return vDiff;
                }
                return e1.getKey()
                    .compareTo(e2.getKey());
            })
            .forEach(e -> sortedMap.put(e.getKey(), e.getValue()
                .size()));
        this.vm31_raw = sortedMap;
    }

    public Map<ConfidentialityMechanism, RatioDTO> getVm11_raw() {
        return Collections.unmodifiableMap(vm11_raw);
    }

    public void setVm11_raw(Map<ConfidentialityMechanism, RatioDTO> vm11_raw) {
        this.vm11_raw = new TreeMap<>(vm11_raw);
    }

    public Map<ConfidentialityMechanism, RatioDTO> getVm12_raw() {
        return Collections.unmodifiableMap(vm12_raw);
    }

    public void setVm12_raw(Map<ConfidentialityMechanism, RatioDTO> vm12_raw) {
        this.vm12_raw = new TreeMap<>(vm12_raw);
    }

    public Map<ConfidentialityMechanism, RatioDTO> getVm21_raw() {
        return Collections.unmodifiableMap(vm21_raw);
    }

    public void setVm21_raw(Map<ConfidentialityMechanism, RatioDTO> vm21_raw) {
        this.vm21_raw = new TreeMap<>(vm21_raw);
    }

}
