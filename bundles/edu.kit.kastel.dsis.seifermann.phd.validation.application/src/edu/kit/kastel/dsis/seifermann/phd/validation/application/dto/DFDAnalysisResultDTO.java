package edu.kit.kastel.dsis.seifermann.phd.validation.application.dto;

public class DFDAnalysisResultDTO {

    private DFDModelAnalysisResultDTO dfdWithoutIssue;
    private DFDModelAnalysisResultDTO dfdWithIssue;

    public DFDModelAnalysisResultDTO getDfdWithoutIssue() {
        return dfdWithoutIssue;
    }

    public void setDfdWithoutIssue(DFDModelAnalysisResultDTO dfdWithoutIssue) {
        this.dfdWithoutIssue = dfdWithoutIssue;
    }

    public DFDModelAnalysisResultDTO getDfdWithIssue() {
        return dfdWithIssue;
    }

    public void setDfdWithIssue(DFDModelAnalysisResultDTO dfdWithIssue) {
        this.dfdWithIssue = dfdWithIssue;
    }

}
