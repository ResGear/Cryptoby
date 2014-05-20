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
package ch.zhaw.cryptoby.filemgr;

import ch.zhaw.cryptoby.asym.imp.CryptRSA;
import ch.zhaw.cryptoby.client.CryptobyClient;
import ch.zhaw.cryptoby.core.CryptobyCore;
import ch.zhaw.cryptoby.helper.CryptobyHelper;
import ch.zhaw.cryptoby.keygen.imp.KeyGenRSA;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class CryptobyFileManagerTest {

    public CryptobyFileManagerTest() {
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
     * Test of getBytesFromFile method, of class FileManager.
     */
    @Test
    public void testGetAndPutByteFiles() {
        System.out.println("Put Plaintext, get Plainfile, encrypt and decrypt Byte Files");
        for (int i = 1; i < 100; i += 3) {
            String filePathPlain = "target/test.txt";
            String filePathEnc = "target/test.cty";
            String filePathDec = "target/test2.txt";
            int keySize = 1024;
            byte[] testString = new byte[i*100+i];
            new Random().nextBytes(testString);

            CryptobyClient client = new CryptobyClient();
            CryptobyCore core = new CryptobyCore(client);
            CryptRSA rsa = new CryptRSA();
            KeyGenRSA generator = new KeyGenRSA(core);

            try {
                CryptobyFileManager.putBytesToFile(filePathPlain, testString);
            } catch (IOException ex) {
                Logger.getLogger(CryptobyFileManagerTest.class.getName()).log(Level.SEVERE, null, ex);
            }

            generator.initGenerator(keySize);
            byte[] publicKey = generator.getPublicKeyByte();
            byte[] privateKey = generator.getPrivateKeyByte();
            byte[] plainInput = null;
            try {
                plainInput = CryptobyFileManager.getBytesFromFile(filePathPlain);
            } catch (IOException ex) {
                Logger.getLogger(CryptobyFileManagerTest.class.getName()).log(Level.SEVERE, null, ex);
            }

            byte[] encrypt = rsa.encrypt(plainInput, publicKey);
            try {
                CryptobyFileManager.putBytesToFile(filePathEnc, encrypt);
            } catch (IOException ex) {
                Logger.getLogger(CryptobyFileManagerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                encrypt = CryptobyFileManager.getBytesFromFile(filePathEnc);
            } catch (IOException ex) {
                Logger.getLogger(CryptobyFileManagerTest.class.getName()).log(Level.SEVERE, null, ex);
            }

            byte[] decrypt = rsa.decrypt(encrypt, privateKey);
            try {
                CryptobyFileManager.putBytesToFile(filePathDec, decrypt);
            } catch (IOException ex) {
                Logger.getLogger(CryptobyFileManagerTest.class.getName()).log(Level.SEVERE, null, ex);
            }

            assertArrayEquals(testString, decrypt);
        }
    }

    /**
     * Test of getKeyFromFile method, of class FileManager.
     */
    @Test
    public void testPutAndGetKey() {
        System.out.println("Put and Get Keys");
        String publicKeyFilePath = "target/publicKey.pub";
        String privateKeyFilePath = "target/privateKey.prv";
        int keySize = 1024;

        CryptobyClient client = new CryptobyClient();
        CryptobyCore core = new CryptobyCore(client);
        KeyGenRSA generator = new KeyGenRSA(core);

        generator.initGenerator(keySize);
        byte[] publicKeyByte = generator.getPublicKeyByte();
        byte[] privateKeyByte = generator.getPrivateKeyByte();
        String publicKey = generator.getPublicKey();
        String privateKey = generator.getPrivateKey();

        try {
            CryptobyFileManager.putKeyToFile(publicKeyFilePath, publicKey);
        } catch (IOException ex) {
            Logger.getLogger(CryptobyFileManagerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            CryptobyFileManager.putKeyToFile(privateKeyFilePath, privateKey);
        } catch (IOException ex) {
            Logger.getLogger(CryptobyFileManagerTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        byte[] resultPublic = null;
        try {
            resultPublic = CryptobyFileManager.getKeyFromFile(publicKeyFilePath);
        } catch (IOException ex) {
            Logger.getLogger(CryptobyFileManagerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        byte[] resultPrivate = null;
        try {
            resultPrivate = CryptobyFileManager.getKeyFromFile(privateKeyFilePath);
        } catch (IOException ex) {
            Logger.getLogger(CryptobyFileManagerTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        assertArrayEquals(publicKeyByte, resultPublic);
        assertArrayEquals(privateKeyByte, resultPrivate);
        assertEquals(publicKey, CryptobyHelper.bytesToHexString(resultPublic));
        assertEquals(privateKey, CryptobyHelper.bytesToHexString(resultPrivate));

    }
//
//    /**
//     * Test of putKeyToFile method, of class FileManager.
//     */
//    @Test
//    public void testPutKeyToFile() {
//        System.out.println("putKeyToFile");
//        String filePath = "";
//        String key = "";
//        FileManager.putKeyToFile(filePath, key);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

}
