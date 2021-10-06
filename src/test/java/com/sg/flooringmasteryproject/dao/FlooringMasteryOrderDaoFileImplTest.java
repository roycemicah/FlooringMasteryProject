/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmasteryproject.dao;

import com.sg.flooringmasteryproject.dto.Order;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author roysk93
 */
public class FlooringMasteryOrderDaoFileImplTest {

    FlooringMasteryOrderDao testDao;
    String testFilePrefix = "Test/Orders/Orders_";

    public FlooringMasteryOrderDaoFileImplTest() {
    }

    @BeforeEach
    public void setUpClass() throws Exception {

        testDao = new FlooringMasteryOrderDaoFileImpl(testFilePrefix);
    }

    @Test
    public void testAddGetOrder() throws Exception {

        int orderNumber = 1;
        String customerName = "Ada";
        String state = "FL";
        BigDecimal taxRate = new BigDecimal("4.45").setScale(2, RoundingMode.HALF_UP);
        String productType = "Marble";
        BigDecimal area = new BigDecimal("100").setScale(2, RoundingMode.HALF_UP);
        BigDecimal costPerSquareFoot = new BigDecimal("2.25").setScale(2, RoundingMode.HALF_UP);
        BigDecimal labourCostPerSquareFoot = new BigDecimal("4.00").setScale(2, RoundingMode.HALF_UP);
        BigDecimal materialCost = new BigDecimal("225.00").setScale(2, RoundingMode.HALF_UP);
        BigDecimal labourCost = new BigDecimal("400.00").setScale(2, RoundingMode.HALF_UP);
        BigDecimal tax = new BigDecimal("27.81").setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = new BigDecimal("652.81").setScale(2, RoundingMode.HALF_UP);

        Order testOrder = new Order(customerName, state, taxRate, productType,
                area, costPerSquareFoot, labourCostPerSquareFoot, materialCost, labourCost, tax, total) {
        };

        testOrder.setOrderNumber(orderNumber);
        String testDate = "10102021";
        testDao.addOrder(testOrder, testDate);

        Order retrievedOrder = testDao.getOrder(testDate, orderNumber);

        assertEquals(testOrder.getOrderNumber(), retrievedOrder.getOrderNumber(), "Checking order number.");
        assertEquals(testOrder.getCustomerName(), retrievedOrder.getCustomerName(), "Checking customer name.");
        assertEquals(testOrder.getState(), retrievedOrder.getState(), "Checking state.");
        assertEquals(testOrder.getTaxRate(), retrievedOrder.getTaxRate(), "Checking tax rate.");
        assertEquals(testOrder.getProductType(), retrievedOrder.getProductType(), "Checking material.");
        assertEquals(testOrder.getArea(), retrievedOrder.getArea(), "Checking square footage.");
        assertEquals(testOrder.getCostPerSquareFoot(), retrievedOrder.getCostPerSquareFoot(), "Checking cost per square foot.");
        assertEquals(testOrder.getLabourCostPerSquareFoot(), retrievedOrder.getLabourCostPerSquareFoot(), "Checking labour cost per square foot.");
        assertEquals(testOrder.getMaterialCost(), retrievedOrder.getMaterialCost(), "Checking material cost.");
        assertEquals(testOrder.getLabourCost(), retrievedOrder.getLabourCost(), "Checking labour cost.");
        assertEquals(testOrder.getTax(), retrievedOrder.getTax(), "Checking tax.");
        assertEquals(testOrder.getTotal(), retrievedOrder.getTotal(), "Checking total.");

        new FileWriter(testFilePrefix + testDate + ".txt");

    }

