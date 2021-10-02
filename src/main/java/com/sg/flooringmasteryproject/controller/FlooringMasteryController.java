/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmasteryproject.controller;

import com.sg.flooringmasteryproject.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmasteryproject.dto.Material;
import com.sg.flooringmasteryproject.dto.Order;
import com.sg.flooringmasteryproject.dto.Tax;
import com.sg.flooringmasteryproject.servicelayer.FlooringMasteryServiceLayerFileImpl;
import com.sg.flooringmasteryproject.servicelayer.NoSavedOrdersException;
import com.sg.flooringmasteryproject.ui.FlooringMasteryView;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author roysk93
 */
public class FlooringMasteryController {
    
    private FlooringMasteryView view;
    
    private FlooringMasteryServiceLayerFileImpl serviceLayer;
    
    public FlooringMasteryController(FlooringMasteryServiceLayerFileImpl serviceLayer, 
            FlooringMasteryView view) {
        this.view = view;
        this.serviceLayer = serviceLayer;
    }
    
    public void run() throws FlooringMasteryPersistenceException {
        
        boolean keepGoing = true;
        
        int menuSelection = view.printMenuAndGetSelection();
        LocalDate date;
        
        try {
            
            List<Tax> states = serviceLayer.getAllStates();
            List<Material> materials = serviceLayer.getAllMaterials();
            
            while(keepGoing) {
                //menuSelection = getMenuSelection();

                switch(menuSelection) {
                    case 1:
                        
                    try {
                        
                        date = view.getUserDate("Enter date to view orders: ");
                        
                        List<Order> orders = serviceLayer.getAllOrders(date);
                        
                        view.displayOrders(orders);
                        
                    } catch (NoSavedOrdersException e) {
                        view.displayErrorMessage(e.getMessage());
                    }

                        //displayOrders(pass in the local date object from user);

                    case 2:
                        //addOrder();
                        date = view.getUserDate("Enter date to add order: ");

                        String[] orderInfo = view.getNewOrderInfo(materials, states);

                        BigDecimal area = view.getArea("Enter square ft greater than 100: ");
                        
                        
                        serviceLayer.addOrder(date, orderInfo[0], orderInfo[1], orderInfo[2], area);
                        
                    case 3:
                        //editOrder();
                        
                        
                        try {

                            date = view.getUserDate("Enter date to edit order: ");
                            List<Order> orders = serviceLayer.getAllOrders(date);
                            view.displayOrders(orders);

                            Order orderToEdit = serviceLayer.getOrder(date, view.getOrderNumberToEdit(orders));
                            
                            //view.getEditedOrderInfo(orderToEdit, materials, states);

                        } catch (NoSavedOrdersException e) {
                                view.displayErrorMessage(e.getMessage());
                        }
                        
                    case 4:
                        //removeOrder();
                    case 5:
                        //exportAllData();

                    case 6:
                        keepGoing = false;
                        break;
                    default:
                        //unknownCommand();
                }
            }
            //exitMessage();
        } catch (FlooringMasteryPersistenceException e) {
          //  view.displayErrorMessage(e.getMessage());
        }
    }
    
    /*
    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }
    
    private void exitMessage() {
        view.displayExitBanner();
    }
    
    public void unknownCommand() {
        view.displayUnknownCommandBanner();
    }     */    
    
}
