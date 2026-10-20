package com.example.databaseproject;

import android.content.Context;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class MyDBHandlerTest {

    private MyDBHandler dbHandler;
    private Context context;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        // Utilise le handler de test
        dbHandler = new MyDBHandler(context);
    }

    @After
    public void tearDown() {
        dbHandler.close();
    }

    @Test
    public void testAddAndFindProduct() {
        String testProductName = "TestLaptop";
        int testSku = 9999;

        // Supprime tout produit précédent avec ce nom
        dbHandler.deleteProduct(testProductName);

        // Test insertion
        Product product = new Product(testProductName, testSku);
        dbHandler.addProduct(product);

        // Test recherche
        Product retrieved = dbHandler.findProduct(testProductName);
        assertNotNull("Le produit devrait être trouvé en base", retrieved);
        assertEquals(testProductName, retrieved.getProductName());
        assertEquals(testSku, retrieved.getSku());
    }

    @Test
    public void testDeleteProduct() {
        String testProductName = "TestToEliminate";
        int testSku = 1234;

        Product product = new Product(testProductName, testSku);
        dbHandler.addProduct(product);

        boolean deleted = dbHandler.deleteProduct(testProductName);
        assertTrue("La suppression doit retourner true", deleted);

        Product searchAfterDelete = dbHandler.findProduct(testProductName);
        assertNull("Le produit ne doit plus être trouvé", searchAfterDelete);
    }
}
