/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.zhaw.cryptoby.asym.imp;

import ch.zhaw.cryptoby.client.CryptobyClient;
import ch.zhaw.cryptoby.core.CryptobyCore;
import ch.zhaw.cryptoby.keygen.imp.KeyGenRSA;
import java.math.BigInteger;
import java.util.Arrays;
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
public class CryptRSATest {

    public CryptRSATest() {
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
     * Test of encrypt method, of class CryptRSA.
     */
    @Test
    public void testRSACrypt1024() {
        System.out.println("RSACrypt1024");
            int keySize = 1024;
            CryptobyClient client = new CryptobyClient();
            CryptobyCore core = new CryptobyCore(client);
            KeyGenRSA generator = new KeyGenRSA(core);
            generator.initGenerator(keySize);
            byte[] plainInput = "Text to Test for Testing from Tester by Testcase".getBytes();
            String publicKeyString = generator.getPublicKey();
            byte[] publicKey = new BigInteger(publicKeyString, Character.MAX_RADIX).toByteArray();
            String privateKeyString = generator.getPrivateKey();
            byte[] privateKey = new BigInteger(privateKeyString, Character.MAX_RADIX).toByteArray();
            CryptRSA rsa = new CryptRSA();
            byte[] expResult = plainInput;
            byte[] result = rsa.encrypt(plainInput, publicKey);
            result = rsa.decrypt(result, privateKey);
            assertArrayEquals(expResult, result);
    }

    @Test
    public void testRSACrypt1024false() {
        System.out.println("RSACrypt1024false");
        int keySize = 1024;
        CryptobyClient client = new CryptobyClient();
        CryptobyCore core = new CryptobyCore(client);
        KeyGenRSA generator = new KeyGenRSA(core);
        generator.initGenerator(keySize);
        byte[] plainInput = "Text to Test for Testing from Tester by Testcase".getBytes();
        String publicKeyString = generator.getPublicKey();
        byte[] publicKey = new BigInteger(publicKeyString, Character.MAX_RADIX).toByteArray();
        generator.initGenerator(keySize);
        String privateKeyString = generator.getPrivateKey();
        byte[] privateKey = new BigInteger(privateKeyString, Character.MAX_RADIX).toByteArray();
        CryptRSA rsa = new CryptRSA();
        byte[] expResult = plainInput;
        byte[] result = rsa.encrypt(plainInput, publicKey);
        result = rsa.decrypt(result, privateKey);
        assertFalse(Arrays.equals(expResult, result));
    }

    @Test
    public void testRSACrypt2048() {
        System.out.println("RSACrypt2048");
        int keySize = 2048;
        CryptobyClient client = new CryptobyClient();
        CryptobyCore core = new CryptobyCore(client);
        KeyGenRSA generator = new KeyGenRSA(core);
        generator.initGenerator(keySize);
        byte[] plainInput = "Text to Test for Testing from Tester by Testcase".getBytes();
        String publicKeyString = generator.getPublicKey();
        byte[] publicKey = new BigInteger(publicKeyString, Character.MAX_RADIX).toByteArray();
        String privateKeyString = generator.getPrivateKey();
        byte[] privateKey = new BigInteger(privateKeyString, Character.MAX_RADIX).toByteArray();
        CryptRSA rsa = new CryptRSA();
        byte[] expResult = plainInput;
        byte[] result = rsa.encrypt(plainInput, publicKey);
        result = rsa.decrypt(result, privateKey);
        assertArrayEquals(expResult, result);
    }
}
