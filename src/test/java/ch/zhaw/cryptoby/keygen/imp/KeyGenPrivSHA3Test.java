/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.zhaw.cryptoby.keygen.imp;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Toby
 */
public class KeyGenPrivSHA3Test {
    
    public KeyGenPrivSHA3Test() {
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
     * Test of generateKey method, of class KeyGenPrivSHA3.
     */
    @Test
    public void testGenerateKey_int_random_length224() {
        System.out.println("generateKey");
        int keySize = 224;
        KeyGenPrivSHA3 instance = new KeyGenPrivSHA3();
        int expResult = 224;
        int result = instance.generateKey(keySize).getBytes().length*4;
        System.out.println(result);
        assertEquals(expResult, result);
    }
    
     /**
     * Test of generateKey method, of class KeyGenPrivSHA3.
     */
    @Test
    public void testGenerateKey_int_random_length256() {
        System.out.println("generateKey");
        int keySize = 256;
        KeyGenPrivSHA3 instance = new KeyGenPrivSHA3();
        int expResult = 256;
        int result = instance.generateKey(keySize).getBytes().length*4;
        System.out.println(result);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of generateKey method, of class KeyGenPrivSHA3.
     */
    @Test
    public void testGenerateKey_int_random_length384() {
        System.out.println("generateKey");
        int keySize = 384;
        KeyGenPrivSHA3 instance = new KeyGenPrivSHA3();
        int expResult = 384;
        int result = instance.generateKey(keySize).getBytes().length*4;
        System.out.println(result);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of generateKey method, of class KeyGenPrivSHA3.
     */
    @Test
    public void testGenerateKey_int_random_length512() {
        System.out.println("generateKey");
        int keySize = 512;
        KeyGenPrivSHA3 instance = new KeyGenPrivSHA3();
        int expResult = 512;
        int result = instance.generateKey(keySize).getBytes().length*4;
        System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of generateKey method, of class KeyGenPrivSHA3.
     */
    @Test
    public void testGenerateKey_int_String() {
        System.out.println("generateKey");
        int keySize = 256;
        String password = "testest";
        KeyGenPrivSHA3 instance = new KeyGenPrivSHA3();
        String expResult = "E195622D04525E14469076F4175B990A72995EA7C9F379C465670C330B4F8B60";
        String result = instance.generateKey(keySize, password);
        System.out.println(result);
        assertEquals(expResult, result);
    }
    
}
