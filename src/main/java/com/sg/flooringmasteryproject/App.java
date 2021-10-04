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

    }

}
