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
import java.math.BigInteger;
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
public class FileManagerTest {

    public FileManagerTest() {
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

        String filePathPlain = "./test.txt";
        String filePathEnc = "./test.cty";
        String filenPathDec = "./test2.txt";
        int keySize = 1024;
        byte[] testString = "Text to Test for Testing from Tester by Testcase".getBytes();
        
        CryptobyClient client = new CryptobyClient();
        CryptobyCore core = new CryptobyCore(client);
        CryptRSA rsa = new CryptRSA();
        KeyGenRSA generator = new KeyGenRSA(core);

        FileManager.putBytesToFile(filePathPlain, testString);

        generator.initGenerator(keySize);
        byte[] publicKey = generator.getPublicKeyByte();
        byte[] privateKey = generator.getPrivateKeyByte();
        byte[] plainInput = FileManager.getBytesFromFile(filePathPlain);
        
        byte[] encrypt = rsa.encrypt(plainInput, publicKey);
        FileManager.putBytesToFile(filePathEnc, encrypt);
        encrypt = FileManager.getBytesFromFile(filePathEnc);

        byte[] decrypt = rsa.decrypt(encrypt, privateKey);
        FileManager.putBytesToFile(filenPathDec, decrypt);
        
        assertArrayEquals(decrypt,testString);
        
    }

    /**
     * Test of getKeyFromFile method, of class FileManager.
     */
    @Test
    public void testPutAndGetKey() {
        System.out.println("Put and Get Keys");
        String publicKeyFilePath = "./publicKey.pub";
        String privateKeyFilePath = "./privateKey.prv";
        int keySize = 1024;
        
        CryptobyClient client = new CryptobyClient();
        CryptobyCore core = new CryptobyCore(client);
        KeyGenRSA generator = new KeyGenRSA(core);
        
        generator.initGenerator(keySize);
        String publicKey = CryptobyHelper.charToBlockString(generator.getPublicKey().toCharArray());
        String privateKey = CryptobyHelper.charToBlockString(generator.getPrivateKey().toCharArray());
        
        FileManager.putKeyToFile(publicKeyFilePath, publicKey);
        FileManager.putKeyToFile(privateKeyFilePath, privateKey);
        
        byte[] resultPublic = FileManager.getKeyFromFile(publicKeyFilePath);
        byte[] resultPrivate = FileManager.getKeyFromFile(privateKeyFilePath);

        assertEquals(publicKey,new String(resultPublic));
        assertEquals(privateKey,new String(resultPrivate));
        
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
