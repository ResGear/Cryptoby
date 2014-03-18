/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.zhaw.cryptoby.prime.imp;

import java.math.BigInteger;
import java.security.SecureRandom;
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
public class MillerRabinTest { 
    
    public MillerRabinTest() {
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
     * Test of millerRabin method, of class MillerRabin.
     */
    @Test
    public void testMillerRabin() {
        System.out.println("millerRabin");
        SecureRandom random = new SecureRandom();
        BigInteger number = BigInteger.probablePrime(1000, random);
        int rounds = 10;
        boolean expResult = true;
        boolean result = MillerRabin.millerRabin(number, rounds);
        assertEquals(expResult, result);
        // positive false test
        number = BigInteger.ONE.add(number);
        expResult = false;
        result = MillerRabin.millerRabin(number, rounds);
        assertEquals(expResult, result);
    }

    /**
     * Test of getProbability method, of class MillerRabin.
     */
    @Test
    public void testGetProbability() {
        System.out.println("getProbability");
        SecureRandom random = new SecureRandom();
        MillerRabin instance = new MillerRabin(5);
        BigInteger number = BigInteger.probablePrime(1000, random);
        instance.isPrime(number);
        double expResult = 100 - (1/(Math.pow(4, 5)))*100;
        double result = instance.getProbability();
        assertEquals(expResult, result, 10^-5);
    }

    /**
     * Test of isPrime method, of class MillerRabin.
     */
    @Test
    public void testIsPrime_BigInteger() {
        System.out.println("isPrime");
        SecureRandom random = new SecureRandom();
        BigInteger number = BigInteger.probablePrime(1000, random);
        MillerRabin instance = new MillerRabin();
        boolean expResult = true;
        boolean result = instance.isPrime(number);
        assertEquals(expResult, result);
        // positive false test
        number = BigInteger.ONE.add(number);
        expResult = false;
        result = instance.isPrime(number);
        assertEquals(expResult, result);
    }
    
}
