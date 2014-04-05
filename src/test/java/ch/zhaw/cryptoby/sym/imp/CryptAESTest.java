/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.zhaw.cryptoby.sym.imp;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
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
public class CryptAESTest {
    
    public CryptAESTest() {
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
     * Test of decrypt method, of class CryptAES.
     * @throws java.security.NoSuchAlgorithmException
     */
//    @Test
//    public void testDecrypt() throws NoSuchAlgorithmException {
//        System.out.println("decrypt");
//        byte[] plainInput = "Text to Test!".getBytes();
//        byte[] key =  "B374A26A71490437AA024E4FADD5B497FDFF1A8EA6FF12F6FB65AF2720B59CCF".getBytes();
//        CryptAES instance = new CryptAES();
//        byte[] expResult = null;
//        byte[] result = instance.decrypt(plainInput, key);
//        System.out.println(new String(result));
//        assertArrayEquals(expResult, result);
//        
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of encrypt method, of class CryptAES.
     */
    @Test
    public void testEncrypt() {
        System.out.println("encrypt");
        byte[] plainInput = "Text to Test!".getBytes();
        byte[] key = "B374A26A71490437AA024E4FADD5B497FDFF1A8EA6FF12F6FB65AF2720B59CCF".getBytes();
        CryptAES instance = new CryptAES();
        byte[] expResult = null;
        byte[] result = instance.encrypt(plainInput, key);
        System.out.println(new String(result));
        result = instance.decrypt(result, key);
        System.out.println(new String(result));
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
