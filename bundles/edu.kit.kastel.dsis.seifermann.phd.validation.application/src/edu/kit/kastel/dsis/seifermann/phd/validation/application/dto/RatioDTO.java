package edu.kit.kastel.dsis.seifermann.phd.validation.application.dto;

import java.io.Serializable;

public class RatioDTO implements Serializable {

    private static final long serialVersionUID = -7608982409522737010L;

    protected final int amount;
    protected final int total;

    public RatioDTO(int amount, int total) {
        super();
        this.amount = amount;
        this.total = total;
    }

    public int getAmount() {
        return amount;
    }

    public int getTotal() {
        return total;
    }

}
