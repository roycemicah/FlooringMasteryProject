/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmasteryproject.servicelayer;

import com.sg.flooringmasteryproject.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmasteryproject.dao.FlooringMasteryTaxDao;
import com.sg.flooringmasteryproject.dto.Tax;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author roysk93
 */
public class FlooringMasteryTaxDaoStubImpl implements FlooringMasteryTaxDao {

    public Tax onlyState;

    public FlooringMasteryTaxDaoStubImpl() {

        String stateAbbr = "FL";
        String stateName = "Florida";
        BigDecimal taxRate = new BigDecimal("4.45").setScale(2, RoundingMode.HALF_UP);

        onlyState = new Tax(stateAbbr, stateName, taxRate);

    }

    public FlooringMasteryTaxDaoStubImpl(Tax testState) {
        this.onlyState = testState;
    }

    @Override
    public List<Tax> getTaxes() throws FlooringMasteryPersistenceException {
        List<Tax> taxes = new ArrayList<>();
        taxes.add(onlyState);
        return taxes;
    }

    @Override
    public Tax getState(String abbreviation) throws FlooringMasteryPersistenceException {

        if (abbreviation.equals(onlyState.getStateAbbr())) {
            return onlyState;
        } else {
            return null;
        }
    }

}
