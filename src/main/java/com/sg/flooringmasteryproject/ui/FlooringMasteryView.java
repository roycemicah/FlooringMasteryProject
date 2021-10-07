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
public class FlooringMasteryView {
    
    private final UserIO io;
    
    @Autowired
    public FlooringMasteryView(UserIO io) {
        this.io = io;
    }

    public int printMenuAndGetSelection() {

        io.print("<<Flooring Program>>");
        io.print("1. Display Orders");
        io.print("2. Add an Order");
        io.print("3. Edit an Order");
        io.print("4. Remove an Order");
        io.print("5. Quit");

        return io.readInt("Please select from the above choices. ", 1, 5);

    }

    public void displayOrders(List<Order> orders) {

        for (Order order : orders) {
            String orderSummary = order.getOrderNumber() + " " + order.getCustomerName() + " "
                    + order.getProductType() + " $" + order.getTotal();

            io.print(orderSummary);
        }

    }

    public LocalDate getUserDate(String message) {

        LocalDate userDate;
        userDate = io.readLocalDate(message);

        return userDate;

    }

    private void printOrderDetails(Order order) {

        if (order.getOrderNumber() != 0) {
            io.print("Order Number: " + order.getOrderNumber());
        }

        io.print("Customer name: " + order.getCustomerName());
        io.print("Material: " + order.getProductType());
        io.print("Area in sq ft: " + order.getArea());
        io.print("Material cost per sq ft: $" + order.getCostPerSquareFoot());
        io.print("Labour cost per sq ft: $" + order.getLabourCostPerSquareFoot());
        io.print("Tax total: $" + order.getTax());
        io.print("Total price: $" + order.getTotal());

    }

    public int getOrderNumberToRemove(List<Order> orders) {

        int orderNumber = io.readInt("Enter order number to remove: ");

        while (!validateOrderNumber(orders, orderNumber)) {
            orderNumber = io.readInt("Enter a valid order number: ");
        }

        return orderNumber;

    }

    public boolean getConfirmation(String message, Order order) {

        printOrderDetails(order);
        io.print(message);
        String userResponse = io.readString("Y/N?");

        if (userResponse.equalsIgnoreCase("Y")) {
            return true;
        }

        return false;

    }

    public LocalDate getFutureDate(String message) {

        LocalDate userDate = io.readLocalDate(message);

        while (!userDate.isAfter(LocalDate.now())) {
            userDate = io.readLocalDate("Enter a date in the future: ");
        }

        return userDate;

    }

    public String[] getNewOrderInfo(List<Material> materials, List<Tax> states) {

        String userName = getValidName();

        displayStates(states);
        String stateAbbr = null;

        while (stateAbbr == null) {
            stateAbbr = io.readString("Enter state abbreviation (format TX): ");

            stateAbbr = getValidState(stateAbbr, states);

            if (stateAbbr == null) {
                displayErrorMessage("Enter an abbreviation for a VALID state");
            }
        }

        displayMaterials(materials);
        String material = null;

        while (material == null) {
            material = io.readString("Enter desired material: ");
            material = getValidMaterial(material, materials);

            if (material == null) {
                displayErrorMessage("Enter a VALID material: ");
            }
        }

        String[] resultArray = {stateAbbr, userName, material};

        return resultArray;

    }

    private String getValidName() {

        String name = io.readString("Enter name: ");

        while (!name.matches("^[A-Za-z0-9,.]+$")) {
            name = io.readString("Enter a valid name: ");
        }

        return name;

    }

    public String[] getEditedOrderInfo(Order orderToEdit, List<Material> materials,
            List<Tax> states) {

        io.print("Current name: " + orderToEdit.getCustomerName());
        String userName = getValidName();

        if (userName.isBlank()) {
            userName = orderToEdit.getCustomerName();
        }

        io.print("Current state: " + orderToEdit.getState());
        displayStates(states);
        String stateAbbr = null;

        while (stateAbbr == null) {
            stateAbbr = io.readString("Enter state abbreviation (format TX): ");

            if (stateAbbr.isBlank()) {
                stateAbbr = orderToEdit.getState();
            } else {
                stateAbbr = getValidState(stateAbbr, states);
            }

            if (stateAbbr == null) {
                displayErrorMessage("Enter an abbreviation for a VALID state");
            }
        }

        io.print("Current material: " + orderToEdit.getProductType());
        displayMaterials(materials);
        String material = null;

        while (material == null) {
            material = io.readString("Enter desired material: ");

            if (material.isBlank()) {
                material = orderToEdit.getProductType();
            } else {
                material = getValidMaterial(material, materials);
            }

        }

        String infoArray[] = {stateAbbr, userName, material};

        return infoArray;

    }

    public void displayStates(List<Tax> states) {

        String stringStates = "";
        io.print("Available states: ");

        for (int i = 0; i < states.size(); i++) {

            if (i == states.size() - 1) {
                stringStates += states.get(i).getStateAbbr();
            } else {
                stringStates += states.get(i).getStateAbbr() + ", ";
            }

        }

        io.print(stringStates);

    }

    public void displayMaterials(List<Material> materials) {

        String stringMaterials = "";
        io.print("Available materials: ");

        for (int i = 0; i < materials.size(); i++) {

            if (i == materials.size() - 1) {
                stringMaterials += materials.get(i).getProductType();
            } else {
                stringMaterials += materials.get(i).getProductType() + ", ";
            }

        }

        io.print(stringMaterials);

    }

    public BigDecimal getArea(String message) {

        BigDecimal area;

        do {
            area = io.readBigDecimal(message);
        } while (area.compareTo(new BigDecimal("100")) < 0);

        return area;

    }

    public BigDecimal getEditedArea(BigDecimal area) {

        io.print("Current area: " + area);

        return getArea("Enter new area that is 100 sq ft or greater: ");

    }

    public int getOrderNumberToEdit(List<Order> orders) {

        int selection;

        do {
            String message = "Enter order number to edit:";
            selection = io.readInt(message);
        } while (!validateOrderNumber(orders, selection));

        return selection;

    }

    private boolean validateOrderNumber(List<Order> orders, int orderNumber) {

        for (Order order : orders) {

            if (order.getOrderNumber() == orderNumber) {
                return true;
            }

        }

        return false;
    }

    private String getValidState(String stateAbbr, List<Tax> states) {

        for (Tax state : states) {

            if (state.getStateAbbr().equals(stateAbbr)) {
                return stateAbbr;
            }

        }

        return null;

    }

    private String getValidMaterial(String materialString, List<Material> materials) {

        for (Material material : materials) {

            if (material.getProductType().equals(materialString)) {
                return materialString;
            }

        }

        return null;
    }
    
    public void displayOrderAddedBanner(Order addedOrder) {
        io.print("Order No. " + addedOrder.getOrderNumber() + " added successfully!");
    }
    
    public void displayOrderNotAdded() {
        io.print("Order not added!");
    }
    
    public void displayOrderEditedBanner(int orderNumber) {
        io.print("Order No. " + orderNumber + " successfully edited!");
    }
    
    public void displayCancelEditOrder() {
        io.print("Cancelled editing order!");
    }
    
    public void displayRemoveOrderBanner(int orderNumber) {
        io.print("Order No. " + orderNumber + " successfully removed!");
    }
    
    public void displayCancelRemoveOrder() {
        io.print("Remove order cancelled!");
    }

    public void displayExitBanner() {
        io.print("Good Bye!");
    }

    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!");
    }

    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }

}
