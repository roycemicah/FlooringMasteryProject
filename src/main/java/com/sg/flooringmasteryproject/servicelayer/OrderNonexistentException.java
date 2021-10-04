/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmasteryproject.servicelayer;

/**
 *
 * @author roysk93
 */
public class OrderNonexistentException extends Exception {
    
    public OrderNonexistentException(String message) {
        super(message);
    }
    
    public OrderNonexistentException(String message, Throwable cause) {
        super(message, cause);
    }

}
