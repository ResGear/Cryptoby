/*
 * Copyright (C) 2014 Toby
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
        String reString = new String(result);
        System.out.println(reString);
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
