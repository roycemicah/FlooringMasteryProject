/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmasteryproject;

import com.sg.flooringmasteryproject.controller.FlooringMasteryController;
import com.sg.flooringmasteryproject.dao.FlooringMasteryMaterialDaoFileImpl;
import com.sg.flooringmasteryproject.dao.FlooringMasteryOrderDaoFileImpl;
import com.sg.flooringmasteryproject.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmasteryproject.dao.FlooringMasteryTaxDaoFileImpl;
import com.sg.flooringmasteryproject.servicelayer.FlooringMasteryServiceLayerFileImpl;
import com.sg.flooringmasteryproject.ui.FlooringMasteryView;
import com.sg.flooringmasteryproject.ui.UserIO;
import com.sg.flooringmasteryproject.ui.UserIOConsoleImpl;

/**
 *
 * @author roysk93
 */
public class App {
    
    public static void main(String[] args) throws FlooringMasteryPersistenceException {
        
        UserIO io = new UserIOConsoleImpl();
       
        FlooringMasteryView view = new FlooringMasteryView(io);
        
        FlooringMasteryOrderDaoFileImpl orderData = new FlooringMasteryOrderDaoFileImpl();
        FlooringMasteryTaxDaoFileImpl taxData = new FlooringMasteryTaxDaoFileImpl();
        FlooringMasteryMaterialDaoFileImpl materialData = new FlooringMasteryMaterialDaoFileImpl();
        FlooringMasteryServiceLayerFileImpl service = new FlooringMasteryServiceLayerFileImpl(orderData, materialData, taxData);
        FlooringMasteryController controller = new FlooringMasteryController(service, view);
        controller.run();

        /*
        FlooringMasteryOrderDaoFileImpl orderData = new FlooringMasteryOrderDaoFileImpl();
        List<Order> orderList = orderData.getOrders("06022013");
        
        // I'd like to loop through the orderList
        for(Order item : orderList) {
            System.out.println(item.getCustomerName() + " " + item.getState() + " " + item.getTotal());
        }
        
        /* 
        
        3,Albert Einstein,KY,6.00,Carpet,217.00,2.25,2.10,488.25,455.70,56.64,1000.59
        
        

        Order myOrder = new Order(4, "Royce", "NY", new BigDecimal("6.00"), "Carpet", new BigDecimal("217.00"),
                new BigDecimal("2.25"), new BigDecimal("2.10"), new BigDecimal("488.25"), new BigDecimal("455.70"),
                new BigDecimal("56.64"), new BigDecimal("1000.59"));

        orderData.addOrder(myOrder, "06022013");
        
        FlooringMasteryTaxDaoFileImpl taxData = new FlooringMasteryTaxDaoFileImpl();
        FlooringMasteryMaterialDaoFileImpl materialData = new FlooringMasteryMaterialDaoFileImpl();
        
        System.out.println(taxData.getState("WA").getStateName());
        
        System.out.println(materialData.getMaterial("Carpet").getCostPerSquareFoot());
        */
    }

}
