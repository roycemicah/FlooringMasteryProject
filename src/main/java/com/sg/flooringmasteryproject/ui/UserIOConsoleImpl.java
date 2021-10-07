/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmasteryproject.ui;

import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Scanner;
import org.springframework.stereotype.Component;

/**
 * FINAL COPY
 * @author Royce Rabanal
 * GitHub: https://github.com/roycemicah
 * Email: royce.rabanal93@gmail.com
 * Date: October 7th, 2021
 * Purpose: TSG Flooring Mastery Project
 */
@Component
public class UserIOConsoleImpl implements UserIO {

    final private Scanner sc = new Scanner(System.in);

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public String readString(String prompt) {
        System.out.println(prompt);
        return sc.nextLine();
    }

    @Override
    public int readInt(String messagePrompt) {

        boolean invalidInput = true;
        int num = 0;

        while (invalidInput) {
            try {
                String stringValue = this.readString(messagePrompt);
                num = Integer.parseInt(stringValue);
                invalidInput = false;
            } catch (NumberFormatException e) {
                this.print("Input error, please try again.");
            }
        }
        return num;

    }

    @Override
    public int readInt(String messagePrompt, int min, int max) {

        int result;

        do {
            result = readInt(messagePrompt);
        } while (result < min || result > max);

        return result;

    }

    @Override
    public long readLong(String messagePrompt) {

        while (true) {
            try {
                return Long.parseLong(this.readString(messagePrompt));
            } catch (NumberFormatException e) {
                this.print("Input error, please try again.");
            }
        }

    }

    @Override
    public long readLong(String messagePrompt, long min, long max) {

        long result;

        do {
            result = readLong(messagePrompt);
        } while (result < min || result > max);

        return result;

    }

    @Override
    public float readFloat(String messagePrompt) {

        while (true) {
            try {
                return Float.parseFloat(this.readString(messagePrompt));
            } catch (NumberFormatException e) {
                this.print("Input error, please try again.");
            }
        }

    }

    @Override
    public float readFloat(String messagePrompt, float min, float max) {

        float result;

        do {
            result = readFloat(messagePrompt);
        } while (result < min || result > max);

        return result;

    }

    @Override
    public double readDouble(String messagePrompt) {

        while (true) {
            try {
                return Double.parseDouble(this.readString(messagePrompt));
            } catch (NumberFormatException e) {
                this.print("Input error, please try again.");
            }
        }

    }

    @Override
    public double readDouble(String messagePrompt, double min, double max) {

        double result;

        do {
            result = readDouble(messagePrompt);
        } while (result < min || result > max);

        return result;

    }

    @Override
    public BigDecimal readBigDecimal(String messagePrompt) {

        BigDecimal input = null;
        boolean invalid = true;

        do {
            try {
                String inputString = this.readString(messagePrompt);

                input = new BigDecimal(inputString);
                invalid = false;
            } catch (NumberFormatException e) {
                this.print("Input error, please try again!");
            }
        } while (invalid);

        return input;

    }

    @Override
    public LocalDate readLocalDate(String prompt) {

        LocalDate date = null;
        boolean invalidInput = true;

        while (invalidInput) {

            try {
                // asking to enter a date with the prompt
                System.out.println(prompt);
                String inputDate = sc.nextLine();
                date = LocalDate.parse(inputDate);
                invalidInput = false;
            } catch (DateTimeException e) {
                this.print("Input Error: Date MUST be in the format YYYY-MM-DD");
            }

        }
        return date;

    }

}
