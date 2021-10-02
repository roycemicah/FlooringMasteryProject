/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmasteryproject.servicelayer;

import com.sg.flooringmasteryproject.dao.FlooringMasteryMaterialDaoFileImpl;
import com.sg.flooringmasteryproject.dao.FlooringMasteryOrderDaoFileImpl;
import com.sg.flooringmasteryproject.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmasteryproject.dao.FlooringMasteryTaxDaoFileImpl;
import com.sg.flooringmasteryproject.dto.Material;
import com.sg.flooringmasteryproject.dto.Order;
import com.sg.flooringmasteryproject.dto.Tax;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author roysk93
 */
public class FlooringMasteryServiceLayerFileImpl {
    
    private FlooringMasteryOrderDaoFileImpl orderDao;
    private FlooringMasteryMaterialDaoFileImpl materialDao;
    private FlooringMasteryTaxDaoFileImpl taxDao;

    public FlooringMasteryServiceLayerFileImpl(FlooringMasteryOrderDaoFileImpl orderDao, 
            FlooringMasteryMaterialDaoFileImpl materialDao, FlooringMasteryTaxDaoFileImpl taxDao) {
        this.orderDao = orderDao;
        this.materialDao = materialDao;
        this.taxDao = taxDao;
    }
    
    public List<Order> getAllOrders(LocalDate date) throws FlooringMasteryPersistenceException, NoSavedOrdersException {
        
        String stringDate = dateToString(date);
        
        List<Order> orders;
        
        try {
            orders = orderDao.getOrders(stringDate);
        } catch(FlooringMasteryPersistenceException e) {
            throw new NoSavedOrdersException("No orders found from this date!", e);
        }
        
        return orders;

    }
    
    public Order addOrder(LocalDate date, String stateAbbr, String name, String productType, 
            BigDecimal area) throws FlooringMasteryPersistenceException {
        
        String dateString = this.dateToString(date);
        
        int orderNumber;
        List<Order> orders;
        
        try {
            orders = this.getAllOrders(date);
            orderNumber = this.getNextOrderNumber(orders);
        } catch(NoSavedOrdersException e) {
            orderNumber = 1;
        }
        
        Tax state = this.getState(stateAbbr);
        Material material = this.getMaterial(productType);
        area = area.setScale(2, RoundingMode.HALF_UP);
        
        BigDecimal materialCost = material.getCostPerSquareFoot().multiply(area);
        BigDecimal labourCost = material.getLabourCostPerSquareFoot();
        BigDecimal taxRate = state.getTaxRate().divide(new BigDecimal("100"));
        BigDecimal tax = materialCost.add(labourCost).multiply(taxRate);
        BigDecimal total = materialCost.add(labourCost).add(tax);
                
        Order newOrder = new Order(orderNumber, name, state.getStateName(), state.getTaxRate(), 
                material.getProductType(), area, material.getCostPerSquareFoot(), material.getLabourCostPerSquareFoot(), 
                materialCost, labourCost, tax, total);
        
        return orderDao.addOrder(newOrder, dateString);
        
    }
    
    public Order getOrder(LocalDate date, int orderNumber) throws FlooringMasteryPersistenceException {
        String dateString = this.dateToString(date);
        return orderDao.getOrder(dateString, orderNumber);
    }
    
    private int getNextOrderNumber(List<Order> orders) {
        int orderNumber = 1;
        
        for (Order order : orders) {
            if (orderNumber >= order.getOrderNumber()) {
                orderNumber = order.getOrderNumber() + 1;
            }
        }
        
        return orderNumber;
    }
    
    /*
    public Order processOrder(LocalDate date) throws FlooringMasteryPersistenceException {
        
        Order newOrder = new Order
        
        String stringDate = dateToString(date);
        orderDao.addOrder(stringDate);
        
    } */
    
    public Material getMaterial(String material) throws FlooringMasteryPersistenceException {
        
        return materialDao.getMaterial(material);
        
    }
    
    public List<Material> getAllMaterials() throws FlooringMasteryPersistenceException {
        return materialDao.getAllMaterials();
    }
    
    public Tax getState(String stateAbbr) throws FlooringMasteryPersistenceException {
        
        return taxDao.getState(stateAbbr);
        
    }
    
    public List<Tax> getAllStates() throws FlooringMasteryPersistenceException {
        
        return taxDao.getTaxes();
                
    }
    
    private String dateToString(LocalDate date) {
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
        return date.format(formatter);
        
    }

}
