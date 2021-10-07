/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmasteryproject.dao;

import com.sg.flooringmasteryproject.dto.Material;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author roysk93
 */
public class FlooringMasteryMaterialDaoFileImplTest {

    FlooringMasteryMaterialDao testDao;

    public FlooringMasteryMaterialDaoFileImplTest() {
    }

    @BeforeEach
    public void setUpClass() throws Exception {

        testDao = new FlooringMasteryMaterialDaoFileImpl();

    }

    @Test
    public void testGetMaterial() throws Exception {

        List<Material> materials = testDao.getAllMaterials();

        for (Material material : materials) {

            Material retrievedMaterial = testDao.getMaterial(material.getProductType());
            assertEquals(material, retrievedMaterial, "Retrieved material must equal object in materials.");

        }

    }

}
