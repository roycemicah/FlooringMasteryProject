/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmasteryproject.dao;

import com.sg.flooringmasteryproject.dto.Material;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * @author roysk93
 */
public class FlooringMasteryMaterialDaoFileImpl implements FlooringMasteryMaterialDao {

    private final String DELIMITER = ",";
    private final String fileName = "Data/Products.txt";
    private final List<Material> productList = new ArrayList<>();

    @Override
    public List<Material> getAllMaterials() throws FlooringMasteryPersistenceException {

        loadMaterials();
        return this.productList.stream().collect(Collectors.toList());

    }

    @Override
    public Material getMaterial(String productType) throws FlooringMasteryPersistenceException {

        loadMaterials();
        return productList.stream().filter((p) -> p.getProductType().equals(productType)).findFirst().get();

    }

    private void loadMaterials() throws FlooringMasteryPersistenceException {

        Scanner sc;

        try {
            sc = new Scanner(new BufferedReader(new FileReader(fileName)));
        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException("Could not load data!", e);
        }

        Material material;
        String currentLine;

        while (sc.hasNextLine()) {
            currentLine = sc.nextLine();
            material = unmarshallMaterial(currentLine);
            productList.add(material);
        }

    }

    private Material unmarshallMaterial(String materialData) {

        String[] fields = materialData.split(DELIMITER);

        String productType = fields[0];
        BigDecimal costPerSquareFoot = new BigDecimal(fields[1]).setScale(2, RoundingMode.HALF_UP);
        BigDecimal labourCostPerSquareFoot = new BigDecimal(fields[2]).setScale(2, RoundingMode.HALF_UP);

        return new Material(productType, costPerSquareFoot, labourCostPerSquareFoot);

    }

}
