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
package ch.zhaw.cryptoby.helper;

import ch.zhaw.cryptoby.ui.imp.console.CryptobyConsole;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Toby
 */
public class CryptobyHelper {

    private static final String EOB = "EndOfBlock";

    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);

        Formatter formatter = new Formatter(sb);
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
        return sb.toString();
    }

    public static byte[] hexStringToBytes(String hexString) {
        byte[] bytes = new byte[hexString.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer
                    .parseInt(hexString.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

    public static String bytesToHexStringUpper(byte[] bytes) {
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static double logBigInteger(BigInteger val) {
        double LOG2 = Math.log(2.0);
        int blex = val.bitLength() - 1022; // any value in 60..1023 works
        if (blex > 0) {
            val = val.shiftRight(blex);
        }
        double res = Math.log(val.doubleValue());
        return blex > 0 ? res + blex * LOG2 : res;
    }

    public static void charToBlockString(char[] charTextHex) {
        int lenLine = 128;
        char[] temp = new char[lenLine];
        for (int i = 0; i < charTextHex.length; i = i + lenLine) {
            if ((charTextHex.length - i) < lenLine) {
                temp = new char[(charTextHex.length - i)];
                System.arraycopy(charTextHex, i, temp, 0, (charTextHex.length - i));
                System.out.println(new String(temp));
            } else {
                System.arraycopy(charTextHex, i, temp, 0, lenLine);
                System.out.println(new String(temp));
            }
        }
    }

    public static void pressEnter() {
        try {
            System.in.read();
        } catch (IOException ex) {
            Logger.getLogger(CryptobyConsole.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void printHexBlock(String cryptType, int inputKeySize, char[] inputCharTextHex) {
        System.out.println("\n"+cryptType +"-" + inputKeySize + " encrypted Text in Hex form (Copy with '" + EOB + "'):");
        CryptobyHelper.charToBlockString(inputCharTextHex);
        System.out.println(EOB);
    }

    public static void printPrivateKeyBlock(String privateKey) {
        System.out.println("\nPrivate Key:");
        CryptobyHelper.charToBlockString(privateKey.toCharArray());
        System.out.println(EOB);
    }

    public static void printPublicKeyBlock(String publicKey) {
        System.out.println("\nPublic Key:");
        CryptobyHelper.charToBlockString(publicKey.toCharArray());
        System.out.println(EOB);
    }

    public static String getEOBString() {
        return EOB;
    }

}