    @Test
    public void testAddEditOrder() throws Exception {

        int orderNumber = 1;
        String customerName = "Ada";
        String state = "FL";
        BigDecimal taxRate = new BigDecimal("4.45").setScale(2, RoundingMode.HALF_UP);
        String productType = "Marble";
        BigDecimal area = new BigDecimal("100").setScale(2, RoundingMode.HALF_UP);
        BigDecimal costPerSquareFoot = new BigDecimal("2.25").setScale(2, RoundingMode.HALF_UP);
        BigDecimal labourCostPerSquareFoot = new BigDecimal("4.00").setScale(2, RoundingMode.HALF_UP);
        BigDecimal materialCost = new BigDecimal("225.00").setScale(2, RoundingMode.HALF_UP);
        BigDecimal labourCost = new BigDecimal("400.00").setScale(2, RoundingMode.HALF_UP);
        BigDecimal tax = new BigDecimal("27.81").setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = new BigDecimal("652.81").setScale(2, RoundingMode.HALF_UP);

        String editedCustomerName = "Charles";
        String editedState = "NY";
        BigDecimal editedTaxRate = new BigDecimal("5.00").setScale(2, RoundingMode.HALF_UP);
        String editedProductType = "Glass";
        BigDecimal editedArea = new BigDecimal("200.00").setScale(2, RoundingMode.HALF_UP);
        BigDecimal editedCostPerSquareFoot = new BigDecimal("3.50").setScale(2, RoundingMode.HALF_UP);
        BigDecimal editedLabourCostPerSquareFoot = new BigDecimal("10.00").setScale(2, RoundingMode.HALF_UP);
        BigDecimal editedMaterialCost = new BigDecimal("700.00").setScale(2, RoundingMode.HALF_UP);
        BigDecimal editedLabourCost = new BigDecimal("2000.00").setScale(2, RoundingMode.HALF_UP);
        BigDecimal editedTax = new BigDecimal("135.00").setScale(2, RoundingMode.HALF_UP);
        BigDecimal editedTotal = new BigDecimal("2835.00").setScale(2, RoundingMode.HALF_UP);

        Order addedOrder = new Order(customerName, state, taxRate, productType, area, costPerSquareFoot,
                labourCostPerSquareFoot, materialCost, labourCost, tax, total);

        Order editedOrder = new Order(editedCustomerName, editedState, editedTaxRate, editedProductType,
                editedArea, editedCostPerSquareFoot, editedLabourCostPerSquareFoot, editedMaterialCost, editedLabourCost, editedTax, editedTotal);

        addedOrder.setOrderNumber(orderNumber);
        editedOrder.setOrderNumber(orderNumber);

        String testDate = "10102021";
        testDao.addOrder(addedOrder, testDate);

        Order retrievedOrder = testDao.editOrder(editedOrder, testDate);

        assertEquals(editedOrder.getOrderNumber(), retrievedOrder.getOrderNumber(), "Checking edited order number.");
        assertEquals(editedOrder.getCustomerName(), retrievedOrder.getCustomerName(), "Checking edited customer name.");
        assertEquals(editedOrder.getState(), retrievedOrder.getState(), "Checking edited state.");
        assertEquals(editedOrder.getTaxRate(), retrievedOrder.getTaxRate(), "Checking edited tax rate.");
        assertEquals(editedOrder.getProductType(), retrievedOrder.getProductType(), "Checking edited material.");
        assertEquals(editedOrder.getArea(), retrievedOrder.getArea(), "Checking edited square footage.");
        assertEquals(editedOrder.getCostPerSquareFoot(), retrievedOrder.getCostPerSquareFoot(), "Checking edited cost per square foot.");
        assertEquals(editedOrder.getLabourCostPerSquareFoot(), retrievedOrder.getLabourCostPerSquareFoot(), "Checking edited labour cost per square foot.");
        assertEquals(editedOrder.getMaterialCost(), retrievedOrder.getMaterialCost(), "Checking edited material cost.");
        assertEquals(editedOrder.getLabourCost(), retrievedOrder.getLabourCost(), "Checking edited labour cost.");
        assertEquals(editedOrder.getTax(), retrievedOrder.getTax(), "Checking edited tax.");
        assertEquals(editedOrder.getTotal(), retrievedOrder.getTotal(), "Checking edited total.");

        new FileWriter(testFilePrefix + testDate + ".txt");

    }

