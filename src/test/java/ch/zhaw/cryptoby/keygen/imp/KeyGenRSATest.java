/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.zhaw.cryptoby.keygen.imp;

import ch.zhaw.cryptoby.client.CryptobyClient;
import ch.zhaw.cryptoby.core.CryptobyCore;
import java.math.BigInteger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Toby
 */
public class KeyGenRSATest {

    public KeyGenRSATest() {
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
     * Test of genPrivateKey method, of class KeyGenRSA.
     */
    @Test
    public void testGenPrivatePublicKey1024() {
        System.out.println("genPrivateKey1024bit");
        int keySize = 1024;
        CryptobyClient client = new CryptobyClient();
        CryptobyCore core = new CryptobyCore(client);
        KeyGenRSA instance = new KeyGenRSA(core);
        instance.initGenerator(keySize);
        String resultPriv = instance.getPrivateKey();
        String resultPub = instance.getPublicKey();
        byte[] publicKey = new BigInteger(resultPub, Character.MAX_RADIX).toByteArray();
        byte[] privateKey = new BigInteger(resultPriv, Character.MAX_RADIX).toByteArray();
        assertTrue(publicKey.length==256);
        assertTrue(privateKey.length==512);
    }

    /**
     * Test of genPrivateKey method, of class KeyGenRSA.
     */
    @Test
    public void testGenPrivatePublicKey2048() {
        System.out.println("genPrivateKey2048bit");
        int keySize = 2048;
        CryptobyClient client = new CryptobyClient();
        CryptobyCore core = new CryptobyCore(client);
        KeyGenRSA instance = new KeyGenRSA(core);
        instance.initGenerator(keySize);
        String resultPriv = instance.getPrivateKey();
        String resultPub = instance.getPublicKey();
        byte[] publicKey = new BigInteger(resultPub, Character.MAX_RADIX).toByteArray();
        byte[] privateKey = new BigInteger(resultPriv, Character.MAX_RADIX).toByteArray();
        System.out.println(resultPriv);
        System.out.println(resultPub);
        assertTrue(publicKey.length==512);
        assertTrue(privateKey.length==1024);
    }
}
