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
package helper;


import helper.CryptobyHelper;
import org.junit.Test;

/**
 *
 * @author Toby
 */
public class CryptobyHelperTest {

    /**
     * Test of charToBlockString method, of class CryptobyHelper.
     */
    @Test
    public void testCharToBlockString() {
        System.out.println("charToBlockString");
        byte[] cryptByte = "TestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTest".getBytes();
        char[] charTextHex = CryptobyHelper.bytesToHexStringUpper(cryptByte).toCharArray();
        String result = CryptobyHelper.charToBlockString(charTextHex);
        System.out.println(result);
    }

    /**
     * Test of printHexBlock method, of class CryptobyHelper.
     */
    @Test
    public void testPrintHexBlock() {
        System.out.println("printHexBlock");
        byte[] cryptByte = "TestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTest".getBytes();
        char[] inputCharTextHex = CryptobyHelper.bytesToHexStringUpper(cryptByte).toCharArray();
        String cryptType = "RSA";
        int inputKeySize = 1024;
        String result = CryptobyHelper.printHexBlock(cryptType, inputKeySize, inputCharTextHex);
        System.out.println(result);
    }

    /**
     * Test of printPrivateKeyBlock method, of class CryptobyHelper.
     */
    @Test
    public void testPrintPrivateKeyBlock() {
        System.out.println("printPrivateKeyBlock");
        String privateKey = "TestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTest";
        String result = CryptobyHelper.printPrivateKeyBlock(privateKey);
        System.out.println(result);
    }

    /**
     * Test of printPublicKeyBlock method, of class CryptobyHelper.
     */
    @Test
    public void testPrintPublicKeyBlock() {
        System.out.println("printPublicKeyBlock");
        String publicKey = "TestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTest";
        String result = CryptobyHelper.printPublicKeyBlock(publicKey);
        System.out.println(result);
    }

    /**
     * Test of getEOBString method, of class CryptobyHelper.
     */
    @Test
    public void testGetEOBString() {
        System.out.println("getEOBString");
        String expResult = "";
        String result = CryptobyHelper.getEOBString();
    }

}