    @Test
    public void testGetAllOrders() throws Exception {

        int firstOrderNumber = 1;
        String firstCustomerName = "Ada";
        String firstState = "FL";
        BigDecimal firstTaxRate = new BigDecimal("4.45").setScale(2, RoundingMode.HALF_UP);
        String firstProductType = "Marble";
        BigDecimal firstArea = new BigDecimal("100").setScale(2, RoundingMode.HALF_UP);
        BigDecimal firstCostPerSquareFoot = new BigDecimal("2.25").setScale(2, RoundingMode.HALF_UP);
        BigDecimal firstLabourCostPerSquareFoot = new BigDecimal("4.00").setScale(2, RoundingMode.HALF_UP);
        BigDecimal firstMaterialCost = new BigDecimal("225.00").setScale(2, RoundingMode.HALF_UP);
        BigDecimal firstLabourCost = new BigDecimal("400.00").setScale(2, RoundingMode.HALF_UP);
        BigDecimal firstTax = new BigDecimal("27.81").setScale(2, RoundingMode.HALF_UP);
        BigDecimal firstTotal = new BigDecimal("652.81").setScale(2, RoundingMode.HALF_UP);

        int secondOrderNumber = 2;
        String secondCustomerName = "Charles";
        String secondState = "NY";
        BigDecimal secondTaxRate = new BigDecimal("5.00").setScale(2, RoundingMode.HALF_UP);
        String secondProductType = "Glass";
        BigDecimal secondArea = new BigDecimal("200.00").setScale(2, RoundingMode.HALF_UP);
        BigDecimal secondCostPerSquareFoot = new BigDecimal("3.50").setScale(2, RoundingMode.HALF_UP);
        BigDecimal secondLabourCostPerSquareFoot = new BigDecimal("10.00").setScale(2, RoundingMode.HALF_UP);
        BigDecimal secondMaterialCost = new BigDecimal("700.00").setScale(2, RoundingMode.HALF_UP);
        BigDecimal secondLabourCost = new BigDecimal("2000.00").setScale(2, RoundingMode.HALF_UP);
        BigDecimal secondTax = new BigDecimal("135.00").setScale(2, RoundingMode.HALF_UP);
        BigDecimal secondTotal = new BigDecimal("2835.00").setScale(2, RoundingMode.HALF_UP);

        Order firstOrder = new Order(firstCustomerName, firstState, firstTaxRate, firstProductType, firstArea, firstCostPerSquareFoot,
                firstLabourCostPerSquareFoot, firstMaterialCost, firstLabourCost, firstTax, firstTotal);

        Order secondOrder = new Order(secondCustomerName, secondState, secondTaxRate, secondProductType,
                secondArea, secondCostPerSquareFoot, secondLabourCostPerSquareFoot, secondMaterialCost, secondLabourCost, secondTax, secondTotal);

        firstOrder.setOrderNumber(firstOrderNumber);
        secondOrder.setOrderNumber(secondOrderNumber);

        String testDate = "10102021";
        testDao.addOrder(firstOrder, testDate);
        testDao.addOrder(secondOrder, testDate);

        // getting back the list of orders
        List<Order> orders = testDao.getOrders(testDate);

        assertNotNull(orders, "The list of orders should not be null!");
        assertEquals(2, orders.size(), "The list of orders should have two orders.");

        assertTrue(testDao.getOrders(testDate).contains(firstOrder), "The list of orders should contain first order.");
        assertTrue(testDao.getOrders(testDate).contains(secondOrder), "The list of orders should contain second order.");

        new FileWriter(testFilePrefix + testDate + ".txt");

    }

