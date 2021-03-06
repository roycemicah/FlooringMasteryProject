/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmasteryproject.servicelayer;

import com.sg.flooringmasteryproject.dao.FlooringMasteryAuditDao;
import com.sg.flooringmasteryproject.dao.FlooringMasteryMaterialDao;
import com.sg.flooringmasteryproject.dao.FlooringMasteryOrderDao;
import com.sg.flooringmasteryproject.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmasteryproject.dao.FlooringMasteryTaxDao;
import com.sg.flooringmasteryproject.dto.Material;
import com.sg.flooringmasteryproject.dto.Order;
import com.sg.flooringmasteryproject.dto.Tax;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * FINAL COPY
 * @author Royce Rabanal
 * GitHub: https://github.com/roycemicah
 * Email: royce.rabanal93@gmail.com
 * Date: October 7th, 2021
 * Purpose: TSG Flooring Mastery Project
 */
@Component
public class FlooringMasteryServiceLayerFileImpl implements FlooringMasteryServiceLayer {

    private final FlooringMasteryOrderDao orderDao;
    private final FlooringMasteryMaterialDao materialDao;
    private final FlooringMasteryTaxDao taxDao;
    private final FlooringMasteryAuditDao auditDao;
    
    @Autowired
    public FlooringMasteryServiceLayerFileImpl(FlooringMasteryOrderDao orderDao,
            FlooringMasteryMaterialDao materialDao, FlooringMasteryTaxDao taxDao,
            FlooringMasteryAuditDao auditDao) {

        this.orderDao = orderDao;
        this.materialDao = materialDao;
        this.taxDao = taxDao;
        this.auditDao = auditDao;

    }

    @Override
    public List<Order> getAllOrders(LocalDate date) throws FlooringMasteryPersistenceException, NoSavedOrdersException {

        String stringDate = dateToString(date);

        List<Order> orders;

        try {
            orders = orderDao.getOrders(stringDate);
        } catch (FlooringMasteryPersistenceException e) {
            throw new NoSavedOrdersException("No orders found from this date!", e);
        }

        if (orders.isEmpty()) {
            throw new NoSavedOrdersException("No orders found from this date!");
        }

        return orders;

    }

    @Override
    public Order addOrder(LocalDate date, String stateAbbr, String name, String productType,
            BigDecimal area) throws FlooringMasteryPersistenceException {

        String dateString = this.dateToString(date);

        int orderNumber;
        List<Order> orders;

        try {
            orders = this.getAllOrders(date);
            orderNumber = this.getNextOrderNumber(orders);
        } catch (NoSavedOrdersException e) {
            orderNumber = 1;
        }

        Order newOrder = createOrder(stateAbbr, name, productType, area);

        newOrder.setOrderNumber(orderNumber);

        auditDao.writeAuditEntry("A new order request has been placed for " + date);

        return orderDao.addOrder(newOrder, dateString);

    }

    @Override
    public Order editOrder(LocalDate date, String stateAbbr, String name, String productType,
            BigDecimal area, int orderNumber) throws FlooringMasteryPersistenceException,
            OrderNonexistentException, NoSavedOrdersException {

        String dateString = this.dateToString(date);

        Order orderToEdit = this.getOrder(date, orderNumber);

        Tax state = this.getState(stateAbbr);
        Material material = this.getMaterial(productType);
        area = area.setScale(2, RoundingMode.HALF_UP);

        BigDecimal materialCost = material.getCostPerSquareFoot().multiply(area).setScale(2, RoundingMode.HALF_UP);
        BigDecimal labourCost = material.getLabourCostPerSquareFoot().multiply(area).setScale(2, RoundingMode.HALF_UP);
        BigDecimal taxRate = state.getTaxRate().setScale(2, RoundingMode.HALF_UP);
        BigDecimal tax = materialCost.add(labourCost).multiply(taxRate.divide(new BigDecimal("100")));
        BigDecimal total = materialCost.add(labourCost).add(tax).setScale(2, RoundingMode.HALF_UP);

        orderToEdit.setCustomerName(name);
        orderToEdit.setProductType(productType);
        orderToEdit.setArea(area);
        orderToEdit.setCostPerSquareFoot(material.getCostPerSquareFoot());
        orderToEdit.setLabourCostPerSquareFoot(material.getLabourCostPerSquareFoot());
        orderToEdit.setMaterialCost(materialCost);
        orderToEdit.setLabourCost(labourCost);
        orderToEdit.setTaxRate(taxRate);
        orderToEdit.setTax(tax);
        orderToEdit.setTotal(total);

        auditDao.writeAuditEntry("Order number (" + orderNumber + ") for " + date + " was modified!");

        return orderDao.editOrder(orderToEdit, dateString);

    }

