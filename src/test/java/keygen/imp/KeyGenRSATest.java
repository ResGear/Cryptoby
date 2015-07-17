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
package keygen.imp;

import client.CryptobyClient;
import core.CryptobyCore;
import helper.CryptobyHelper;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Tobias Rees
 */
public class KeyGenRSATest {

    int rounds;

    /**
     *
     */
    public KeyGenRSATest() {
        rounds = 1;
    }

    /**
     * Test of genPrivateKey method, of class KeyGenRSA.
     */
    @Test
    public void testGenPrivatePublicKey1024() {
        for (int i = 0; i < rounds; i++) {
            System.out.println("genKey1024bit");
            int keySize = 1024;
            CryptobyClient client = new CryptobyClient();
            CryptobyCore core = new CryptobyCore(client);
            KeyGenRSA instance = new KeyGenRSA(core);
            instance.initGenerator(keySize);
            String resultPriv = instance.getPrivateKey();
            String resultPub = instance.getPublicKey();
            byte[] publicKey = CryptobyHelper.hexStringToBytes(resultPub);
            byte[] privateKey = CryptobyHelper.hexStringToBytes(resultPriv);
            assertTrue(publicKey.length == 128);
            assertTrue(privateKey.length == 256);
        }
    }

    /**
     * Test of genPrivateKey method, of class KeyGenRSA.
     */
    @Test
    public void testGenPrivatePublicKey2048() {
        for (int i = 0; i < rounds; i++) {
            System.out.println("genKey2048bit");
            int keySize = 2048;
            CryptobyClient client = new CryptobyClient();
            CryptobyCore core = new CryptobyCore(client);
            KeyGenRSA instance = new KeyGenRSA(core);
            instance.initGenerator(keySize);
            String resultPriv = instance.getPrivateKey();
            String resultPub = instance.getPublicKey();
            byte[] publicKey = CryptobyHelper.hexStringToBytes(resultPub);
            byte[] privateKey = CryptobyHelper.hexStringToBytes(resultPriv);
            assertTrue(publicKey.length == 256);  
            assertTrue(privateKey.length == 512);
        }
    }
        
    /**
     * Test of genPrivateKey method, of class KeyGenRSA.
     */
    @Test
    public void testGenPrivatePublicKey4096() {
        for (int i = 0; i < rounds; i++) {
            System.out.println("genKey4096bit");
            int keySize = 4096;
            CryptobyClient client = new CryptobyClient();
            CryptobyCore core = new CryptobyCore(client);
            KeyGenRSA instance = new KeyGenRSA(core);
            instance.initGenerator(keySize);
            String resultPriv = instance.getPrivateKey();
            String resultPub = instance.getPublicKey();
            byte[] publicKey = CryptobyHelper.hexStringToBytes(resultPub);
            byte[] privateKey = CryptobyHelper.hexStringToBytes(resultPriv);
            assertTrue(publicKey.length == 512);  
            assertTrue(privateKey.length == 1024);
        }
    }

}
