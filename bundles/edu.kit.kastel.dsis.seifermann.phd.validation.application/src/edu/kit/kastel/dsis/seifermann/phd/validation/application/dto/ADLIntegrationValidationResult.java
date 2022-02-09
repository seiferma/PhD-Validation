package edu.kit.kastel.dsis.seifermann.phd.validation.application.dto;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import edu.kit.kastel.dsis.seifermann.phd.validation.application.calculations.JaccardCoefficientCalculator.JaccardCoefficientRaw;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanism;

public class ADLIntegrationValidationResult implements Serializable {

    private static final long serialVersionUID = -6723449364250835405L;

    private double vm81;
    private double vm82;
    private double vm83;
    private double vm84;
    private double vm85;
    private double vm86;
    private double vm91;
    private double vm92;
    private double vm93;
    private double vm94;
    private Map<Integer, Double> vm101;
    private Map<Integer, Double> vm102;
    private Map<Pair<Integer, Integer>, Double> vm111;
    private Map<Pair<Integer, Integer>, Double> vm112;
    private Map<ConfidentialityMechanism, RatioDTO> vm81_raw;
    private Map<ConfidentialityMechanism, RatioDTO> vm82_raw;
    private Map<ConfidentialityMechanism, RatioDTO> vm83_raw;
    private Map<ConfidentialityMechanism, RatioDTO> vm84_raw;
    private Map<ConfidentialityMechanism, RatioDTO> vm85_raw;
    private Map<ConfidentialityMechanism, RatioDTO> vm86_raw;
    private Map<Integer, DFDModelAnalysisResultDTO> vm91_raw;
    private Map<Integer, DFDModelAnalysisResultDTO> vm92_raw;
    private Map<Integer, DFDModelAnalysisResultDTO> vm93_raw;
    private Map<Integer, DFDModelAnalysisResultDTO> vm94_raw;
    private Map<Integer, JaccardCoefficientRaw> vm101_raw;
    private Map<Integer, JaccardCoefficientRaw> vm102_raw;
    private Map<Pair<Integer, Integer>, JaccardCoefficientRaw> vm111_raw;
    private Map<Pair<Integer, Integer>, JaccardCoefficientRaw> vm112_raw;

    public double getVm81() {
        return vm81;
    }

    public void setVm81(double vm81) {
        this.vm81 = vm81;
    }

    public double getVm82() {
        return vm82;
    }

    public void setVm82(double vm82) {
        this.vm82 = vm82;
    }

    public double getVm83() {
        return vm83;
    }

    public void setVm83(double vm83) {
        this.vm83 = vm83;
    }

    public double getVm84() {
        return vm84;
    }

    public void setVm84(double vm84) {
        this.vm84 = vm84;
    }

    public double getVm85() {
        return vm85;
    }

    public void setVm85(double vm85) {
        this.vm85 = vm85;
    }

    public double getVm86() {
        return vm86;
    }

    public void setVm86(double vm86) {
        this.vm86 = vm86;
    }

    public Map<ConfidentialityMechanism, RatioDTO> getVm81_raw() {
        return vm81_raw;
    }

    public void setVm81_raw(Map<ConfidentialityMechanism, RatioDTO> vm81_raw) {
        this.vm81_raw = vm81_raw;
    }

    public Map<ConfidentialityMechanism, RatioDTO> getVm82_raw() {
        return vm82_raw;
    }

    public void setVm82_raw(Map<ConfidentialityMechanism, RatioDTO> vm82_raw) {
        this.vm82_raw = vm82_raw;
    }

    public Map<ConfidentialityMechanism, RatioDTO> getVm83_raw() {
        return vm83_raw;
    }

    public void setVm83_raw(Map<ConfidentialityMechanism, RatioDTO> vm83_raw) {
        this.vm83_raw = vm83_raw;
    }

    public Map<ConfidentialityMechanism, RatioDTO> getVm84_raw() {
        return vm84_raw;
    }

    public void setVm84_raw(Map<ConfidentialityMechanism, RatioDTO> vm84_raw) {
        this.vm84_raw = vm84_raw;
    }

