/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmasteryproject.dao;

import com.sg.flooringmasteryproject.dto.Order;
import java.util.List;

/**
 * FINAL COPY
 * @author Royce Rabanal
 * GitHub: https://github.com/roycemicah
 * Email: royce.rabanal93@gmail.com
 * Date: October 7th, 2021
 * Purpose: TSG Flooring Mastery Project
 */
public interface FlooringMasteryOrderDao {

    Order addOrder(Order order, String date) throws FlooringMasteryPersistenceException;

    Order editOrder(Order order, String date) throws FlooringMasteryPersistenceException;

    Order removeOrder(String date, int orderNumber) throws FlooringMasteryPersistenceException;

    List<Order> getOrders(String date) throws FlooringMasteryPersistenceException;

    Order getOrder(String dateString, int orderNumber) throws FlooringMasteryPersistenceException;
       
}
