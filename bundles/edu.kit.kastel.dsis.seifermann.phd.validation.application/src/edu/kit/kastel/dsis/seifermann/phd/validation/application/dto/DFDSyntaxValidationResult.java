package edu.kit.kastel.dsis.seifermann.phd.validation.application.dto;

import java.io.Serializable;
import java.util.Map;

import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanism;

public class DFDSyntaxValidationResult implements Serializable {

    private static final long serialVersionUID = 3527729162663320862L;

    private double vm11;
    private double vm12;
    private double vm21;
    private int vm31;
    private Map<ConfidentialityMechanism, RatioDTO> vm11_raw;
    private Map<ConfidentialityMechanism, RatioDTO> vm12_raw;
    private Map<ConfidentialityMechanism, RatioDTO> vm21_raw;
    private Map<String, Double> vm31_raw;

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

    public int getVm31() {
        return vm31;
    }

    public void setVm31(int vm31) {
        this.vm31 = vm31;
    }

    public Map<String, Double> getVm31_raw() {
        return vm31_raw;
    }

    public void setVm31_raw(Map<String, Double> vm31_raw) {
        this.vm31_raw = vm31_raw;
    }

    public Map<ConfidentialityMechanism, RatioDTO> getVm11_raw() {
        return vm11_raw;
    }

    public void setVm11_raw(Map<ConfidentialityMechanism, RatioDTO> vm11_raw) {
        this.vm11_raw = vm11_raw;
    }

    public Map<ConfidentialityMechanism, RatioDTO> getVm12_raw() {
        return vm12_raw;
    }

    public void setVm12_raw(Map<ConfidentialityMechanism, RatioDTO> vm12_raw) {
        this.vm12_raw = vm12_raw;
    }

    public Map<ConfidentialityMechanism, RatioDTO> getVm21_raw() {
        return vm21_raw;
    }

    public void setVm21_raw(Map<ConfidentialityMechanism, RatioDTO> vm21_raw) {
        this.vm21_raw = vm21_raw;
    }

}
