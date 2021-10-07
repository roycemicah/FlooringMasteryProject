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
 * FINAL COPY
 * @author Royce Rabanal
 * GitHub: https://github.com/roycemicah
 * Email: royce.rabanal93@gmail.com
 * Date: October 7th, 2021
 * Purpose: TSG Flooring Mastery Project
 */
public class App {

    public static void main(String[] args) throws FlooringMasteryPersistenceException {
        
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        FlooringMasteryController controller = context.getBean("flooringMasteryController", 
                FlooringMasteryController.class);
        controller.run();

    }

}
