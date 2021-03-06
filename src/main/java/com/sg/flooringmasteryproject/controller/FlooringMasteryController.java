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
import com.sg.flooringmasteryproject.servicelayer.FlooringMasteryServiceLayer;
import com.sg.flooringmasteryproject.servicelayer.NoSavedOrdersException;
import com.sg.flooringmasteryproject.servicelayer.OrderNonexistentException;
import com.sg.flooringmasteryproject.ui.FlooringMasteryView;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * FINAL COPY
 *
 * @author Royce Rabanal GitHub: https://github.com/roycemicah Email:
 * royce.rabanal93@gmail.com Date: October 7th, 2021 Purpose: TSG Flooring
 * Mastery Project
 */
@Component
public class FlooringMasteryController {

    private final FlooringMasteryView view;
    private final FlooringMasteryServiceLayer serviceLayer;

    @Autowired
    public FlooringMasteryController(FlooringMasteryServiceLayer serviceLayer,
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

                menuSelection = view.printMenuAndGetSelection();
                switch (menuSelection) {
                    case 1:
                        displayOrders();
                        break;

                    case 2:
                        addOrder(materials, states);
                        break;

                    case 3:
                        editOrder(materials, states);
                        break;

                    case 4:
                        removeOrder();
                        break;

                    case 5:
                        view.displayExitBanner();
                        keepGoing = false;
                        break;

                }

            } catch (FlooringMasteryPersistenceException e) {
                view.displayErrorMessage(e.getMessage());

            }

        }

    }

    private void displayOrders() throws FlooringMasteryPersistenceException {

        LocalDate date = view.getUserDate("Enter date to view orders: ");

        try {

            List<Order> orders = serviceLayer.getAllOrders(date);
            view.displayOrders(orders);

        } catch (NoSavedOrdersException e) {
            view.displayErrorMessage(e.getMessage());
        }

    }

    private void addOrder(List<Material> materials, List<Tax> states) throws FlooringMasteryPersistenceException {

        LocalDate date = view.getFutureDate("Enter date to add order: ");

        String[] orderInfo = view.getNewOrderInfo(materials, states);

        BigDecimal area = view.getArea("Enter square ft greater than 100: ");

        Order unconfirmedOrder = serviceLayer.getUnconfirmedOrder(date, orderInfo[0], orderInfo[1], orderInfo[2], area);

        if (view.getConfirmation("Are you sure you want to add this order?", unconfirmedOrder)) {
            Order addedOrder = serviceLayer.addOrder(date, orderInfo[0], orderInfo[1], orderInfo[2], area);
            view.displayOrderAddedBanner(addedOrder);
        } else {
            view.displayOrderNotAdded();
        }
    }

    private void editOrder(List<Material> materials, List<Tax> states) throws FlooringMasteryPersistenceException {

        try {

            LocalDate date = view.getFutureDate("Enter date to edit order: ");

            List<Order> orders = serviceLayer.getAllOrders(date);
            view.displayOrders(orders);

            Order orderToEdit = serviceLayer.getOrder(date, view.getOrderNumberToEdit(orders));
            BigDecimal editedArea = view.getEditedArea(orderToEdit.getArea());

            String[] editInfo = view.getEditedOrderInfo(orderToEdit, materials, states);
            
            Order unconfirmedOrder = serviceLayer.getUnconfirmedOrder(date, editInfo[0], editInfo[1], editInfo[2], editedArea,
                    orderToEdit.getOrderNumber());

            if (view.getConfirmation("Are you sure you want to edit this order?", unconfirmedOrder)) {
                serviceLayer.editOrder(date, editInfo[0], editInfo[1], editInfo[2], editedArea,
                        orderToEdit.getOrderNumber());
                view.displayOrderEditedBanner(orderToEdit.getOrderNumber());
            } else {
                view.displayCancelEditOrder();
            }

        } catch (NoSavedOrdersException | OrderNonexistentException e) {
            view.displayErrorMessage(e.getMessage());
        }

    }

    private void removeOrder() throws FlooringMasteryPersistenceException {

        try {
            LocalDate date = view.getFutureDate("Enter date to remove order: ");
            List<Order> orders = serviceLayer.getAllOrders(date);
            view.displayOrders(orders);
            int orderNumber = view.getOrderNumberToRemove(orders);

            if (view.getConfirmation("Are you sure you want to remove this order?",
                    serviceLayer.getOrder(date, orderNumber))) {
                serviceLayer.removeOrder(date, orderNumber);
                view.displayRemoveOrderBanner(orderNumber);
            } else {
                view.displayCancelRemoveOrder();
            }

        } catch (NoSavedOrdersException | OrderNonexistentException e) {
            view.displayErrorMessage(e.getMessage());
        }

    }

}
