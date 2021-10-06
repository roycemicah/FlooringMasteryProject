/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmasteryproject.servicelayer;

import com.sg.flooringmasteryproject.dao.FlooringMasteryMaterialDao;
import com.sg.flooringmasteryproject.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmasteryproject.dto.Material;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author roysk93
 */
public class FlooringMasteryMaterialDaoStubImpl implements FlooringMasteryMaterialDao {

    public Material onlyMaterial;

    public FlooringMasteryMaterialDaoStubImpl() {
        String productType = "Wood";
        BigDecimal costPerSquareFoot = new BigDecimal("5.00").setScale(2, RoundingMode.HALF_UP);
        BigDecimal labourCostPerSquareFoot = new BigDecimal("10.00").setScale(2, RoundingMode.HALF_UP);

        onlyMaterial = new Material(productType, costPerSquareFoot, labourCostPerSquareFoot);

    }

    public FlooringMasteryMaterialDaoStubImpl(Material testMaterial) {
        this.onlyMaterial = testMaterial;
    }

    @Override
    public List<Material> getAllMaterials() throws FlooringMasteryPersistenceException {

        List<Material> materials = new ArrayList<>();
        materials.add(onlyMaterial);
        return materials;

    }

    @Override
    public Material getMaterial(String productType) throws FlooringMasteryPersistenceException {

        if (productType.equals(onlyMaterial.getProductType())) {
            return onlyMaterial;
        } else {
            return null;
        }

    }

}
