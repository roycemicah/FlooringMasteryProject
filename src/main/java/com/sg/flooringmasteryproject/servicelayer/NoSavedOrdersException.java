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
public class NoSavedOrdersException extends Exception {

    public NoSavedOrdersException(String message) {
        super(message);
    }

    public NoSavedOrdersException(String message, Throwable cause) {
        super(message, cause);
    }

}