package com.github.exper0;

import java.math.BigDecimal;

/**
 * @author Andrei Eliseev (aeg.exper0@gmail.com)
 */
public class TestEntry {
    private BigDecimal value;
    private int quantity;

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
