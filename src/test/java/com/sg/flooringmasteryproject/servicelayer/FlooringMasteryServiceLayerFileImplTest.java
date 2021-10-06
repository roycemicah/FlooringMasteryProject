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
import com.sg.flooringmasteryproject.dto.Order;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author roysk93
 */
public class FlooringMasteryServiceLayerFileImplTest {

    private FlooringMasteryServiceLayer serviceLayer;

    public FlooringMasteryServiceLayerFileImplTest() {
        FlooringMasteryOrderDao orderDao = new FlooringMasteryOrderDaoStubImpl();
        FlooringMasteryMaterialDao materialDao = new FlooringMasteryMaterialDaoStubImpl();
        FlooringMasteryAuditDao auditDao = new FlooringMasteryAuditDaoStubImpl();
        FlooringMasteryTaxDao taxDao = new FlooringMasteryTaxDaoStubImpl();

        this.serviceLayer = new FlooringMasteryServiceLayerFileImpl(orderDao, materialDao, taxDao, auditDao);
    }

    @Test
    public void testAddOrder() throws Exception {

        String customerName = "Ada";
        String state = "FL";
        String productType = "Wood";
        BigDecimal area = new BigDecimal("100").setScale(2, RoundingMode.HALF_UP);
        LocalDate date = LocalDate.parse("2021-10-10");

        try {
            serviceLayer.addOrder(date, state, customerName, productType, area);
        } catch (FlooringMasteryPersistenceException e) {
            fail("Order was valid. No exception should have been thrown.");
        }

    }

    @Test
    public void getOrder() throws Exception {

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

        Order testOrder = new Order(customerName, state, taxRate, productType,
                area, costPerSquareFoot, labourCostPerSquareFoot, materialCost, labourCost, tax, total);

        testOrder.setOrderNumber(orderNumber);
        LocalDate date = LocalDate.parse("2021-10-10");

        Order retrievedOrder = serviceLayer.getOrder(date, testOrder.getOrderNumber());

        assertNotNull(retrievedOrder, "Getting order number 1 should not be null.");
        assertEquals(testOrder, retrievedOrder, "Order stored under order number 1 should be order number 1");

        try {
            Order shouldThrowError = serviceLayer.getOrder(date, 17);
            fail("Nonexistent order should have thrown OrderNonexistentException!");
        } catch (OrderNonexistentException e) {
            return;
        }

    }

    @Test
    public void getAllOrders() throws Exception {

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

        Order testOrder = new Order(customerName, state, taxRate, productType,
                area, costPerSquareFoot, labourCostPerSquareFoot, materialCost, labourCost, tax, total);

        testOrder.setOrderNumber(orderNumber);
        LocalDate date = LocalDate.parse("2021-10-10");

        assertEquals(1, serviceLayer.getAllOrders(date).size(), "Should only have one order.");
        assertTrue(serviceLayer.getAllOrders(date).contains(testOrder), "The one order should be order number 1.");

    }

    @Test
    public void editOrder() throws Exception {

        int orderNumber = 1;
        String customerName = "Charles";
        String state = "FL";
        String productType = "Wood";
        BigDecimal area = new BigDecimal("150.00").setScale(2, RoundingMode.HALF_UP);
        LocalDate date = LocalDate.parse("2021-10-10");

        Order testOrder = serviceLayer.editOrder(date, state, customerName, productType, area, 1);

        assertEquals(1, serviceLayer.getAllOrders(date).size(), "Should only have one order!");
        assertTrue(serviceLayer.getAllOrders(date).contains(testOrder), "The one order should be order number 1.");

        assertEquals(testOrder.getArea(), area, "Updated area should be equal to 150.00");
        assertEquals(testOrder.getCustomerName(), customerName, "Updated name should be equal to Charles.");

    }

    @Test
    public void removeOrder() throws Exception {

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

        Order testOrder = new Order(customerName, state, taxRate, productType,
                area, costPerSquareFoot, labourCostPerSquareFoot, materialCost, labourCost, tax, total);

        testOrder.setOrderNumber(orderNumber);
        LocalDate date = LocalDate.parse("2021-10-10");

        Order firstOrder = serviceLayer.removeOrder(date, 1);
        assertNotNull(firstOrder, "Removing order number 1 should not be null.");
        assertEquals(testOrder, firstOrder, "Order removed should be order number 1.");

        try {
            Order shouldBeNull = serviceLayer.removeOrder(date, 17);
            fail("Removing 17 should be null.");
        } catch (OrderNonexistentException e) {
            return;
        }

    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

}
