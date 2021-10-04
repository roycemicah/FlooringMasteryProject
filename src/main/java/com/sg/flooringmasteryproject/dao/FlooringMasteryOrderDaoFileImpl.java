/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmasteryproject.dao;

import com.sg.flooringmasteryproject.dto.Order;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * @author roysk93
 */
public class FlooringMasteryOrderDaoFileImpl {
    
    private final String DELIMITER = ",";
    private String fileName;
    private List<Order> orderList = new ArrayList<>();
    
    private void loadData(String date) throws FlooringMasteryPersistenceException {
        Scanner sc;
        
        // the DAO will open this file
        fileName = "Orders/Orders_" + date + ".txt";
        
        // opening file...
        orderList.clear();
        
        try {   
            sc = new Scanner(new BufferedReader(new FileReader(fileName)));
        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException("Could not load data!", e);
        }
        
        Order order;
        String currentLine;
        
        while (sc.hasNextLine()) {
            currentLine = sc.nextLine();
            order = unmarshallOrder(currentLine);
            orderList.add(order);
        }
      
    }
    
    public Order addOrder(Order order, String date) throws FlooringMasteryPersistenceException {
        // clears and loads new data
        
        try {
            this.loadData(date);
        } catch (FlooringMasteryPersistenceException e) {

        }
        
        this.orderList.add(order);

        this.writeOrders(date);

        return order;
    }
    
    public Order editOrder(Order order, String date) throws FlooringMasteryPersistenceException {
        
        this.loadData(date);
        Order editedOrder = null;
        
        for(int i = 0; i < this.orderList.size(); i++) {
            if(this.orderList.get(i).getOrderNumber() == order.getOrderNumber()) {
                this.orderList.set(i, order);
                editedOrder = order;
                this.writeOrders(date);
            }
        }
        
        return editedOrder;
    }
    
    // this method is reading from the txt file
    private Order unmarshallOrder(String orderData) {
        
        String[] fields = orderData.split(DELIMITER);
        
        /*
        OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,
        LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total
        */
        
        int orderNumber = Integer.parseInt(fields[0]);
        String customerName = fields[1];
        String state = fields[2];
        BigDecimal taxRate = new BigDecimal(fields[3]).setScale(2, RoundingMode.HALF_UP);
        String productType = fields[4];
        BigDecimal area = new BigDecimal(fields[5]).setScale(2, RoundingMode.HALF_UP);
        BigDecimal costPerSquareFoot = new BigDecimal(fields[6]).setScale(2, RoundingMode.HALF_UP);
        BigDecimal labourCostPerSquareFoot = new BigDecimal(fields[7]).setScale(2, RoundingMode.HALF_UP);
        BigDecimal materialCost = new BigDecimal(fields[8]).setScale(2, RoundingMode.HALF_UP);
        BigDecimal labourCost = new BigDecimal(fields[9]).setScale(2, RoundingMode.HALF_UP);
        BigDecimal tax = new BigDecimal(fields[10]).setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = new BigDecimal(fields[11]).setScale(2, RoundingMode.HALF_UP);
        
        Order order = new Order(customerName, state, taxRate, productType, area, costPerSquareFoot, 
                         labourCostPerSquareFoot, materialCost, labourCost, tax, total);
        order.setOrderNumber(orderNumber);
        
        return order;

    }
    
    public Order removeOrder(String date, int orderNumber) throws FlooringMasteryPersistenceException {
        loadData(date);
        Order removedOrder = null;
        
        for(Order order : this.orderList) {
            if(order.getOrderNumber() == orderNumber) {
                orderList.remove(order);
                writeOrders(date);
                return order;
            }
        }
        
        return removedOrder;
        
    }

    public List<Order> getOrders(String date) throws FlooringMasteryPersistenceException {

        this.loadData(date);
        
        // returns a copy of the same list
        return orderList.stream().collect(Collectors.toList());

    }
    
    public Order getOrder(String dateString, int orderNumber) throws FlooringMasteryPersistenceException {
        
        this.loadData(dateString);
        return this.orderList.stream().filter((p) -> p.getOrderNumber() == orderNumber).findFirst().get();

    }
    
    private void writeOrders(String date) throws FlooringMasteryPersistenceException {
        
        PrintWriter out;
        
        try {
            out = new PrintWriter(new FileWriter("Orders/Orders_" + date + ".txt"));
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException("Could not persist data!", e);
        }
        
        String line;
        
        for(Order order : this.orderList) {
            line = marshallOrder(order);
            out.println(line);
            out.flush();
        }
        out.close();
        
    }
    
    private String marshallOrder(Order order) {
        
        String line = "";
        line += order.getOrderNumber() + DELIMITER;
        line += order.getCustomerName() + DELIMITER;
        line += order.getState() + DELIMITER;
        line += order.getTaxRate() + DELIMITER;
        line += order.getProductType() + DELIMITER;
        line += order.getArea() + DELIMITER;
        line += order.getCostPerSquareFoot() + DELIMITER;
        line += order.getLabourCostPerSquareFoot() + DELIMITER;
        line += order.getMaterialCost() + DELIMITER;
        line += order.getLabourCost() + DELIMITER;
        line += order.getTax() + DELIMITER;
        line += order.getTotal();
        
        return line;
    }
    
    
}
