package edu.kit.kastel.dsis.seifermann.phd.validation.application.dto;

import java.io.Serializable;
import java.util.Map;

public class DFDSemanticsValidationResult implements Serializable {

    private static final long serialVersionUID = -7154048534147349525L;

    private int vm61;
    private int vm62;
    private Map<Integer, DFDModelAnalysisResultDTO> vm61_raw;
    private Map<Integer, DFDModelAnalysisResultDTO> vm62_raw;

    public int getVm61() {
        return vm61;
    }

    public void setVm61(int vm61) {
        this.vm61 = vm61;
    }

    public int getVm62() {
        return vm62;
    }

    public void setVm62(int vm62) {
        this.vm62 = vm62;
    }

    public Map<Integer, DFDModelAnalysisResultDTO> getVm61_raw() {
        return vm61_raw;
    }

    public void setVm61_raw(Map<Integer, DFDModelAnalysisResultDTO> vm61_raw) {
        this.vm61_raw = vm61_raw;
    }

    public Map<Integer, DFDModelAnalysisResultDTO> getVm62_raw() {
        return vm62_raw;
    }

    public void setVm62_raw(Map<Integer, DFDModelAnalysisResultDTO> vm62_raw) {
        this.vm62_raw = vm62_raw;
    }

}
