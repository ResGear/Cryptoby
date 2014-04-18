/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.zhaw.cryptoby.sym.imp;

import ch.zhaw.cryptoby.core.CryptobyHelper;
import ch.zhaw.cryptoby.keygen.imp.KeyGenPrivSHA3;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
    
    @Test
    public void testEncryptDecrypt256() {
        System.out.println("encrypt and decrypt testphrase");
        byte[] plainInput = "Text to Test for Testing from Tester by Testcase".getBytes();
        KeyGenPrivSHA3 keyGen = new KeyGenPrivSHA3();
        String hexKey = keyGen.generateKey(256, "password");
        byte[] bKey = CryptobyHelper.hexStringToBytes(hexKey);
        System.out.println(hexKey);
        CryptAES instance = new CryptAES();
        byte[] expResult = plainInput;
        byte[] result = instance.encrypt(plainInput, bKey);
        String resString = CryptobyHelper.bytesToHexString(result);
        System.out.println(resString);
        result = instance.decrypt(result, bKey);
        System.out.println(new String(result));
        assertArrayEquals(expResult, result);
    }
    
        @Test
        public void testEncryptDecrypt256_falseKey() {
        System.out.println("crypt false key");
        byte[] plainInput = "Text to Test for Testing from Tester by Testcase".getBytes();
        KeyGenPrivSHA3 keyGen = new KeyGenPrivSHA3();
        String hexKey = keyGen.generateKey(256, "password");
        byte[] bKey = CryptobyHelper.hexStringToBytes(hexKey);
        System.out.println(hexKey);
        CryptAES instance = new CryptAES();
        byte[] expResult = plainInput;
        byte[] result = instance.encrypt(plainInput, bKey);
        String resString = CryptobyHelper.bytesToHexString(result);
        System.out.println(resString);
        hexKey = keyGen.generateKey(256, "passwordFalse");
        bKey = CryptobyHelper.hexStringToBytes(hexKey);
        result = instance.decrypt(result, bKey);
        System.out.println(new String(result));
        assertFalse(new String(expResult).equals(new  String(result)));
    }
        
        @Test
        public void testEncryptDecrypt256_false() {
        System.out.println("crypt almost false key");
        byte[] plainInput = "Text to Test for Testing from Tester by Testcase".getBytes();
        String hexKey = "13A9489AF957FF7B5E8E712737D0B4A0C92AE8EBAE9DD11E9C11B8CB79707017";
        byte[] bKey = CryptobyHelper.hexStringToBytes(hexKey);
        System.out.println(hexKey);
        CryptAES instance = new CryptAES();
        byte[] expResult = plainInput;
        byte[] result = instance.encrypt(plainInput, bKey);
        String resString = CryptobyHelper.bytesToHexString(result);
        System.out.println(resString);
        hexKey = "13A9489AF957FF7B5E8E712737D0B4A0C92AE8EBAE9DD11E9C11B8CB79707011";
        bKey = CryptobyHelper.hexStringToBytes(hexKey);
        result = instance.decrypt(result, bKey);
        System.out.println(new String(result));
        assertFalse(new String(expResult).equals(new  String(result)));
    }
}