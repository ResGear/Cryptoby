/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.zhaw.cryptoby.ui.imp;

import ch.zhaw.cryptoby.client.CryptobyClient;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Toby
 */
public class CryptobyConsoleTest {
    
    public CryptobyConsoleTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of startUI method, of class CryptobyConsole.
     */
    @Test
    public void testStartUI() {
        System.out.println("startUI");
        CryptobyClient client = new CryptobyClient();
        CryptobyConsole instance = new CryptobyConsole(client.getCore());
//        instance.startUI();
    }
    
}
