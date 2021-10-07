/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmasteryproject;

import com.sg.flooringmasteryproject.controller.FlooringMasteryController;
import com.sg.flooringmasteryproject.dao.FlooringMasteryPersistenceException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author roysk93
 */
public class App {

    public static void main(String[] args) throws FlooringMasteryPersistenceException {
        
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        FlooringMasteryController controller = context.getBean("flooringMasteryController", 
                FlooringMasteryController.class);
        controller.run();
        
        /*
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
        appContext.scan("com.sg.flooringmasteryproject");
        appContext.refresh();
        
        FlooringMasteryController controller = appContext.getBean("flooringMasteryController", 
                FlooringMasteryController.class);
        controller.run(); */
        
        /*
        UserIO io = new UserIOConsoleImpl();
        FlooringMasteryView view = new FlooringMasteryView(io);
        FlooringMasteryOrderDao orderData = new FlooringMasteryOrderDaoFileImpl();
        FlooringMasteryTaxDao taxData = new FlooringMasteryTaxDaoFileImpl();
        FlooringMasteryMaterialDao materialData = new FlooringMasteryMaterialDaoFileImpl();
        FlooringMasteryAuditDao auditDao = new FlooringMasteryAuditDaoFileImpl();
        FlooringMasteryServiceLayer service = new FlooringMasteryServiceLayerFileImpl(orderData, materialData, taxData, auditDao);
        FlooringMasteryController controller = new FlooringMasteryController(service, view);
        controller.run();*/

    }

}
