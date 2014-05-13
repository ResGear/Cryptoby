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
 *//*
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
import java.util.Random;
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
        for(int i = 1;i < 11;i++){
        String filePathPlain = "target/test.txt";
        String filePathEnc = "target/test.cty";
        String filePathDec = "target/test2.txt";
        int keySize = 1024;
        byte[] testString = new byte[i*1000];
        new Random().nextBytes(testString);

        CryptobyClient client = new CryptobyClient();
        CryptobyCore core = new CryptobyCore(client);
        CryptRSA rsa = new CryptRSA();
        KeyGenRSA generator = new KeyGenRSA(core);

        CryptobyFileManager.putBytesToFile(filePathPlain, testString);

        generator.initGenerator(keySize);
        byte[] publicKey = generator.getPublicKeyByte();
        byte[] privateKey = generator.getPrivateKeyByte();
        byte[] plainInput = CryptobyFileManager.getBytesFromFile(filePathPlain);

        System.out.println("Plaininput: " + plainInput.length);
        System.out.println("publickey: " + publicKey.length);
        byte[] encrypt = rsa.encrypt(plainInput, publicKey);
        System.out.println("encrypt: " + encrypt.length);
        CryptobyFileManager.putBytesToFile(filePathEnc, encrypt);
        assertArrayEquals(encrypt, CryptobyFileManager.getBytesFromFile(filePathEnc));
        encrypt = CryptobyFileManager.getBytesFromFile(filePathEnc);
        

        byte[] decrypt = rsa.decrypt(encrypt, privateKey);
        CryptobyFileManager.putBytesToFile(filePathDec, decrypt);
        System.out.println("encrypt: " + encrypt.length);
        System.out.println("decrypt: " + decrypt.length);

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
        String publicKey = CryptobyHelper.charToBlockString(generator.getPublicKey().toCharArray());
        String privateKey = CryptobyHelper.charToBlockString(generator.getPrivateKey().toCharArray());

        CryptobyFileManager.putKeyToFile(publicKeyFilePath, publicKey);
        CryptobyFileManager.putKeyToFile(privateKeyFilePath, privateKey);

        byte[] resultPublic = CryptobyFileManager.getKeyFromFile(publicKeyFilePath);
        byte[] resultPrivate = CryptobyFileManager.getKeyFromFile(privateKeyFilePath);

        assertEquals(publicKey, new String(resultPublic));
        assertEquals(privateKey, new String(resultPrivate));

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
