/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmasteryproject.dao;

import com.sg.flooringmasteryproject.dto.Tax;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 *
 * @author roysk93
 */
@Component
public class FlooringMasteryTaxDaoFileImpl implements FlooringMasteryTaxDao {

    private final String DELIMITER = ",";
    private final String fileName = "Data/Taxes.txt";
    private final List<Tax> taxes = new ArrayList<>();

    @Override
    public List<Tax> getTaxes() throws FlooringMasteryPersistenceException {

        loadTaxes();
        return this.taxes.stream().collect(Collectors.toList());

    }

    private void loadTaxes() throws FlooringMasteryPersistenceException {

        Scanner sc;

        try {
            sc = new Scanner(new BufferedReader(new FileReader(fileName)));
        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException("Could not load data!", e);
        }

        Tax tax;
        String currentLine;

        while (sc.hasNextLine()) {
            currentLine = sc.nextLine();
            tax = unmarshallTaxes(currentLine);
            taxes.add(tax);
        }

    }

    private Tax unmarshallTaxes(String taxData) {

        String[] fields = taxData.split(DELIMITER);

        String stateAbbr = fields[0];
        String stateName = fields[1];
        BigDecimal taxRate = new BigDecimal(fields[2]).setScale(2, RoundingMode.HALF_UP);

        return new Tax(stateAbbr, stateName, taxRate);

    }

    @Override
    public Tax getState(String abbreviation) throws FlooringMasteryPersistenceException {

        loadTaxes();
        return taxes.stream().filter((p) -> p.getStateAbbr().equals(abbreviation)).findFirst().get();

    }

}
