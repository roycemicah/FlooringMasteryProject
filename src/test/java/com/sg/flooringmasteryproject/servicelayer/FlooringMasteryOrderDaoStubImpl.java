/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmasteryproject.servicelayer;

import com.sg.flooringmasteryproject.dao.FlooringMasteryOrderDao;
import com.sg.flooringmasteryproject.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmasteryproject.dto.Order;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author roysk93
 */
public class FlooringMasteryOrderDaoStubImpl implements FlooringMasteryOrderDao {
    
    public Order onlyOrder;
    
    public FlooringMasteryOrderDaoStubImpl() {
        
        int orderNumber = 1;
        String customerName = "Ada";
        String state = "FL";
        BigDecimal taxRate = new BigDecimal("4.45").setScale(2, RoundingMode.HALF_UP);
        String productType = "Wood";
        BigDecimal area = new BigDecimal("100").setScale(2, RoundingMode.HALF_UP);
        BigDecimal costPerSquareFoot = new BigDecimal("5.00").setScale(2, RoundingMode.HALF_UP);
        BigDecimal labourCostPerSquareFoot = new BigDecimal("10.00").setScale(2, RoundingMode.HALF_UP);
        BigDecimal materialCost = new BigDecimal("500.00").setScale(2, RoundingMode.HALF_UP);
        BigDecimal labourCost = new BigDecimal("1000.00").setScale(2, RoundingMode.HALF_UP);
        BigDecimal tax = new BigDecimal("66.75").setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = new BigDecimal("1566.75").setScale(2, RoundingMode.HALF_UP);
        
        onlyOrder = new Order(customerName, state, taxRate, productType,
                area, costPerSquareFoot, labourCostPerSquareFoot, materialCost, labourCost, tax, total);
        
        onlyOrder.setOrderNumber(orderNumber);
        
    }
    
    public FlooringMasteryOrderDaoStubImpl(Order testOrder) {
        this.onlyOrder = testOrder;
    }

    @Override
    public Order addOrder(Order order, String date) throws FlooringMasteryPersistenceException {
        
        if(order.getOrderNumber() == onlyOrder.getOrderNumber() + 1) {
            return order;
        } else {
            return null;
        }
        
    }

    @Override
    public Order editOrder(Order order, String date) throws FlooringMasteryPersistenceException {
        
        if(order.getOrderNumber() == onlyOrder.getOrderNumber()) {
            return order;
        } else {
            return null;
        }
    }

    @Override
    public Order removeOrder(String date, int orderNumber) throws FlooringMasteryPersistenceException {
        
        if(orderNumber == onlyOrder.getOrderNumber()) {
            return onlyOrder;
        } else {
            return null;
        }
        
    }

    @Override
    public List<Order> getOrders(String date) throws FlooringMasteryPersistenceException {
        
        List<Order> orders = new ArrayList<>();
        orders.add(onlyOrder);
        return orders;
        
    }

    @Override
    public Order getOrder(String dateString, int orderNumber) throws FlooringMasteryPersistenceException {
        
        if(orderNumber == onlyOrder.getOrderNumber()) {
            return onlyOrder;
        } else {
            return null;
        }
        
    }
    
}