    @Override
    public Order removeOrder(LocalDate date, int orderNumber) throws FlooringMasteryPersistenceException,
            OrderNonexistentException, NoSavedOrdersException {

        String dateString = dateToString(date);
        Order orderToRemove = getOrder(date, orderNumber);
        auditDao.writeAuditEntry("Order number (" + orderNumber + ") placed for " + date + " removed!");
        return orderDao.removeOrder(dateString, orderNumber);

    }

    private Order createOrder(String stateAbbr, String name, String productType, BigDecimal area) throws FlooringMasteryPersistenceException {

        Tax state = this.getState(stateAbbr);
        Material material = this.getMaterial(productType);
        area = area.setScale(2, RoundingMode.HALF_UP);

        BigDecimal materialCost = material.getCostPerSquareFoot().multiply(area);
        BigDecimal labourCost = material.getLabourCostPerSquareFoot().multiply(area);
        BigDecimal taxRate = state.getTaxRate().setScale(2, RoundingMode.HALF_UP);
        BigDecimal tax = materialCost.add(labourCost).multiply(taxRate.divide(new BigDecimal("100")));
        BigDecimal total = materialCost.add(labourCost).add(tax);

        Order order = new Order(name, state.getStateAbbr(), state.getTaxRate(),
                material.getProductType(), area, material.getCostPerSquareFoot(), material.getLabourCostPerSquareFoot(),
                materialCost, labourCost, tax, total);
        
        return order;

    }

    @Override
    public Order getUnconfirmedOrder(LocalDate date, String stateAbbr, String name, String productType,
            BigDecimal area) throws FlooringMasteryPersistenceException {

        String dateString = this.dateToString(date);

        Order unconfirmedOrder = createOrder(stateAbbr, name, productType, area);

        return unconfirmedOrder;

    }

    @Override
    public Order getUnconfirmedOrder(LocalDate date, String stateAbbr, String name, String productType,
            BigDecimal area, int orderNumber) throws FlooringMasteryPersistenceException {

        String dateString = this.dateToString(date);

        Order unconfirmedOrder = createOrder(stateAbbr, name, productType, area);

        unconfirmedOrder.setOrderNumber(orderNumber);

        return unconfirmedOrder;

    }

    @Override
    public Order getOrder(LocalDate date, int orderNumber) throws NoSavedOrdersException, 
            OrderNonexistentException {

        String dateString = this.dateToString(date);
        Order retrievedOrder = null;

        try {
            retrievedOrder = orderDao.getOrder(dateString, orderNumber);
        } catch (FlooringMasteryPersistenceException e) {
            throw new NoSavedOrdersException("No orders exist for this date!", e);
        }
        
        if(retrievedOrder == null) {
            throw new OrderNonexistentException("Order does not exist!");
        }

        return retrievedOrder;

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

    @Override
    public Material getMaterial(String material) throws FlooringMasteryPersistenceException {
        return materialDao.getMaterial(material);
    }

    @Override
    public List<Material> getAllMaterials() throws FlooringMasteryPersistenceException {
        return materialDao.getAllMaterials();
    }

    @Override
    public Tax getState(String stateAbbr) throws FlooringMasteryPersistenceException {

        return taxDao.getState(stateAbbr);

    }

    @Override
    public List<Tax> getAllStates() throws FlooringMasteryPersistenceException {
        return taxDao.getTaxes();
    }

    private String dateToString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
        return date.format(formatter);
    }

}
