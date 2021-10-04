/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmasteryproject.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author roysk93
 */
public class Tax {

    private String stateAbbr;
    private String stateName;
    private BigDecimal taxRate;

    public Tax(String stateAbbr, String stateName, BigDecimal taxRate) {

        this.stateAbbr = stateAbbr;
        this.stateName = stateName;
        this.taxRate = taxRate.setScale(2, RoundingMode.HALF_UP);

    }

    public String getStateAbbr() {
        return this.stateAbbr;
    }

    public String getStateName() {
        return this.stateName;
    }

    public BigDecimal getTaxRate() {
        return this.taxRate;
    }

}