    public Map<ConfidentialityMechanism, RatioDTO> getVm85_raw() {
        return vm85_raw;
    }

    public void setVm85_raw(Map<ConfidentialityMechanism, RatioDTO> vm85_raw) {
        this.vm85_raw = vm85_raw;
    }

    public Map<ConfidentialityMechanism, RatioDTO> getVm86_raw() {
        return vm86_raw;
    }

    public void setVm86_raw(Map<ConfidentialityMechanism, RatioDTO> vm86_raw) {
        this.vm86_raw = vm86_raw;
    }

    public double getVm91() {
        return vm91;
    }

    public void setVm91(double vm91) {
        this.vm91 = vm91;
    }

    public double getVm92() {
        return vm92;
    }

    public void setVm92(double vm92) {
        this.vm92 = vm92;
    }

    public double getVm93() {
        return vm93;
    }

    public void setVm93(double vm93) {
        this.vm93 = vm93;
    }

    public double getVm94() {
        return vm94;
    }

    public void setVm94(double vm94) {
        this.vm94 = vm94;
    }

    public Map<Integer, DFDModelAnalysisResultDTO> getVm91_raw() {
        return vm91_raw;
    }

    public void setVm91_raw(Map<Integer, DFDModelAnalysisResultDTO> vm91_raw) {
        this.vm91_raw = vm91_raw;
    }

    public Map<Integer, DFDModelAnalysisResultDTO> getVm92_raw() {
        return vm92_raw;
    }

    public void setVm92_raw(Map<Integer, DFDModelAnalysisResultDTO> vm92_raw) {
        this.vm92_raw = vm92_raw;
    }

    public Map<Integer, DFDModelAnalysisResultDTO> getVm93_raw() {
        return vm93_raw;
    }

    public void setVm93_raw(Map<Integer, DFDModelAnalysisResultDTO> vm93_raw) {
        this.vm93_raw = vm93_raw;
    }

    public Map<Integer, DFDModelAnalysisResultDTO> getVm94_raw() {
        return vm94_raw;
    }

    public void setVm94_raw(Map<Integer, DFDModelAnalysisResultDTO> vm94_raw) {
        this.vm94_raw = vm94_raw;
    }

    public Map<Pair<Integer, Integer>, Double> getVm111() {
        return vm111;
    }

    public void setVm111(Map<Pair<Integer, Integer>, Double> vm111) {
        this.vm111 = vm111;
    }

    public Map<Pair<Integer, Integer>, Double> getVm112() {
        return vm112;
    }

    public void setVm112(Map<Pair<Integer, Integer>, Double> vm112) {
        this.vm112 = vm112;
    }

    public Map<Pair<Integer, Integer>, JaccardCoefficientRaw> getVm111_raw() {
        return vm111_raw;
    }

    public void setVm111_raw(Map<Pair<Integer, Integer>, JaccardCoefficientRaw> vm111_raw) {
        this.vm111_raw = vm111_raw;
    }

    public Map<Pair<Integer, Integer>, JaccardCoefficientRaw> getVm112_raw() {
        return vm112_raw;
    }

    public void setVm112_raw(Map<Pair<Integer, Integer>, JaccardCoefficientRaw> vm112_raw) {
        this.vm112_raw = vm112_raw;
    }

    public Map<Integer, Double> getVm101() {
        return vm101;
    }

    public void setVm101(Map<Integer, Double> vm101) {
        this.vm101 = vm101;
    }

    public Map<Integer, Double> getVm102() {
        return vm102;
    }

    public void setVm102(Map<Integer, Double> vm102) {
        this.vm102 = vm102;
    }

    public Map<Integer, JaccardCoefficientRaw> getVm101_raw() {
        return vm101_raw;
    }

    public void setVm101_raw(Map<Integer, JaccardCoefficientRaw> vm101_raw) {
        this.vm101_raw = vm101_raw;
    }

    public Map<Integer, JaccardCoefficientRaw> getVm102_raw() {
        return vm102_raw;
    }

    public void setVm102_raw(Map<Integer, JaccardCoefficientRaw> vm102_raw) {
        this.vm102_raw = vm102_raw;
    }

}
