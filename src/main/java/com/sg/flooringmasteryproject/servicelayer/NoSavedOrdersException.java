/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmasteryproject.servicelayer;

/**
 * FINAL COPY
 * @author Royce Rabanal
 * GitHub: https://github.com/roycemicah
 * Email: royce.rabanal93@gmail.com
 * Date: October 7th, 2021
 * Purpose: TSG Flooring Mastery Project
 */
public class NoSavedOrdersException extends Exception {

    public NoSavedOrdersException(String message) {
        super(message);
    }

    public NoSavedOrdersException(String message, Throwable cause) {
        super(message, cause);
    }

}