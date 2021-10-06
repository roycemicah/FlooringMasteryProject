/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmasteryproject;

import com.sg.flooringmasteryproject.controller.FlooringMasteryController;
import com.sg.flooringmasteryproject.dao.FlooringMasteryAuditDao;
import com.sg.flooringmasteryproject.dao.FlooringMasteryAuditDaoFileImpl;
import com.sg.flooringmasteryproject.dao.FlooringMasteryMaterialDao;
import com.sg.flooringmasteryproject.dao.FlooringMasteryMaterialDaoFileImpl;
import com.sg.flooringmasteryproject.dao.FlooringMasteryOrderDao;
import com.sg.flooringmasteryproject.dao.FlooringMasteryOrderDaoFileImpl;
import com.sg.flooringmasteryproject.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmasteryproject.dao.FlooringMasteryTaxDao;
import com.sg.flooringmasteryproject.dao.FlooringMasteryTaxDaoFileImpl;
import com.sg.flooringmasteryproject.servicelayer.FlooringMasteryServiceLayer;
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
        FlooringMasteryOrderDao orderData = new FlooringMasteryOrderDaoFileImpl();
        FlooringMasteryTaxDao taxData = new FlooringMasteryTaxDaoFileImpl();
        FlooringMasteryMaterialDao materialData = new FlooringMasteryMaterialDaoFileImpl();
        FlooringMasteryAuditDao auditDao = new FlooringMasteryAuditDaoFileImpl();
        FlooringMasteryServiceLayer service = new FlooringMasteryServiceLayerFileImpl(orderData, materialData, taxData, auditDao);
        FlooringMasteryController controller = new FlooringMasteryController(service, view);
        controller.run();

    }

}
