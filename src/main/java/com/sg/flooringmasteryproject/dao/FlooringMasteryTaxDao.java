/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmasteryproject.dao;

import com.sg.flooringmasteryproject.dto.Tax;
import java.util.List;

/**
 *
 * @author roysk93
 */
public interface FlooringMasteryTaxDao {

    List<Tax> getTaxes() throws FlooringMasteryPersistenceException;

    Tax getState(String abbreviation) throws FlooringMasteryPersistenceException;

}
