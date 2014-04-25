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
    
    int rounds;

    public KeyGenRSATest() {
        rounds = 1;
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
        for(int i = 0; i < rounds;i++){
        System.out.println("genKey1024bit");
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
    }

    /**
     * Test of genPrivateKey method, of class KeyGenRSA.
     */
    @Test
    public void testGenPrivatePublicKey2048() {
        for(int i = 0; i < rounds;i++){
        System.out.println("genKey2048bit");
        int keySize = 2048;
        CryptobyClient client = new CryptobyClient();
        CryptobyCore core = new CryptobyCore(client);
        KeyGenRSA instance = new KeyGenRSA(core);
        instance.initGenerator(keySize);
        String resultPriv = instance.getPrivateKey();
        String resultPub = instance.getPublicKey();
        byte[] publicKey = new BigInteger(resultPub, Character.MAX_RADIX).toByteArray();
        byte[] privateKey = new BigInteger(resultPriv, Character.MAX_RADIX).toByteArray();
        assertTrue(publicKey.length==512);
        assertTrue(privateKey.length==1024);
        }
    }
    
}
