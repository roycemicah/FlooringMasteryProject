/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmasteryproject.dao;

import com.sg.flooringmasteryproject.dto.Tax;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * FINAL COPY
 * @author Royce Rabanal
 * GitHub: https://github.com/roycemicah
 * Email: royce.rabanal93@gmail.com
 * Date: October 7th, 2021
 * Purpose: TSG Flooring Mastery Project
 */
public class FlooringMasteryTaxDaoFileImplTest {

    FlooringMasteryTaxDao testDao;

    public FlooringMasteryTaxDaoFileImplTest() {
    }

    @BeforeEach
    public void setUpClass() throws Exception {

        testDao = new FlooringMasteryTaxDaoFileImpl();

    }

    @Test
    public void testGetState() throws Exception {

        List<Tax> states = testDao.getTaxes();

        for (Tax state : states) {

            Tax retrievedState = testDao.getState(state.getStateAbbr());
            assertEquals(state, retrievedState, "Retrieved stae must equal object in states.");

        }
    }

}
