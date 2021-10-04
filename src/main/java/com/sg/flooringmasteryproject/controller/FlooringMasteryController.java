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
import com.sg.flooringmasteryproject.servicelayer.OrderNonexistentException;
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

        LocalDate date;
        int menuSelection;

        List<Tax> states = serviceLayer.getAllStates();
        List<Material> materials = serviceLayer.getAllMaterials();

        while (keepGoing) {

            try {
                //menuSelection = getMenuSelection();
                menuSelection = view.printMenuAndGetSelection();
                switch (menuSelection) {
                    case 1:
                        
                    try {

                        date = view.getUserDate("Enter date to view orders: ");

                        List<Order> orders = serviceLayer.getAllOrders(date);

                        view.displayOrders(orders);

                    } catch (NoSavedOrdersException e) {
                        view.displayErrorMessage(e.getMessage());
                    }

                    //displayOrders(pass in the local date object from user);
                    break;

                    case 2:
                        //addOrder();
                        date = view.getFutureDate("Enter date to add order: ");

                        String[] orderInfo = view.getNewOrderInfo(materials, states);

                        BigDecimal area = view.getArea("Enter square ft greater than 100: ");

                        Order unconfirmedOrder = serviceLayer.getUnconfirmedOrder(date, orderInfo[0], orderInfo[1], orderInfo[2], area);
                        
                        if(view.getConfirmation("Are you sure you want to add this order?", unconfirmedOrder)) {
                            serviceLayer.addOrder(date, orderInfo[0], orderInfo[1], orderInfo[2], area);
                        }
                        break;

                    case 3:
                        //editOrder();

                        try {

                        date = view.getFutureDate("Enter date to edit order: ");

                        List<Order> orders = serviceLayer.getAllOrders(date);
                        view.displayOrders(orders);

                        Order orderToEdit = serviceLayer.getOrder(date, view.getOrderNumberToEdit(orders));
                        BigDecimal editedArea = view.getEditedArea(orderToEdit.getArea());

                        String[] editInfo = view.getEditedOrderInfo(orderToEdit, materials, states);
                        unconfirmedOrder = serviceLayer.getUnconfirmedOrder(date, editInfo[0], editInfo[1], editInfo[2], editedArea,
                                orderToEdit.getOrderNumber());
                        
                        if(view.getConfirmation("Are you sure you want to edit this order?", unconfirmedOrder)) {
                            serviceLayer.editOrder(date, editInfo[0], editInfo[1], editInfo[2], editedArea,
                                orderToEdit.getOrderNumber());
                        }

                    } catch (NoSavedOrdersException | OrderNonexistentException e) {
                        view.displayErrorMessage(e.getMessage());
                    }
                    break;

                    case 4:
                    //removeOrder();
                        try {
                        date = view.getFutureDate("Enter date to remove order: ");
                        
                        List<Order> orders = serviceLayer.getAllOrders(date);
                        view.displayOrders(orders);
                        int orderNumber = view.getOrderNumberToRemove(orders);
                                
                        if(view.getConfirmation("Are you sure you want to remove this order?", 
                                serviceLayer.getOrder(date, orderNumber))) {
                            serviceLayer.removeOrder(date, orderNumber);
                        }
                        
                        
                        } catch(NoSavedOrdersException | OrderNonexistentException e) {
                            view.displayErrorMessage(e.getMessage());
                        }
                        break;
                        
                    case 5:
                    //exportAllData();

                    case 6:
                        keepGoing = false;
                        break;
                    default:
                    //unknownCommand();
                }

            } catch (FlooringMasteryPersistenceException e) {
                view.displayErrorMessage(e.getMessage());

            }

        }

    }
}
