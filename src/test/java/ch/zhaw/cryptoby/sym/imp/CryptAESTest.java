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
package ch.zhaw.cryptoby.sym.imp;

import ch.zhaw.cryptoby.helper.CryptobyHelper;
import ch.zhaw.cryptoby.keygen.imp.KeyGenSHA3;
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
        KeyGenSHA3 keyGen = new KeyGenSHA3();
        String hexKey = keyGen.generateKey(256, "password");
        byte[] bKey = CryptobyHelper.hexStringToBytes(hexKey);
        CryptAES instance = new CryptAES();
        byte[] expResult = plainInput;
        byte[] result = instance.encrypt(plainInput, bKey);
        String resString = CryptobyHelper.bytesToHexStringUpper(result);
        System.out.println(resString);
        result = instance.decrypt(result, bKey);
        System.out.println(new String(result));
        assertArrayEquals(expResult, result);
    }

    @Test
    public void testEncryptDecrypt256_falseKey() {
        System.out.println("crypt false key");
        byte[] plainInput = "Text to Test for Testing from Tester by Testcase".getBytes();
        KeyGenSHA3 keyGen = new KeyGenSHA3();
        String hexKey = keyGen.generateKey(256, "password");
        byte[] bKey = CryptobyHelper.hexStringToBytes(hexKey);
        CryptAES instance = new CryptAES();
        byte[] expResult = plainInput;
        byte[] result = instance.encrypt(plainInput, bKey);
        String resString = CryptobyHelper.bytesToHexStringUpper(result);
        System.out.println(resString);
        hexKey = keyGen.generateKey(256, "passwordFalse");
        bKey = CryptobyHelper.hexStringToBytes(hexKey);
        result = instance.decrypt(result, bKey);
        System.out.println(new String(result));
        assertFalse(new String(expResult).equals(new String(result)));
    }

    @Test
    public void testEncryptDecrypt256_false() {
        System.out.println("crypt almost false key");
        byte[] plainInput = "Text to Test for Testing from Tester by Testcase".getBytes();
        String hexKey = "13A9489AF957FF7B5E8E712737D0B4A0C92AE8EBAE9DD11E9C11B8CB79707017";
        byte[] bKey = CryptobyHelper.hexStringToBytes(hexKey);
        CryptAES instance = new CryptAES();
        byte[] expResult = plainInput;
        byte[] result = instance.encrypt(plainInput, bKey);
        String resString = CryptobyHelper.bytesToHexStringUpper(result);
        System.out.println(resString);
        hexKey = "13A9489AF957FF7B5E8E712737D0B4A0C92AE8EBAE9DD11E9C11B8CB79707011";
        bKey = CryptobyHelper.hexStringToBytes(hexKey);
        result = instance.decrypt(result, bKey);
        System.out.println(new String(result));
        assertFalse(new String(expResult).equals(new String(result)));
    }

    @Test
    public void testEncryptDecrypt256_CBC() {
        System.out.println("encrypt and decrypt recurring words");
        byte[] plainInput = "TestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTest".getBytes();
        KeyGenSHA3 keyGen = new KeyGenSHA3();
        String hexKey = keyGen.generateKey(256, "password");
        byte[] bKey = CryptobyHelper.hexStringToBytes(hexKey);
        CryptAES instance = new CryptAES();
        byte[] expResult = plainInput;
        byte[] result = instance.encrypt(plainInput, bKey);
        String resString = CryptobyHelper.bytesToHexStringUpper(result);
        for(int i = 0; i < resString.length() - 32 ;i += 32){
            assertFalse(resString.substring(i, i+32).equals(resString.substring(i+32, i+64)));
        }
        System.out.println(resString);
        result = instance.decrypt(result, bKey);
        System.out.println(new String(result));
        assertArrayEquals(expResult, result);
    }
}
