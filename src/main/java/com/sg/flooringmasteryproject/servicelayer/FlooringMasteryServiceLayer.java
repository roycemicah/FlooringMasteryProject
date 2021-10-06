/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmasteryproject.servicelayer;

import com.sg.flooringmasteryproject.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmasteryproject.dto.Material;
import com.sg.flooringmasteryproject.dto.Order;
import com.sg.flooringmasteryproject.dto.Tax;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author roysk93
 */
public interface FlooringMasteryServiceLayer {

    List<Order> getAllOrders(LocalDate date) throws FlooringMasteryPersistenceException, NoSavedOrdersException;

    Order addOrder(LocalDate date, String stateAbbr, String name, String productType,
            BigDecimal area) throws FlooringMasteryPersistenceException;

    Order editOrder(LocalDate date, String stateAbbr, String name, String productType,
            BigDecimal area, int orderNumber) throws FlooringMasteryPersistenceException,
            OrderNonexistentException, NoSavedOrdersException;

    Order removeOrder(LocalDate date, int orderNumber) throws FlooringMasteryPersistenceException,
            OrderNonexistentException, NoSavedOrdersException;

    Order getUnconfirmedOrder(LocalDate date, String stateAbbr, String name, String productType,
            BigDecimal area) throws FlooringMasteryPersistenceException;

    Order getUnconfirmedOrder(LocalDate date, String stateAbbr, String name, String productType,
            BigDecimal area, int orderNumber) throws FlooringMasteryPersistenceException;

    Order getOrder(LocalDate date, int orderNumber) throws OrderNonexistentException, NoSavedOrdersException;

    Material getMaterial(String material) throws FlooringMasteryPersistenceException;

    List<Material> getAllMaterials() throws FlooringMasteryPersistenceException;

    Tax getState(String stateAbbr) throws FlooringMasteryPersistenceException;

    List<Tax> getAllStates() throws FlooringMasteryPersistenceException;

}
