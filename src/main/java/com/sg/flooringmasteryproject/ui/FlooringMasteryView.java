/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmasteryproject.ui;

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
public class FlooringMasteryView {
    
    private UserIO io;
    
    public FlooringMasteryView(UserIO io) {
        this.io = io;
    }
    
    public int printMenuAndGetSelection() {
        io.print("<<Flooring Program>>");
        io.print("1. Display Orders");
        io.print("2. Add an Order");
        io.print("3. Edit an Order");
        io.print("4. Remove an Order");
        io.print("5. Export all Data");
        io.print("6. Quit");
        
        return io.readInt("Please select from the above choices. ", 1, 6);
    }
    
    public void displayOrders(List<Order> orders) {

        for (Order order : orders) {
            
            String orderSummary = order.getOrderNumber() + " " + order.getCustomerName() + " " +
                    order.getProductType() + " " + order.getTotal();

            io.print(orderSummary);
            
        }

    }
    
    public LocalDate getUserDate(String message) {
        LocalDate userDate;

        userDate = io.readLocalDate(message);

        return userDate;
    }
    
    public String[] getNewOrderInfo(List<Material> materials, List<Tax> States) {
        String userName = io.readString("Enter name: ");
        String stateAbbr = io.readString("Enter state abbreviation (format TX): ");
        String material = io.readString("Enter material: ");
        
        String[] resultArray = {stateAbbr, userName, material};
        
        return resultArray;
    }
    
    public BigDecimal getArea(String message) {
        BigDecimal area;

        do {
            area = io.readBigDecimal(message);
        } while (area.compareTo(new BigDecimal("100")) < 0);

        return area;

    }
    
    public int getOrderNumberToEdit(List<Order> orders) {
        
        int selection;
        
        do {
            String message = "Enter order number to edit:";
            selection = io.readInt(message);
        } while(validateOrderNumber(orders, selection));
        
        
        return selection;

    }
    
    public boolean validateOrderNumber(List<Order> orders, int orderNumber) {
        
        for(Order order : orders) {
            
            if(order.getOrderNumber() == orderNumber) {
                return true;
            }
            
        }
        
        return false;
    }
    
    public void displayExitBanner() {
        io.print("Good Bye!!!");
    }
    
    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!");
    }
    
    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }
    
}