    @Test
    public void testRemoveOrder() throws Exception {

        int firstOrderNumber = 1;
        String firstCustomerName = "Ada";
        String firstState = "FL";
        BigDecimal firstTaxRate = new BigDecimal("4.45").setScale(2, RoundingMode.HALF_UP);
        String firstProductType = "Marble";
        BigDecimal firstArea = new BigDecimal("100").setScale(2, RoundingMode.HALF_UP);
        BigDecimal firstCostPerSquareFoot = new BigDecimal("2.25").setScale(2, RoundingMode.HALF_UP);
        BigDecimal firstLabourCostPerSquareFoot = new BigDecimal("4.00").setScale(2, RoundingMode.HALF_UP);
        BigDecimal firstMaterialCost = new BigDecimal("225.00").setScale(2, RoundingMode.HALF_UP);
        BigDecimal firstLabourCost = new BigDecimal("400.00").setScale(2, RoundingMode.HALF_UP);
        BigDecimal firstTax = new BigDecimal("27.81").setScale(2, RoundingMode.HALF_UP);
        BigDecimal firstTotal = new BigDecimal("652.81").setScale(2, RoundingMode.HALF_UP);

        int secondOrderNumber = 2;
        String secondCustomerName = "Charles";
        String secondState = "NY";
        BigDecimal secondTaxRate = new BigDecimal("5.00").setScale(2, RoundingMode.HALF_UP);
        String secondProductType = "Glass";
        BigDecimal secondArea = new BigDecimal("200.00").setScale(2, RoundingMode.HALF_UP);
        BigDecimal secondCostPerSquareFoot = new BigDecimal("3.50").setScale(2, RoundingMode.HALF_UP);
        BigDecimal secondLabourCostPerSquareFoot = new BigDecimal("10.00").setScale(2, RoundingMode.HALF_UP);
        BigDecimal secondMaterialCost = new BigDecimal("700.00").setScale(2, RoundingMode.HALF_UP);
        BigDecimal secondLabourCost = new BigDecimal("2000.00").setScale(2, RoundingMode.HALF_UP);
        BigDecimal secondTax = new BigDecimal("135.00").setScale(2, RoundingMode.HALF_UP);
        BigDecimal secondTotal = new BigDecimal("2835.00").setScale(2, RoundingMode.HALF_UP);

        Order firstOrder = new Order(firstCustomerName, firstState, firstTaxRate, firstProductType, firstArea, firstCostPerSquareFoot,
                firstLabourCostPerSquareFoot, firstMaterialCost, firstLabourCost, firstTax, firstTotal);

        Order secondOrder = new Order(secondCustomerName, secondState, secondTaxRate, secondProductType,
                secondArea, secondCostPerSquareFoot, secondLabourCostPerSquareFoot, secondMaterialCost, secondLabourCost, secondTax, secondTotal);

        firstOrder.setOrderNumber(firstOrderNumber);
        secondOrder.setOrderNumber(secondOrderNumber);

        String testDate = "10102021";
        testDao.addOrder(firstOrder, testDate);
        testDao.addOrder(secondOrder, testDate);

        Order removedOrder = testDao.removeOrder(testDate, firstOrderNumber);

        assertEquals(removedOrder, firstOrder, "The removed order should be first order.");

        List<Order> orders = testDao.getOrders(testDate);

        assertNotNull(orders, "All orders should be not null.");
        assertEquals(1, orders.size(), "All orders should only have 1 order.");

        assertFalse(orders.contains(firstOrder), "All orders should NOT include first order.");
        assertTrue(orders.contains(secondOrder), "All orders should include second order.");

        removedOrder = testDao.removeOrder(testDate, secondOrderNumber);

        assertEquals(removedOrder, secondOrder, "The removed order should be second order.");

        orders = testDao.getOrders(testDate);

        assertTrue(orders.isEmpty(), "The retrieved list of orders should be empty.");

        Order retrievedOrder = testDao.getOrder(testDate, firstOrderNumber);
        assertNull(retrievedOrder, "First order was removed, should be null.");

        retrievedOrder = testDao.getOrder(testDate, secondOrderNumber);
        assertNull(retrievedOrder, "Second order was removed, should be null.");

    }

}
