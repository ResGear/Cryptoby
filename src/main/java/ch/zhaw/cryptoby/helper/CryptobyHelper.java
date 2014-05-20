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
 * This class provides different static helper methods which will be used in the
 * other classes of the application
 *
 * @author Tobias Rees
 */
public class CryptobyHelper {

    private static final String EOB = "EndOfBlock";

    /**
     * Convert byte array to Hex String.
     *
     * @param bytes Byte array to convert in Hex String
     * @return Hex String of bytes
     */
    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);

        Formatter formatter = new Formatter(sb);
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
        return sb.toString();
    }

    /**
     * Convert Hex String to byte array.
     *
     * @param hexString Hex String to convert in byte array
     * @return Byte array of hexString
     */
    public static byte[] hexStringToBytes(String hexString) {
        byte[] bytes = new byte[hexString.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer
                    .parseInt(hexString.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

    /**
     * Convert byte array to Upper Hex String.
     *
     * @param bytes Byte array to convert in Upper Hex String
     * @return Upper Hex String of bytes
     */
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

    /**
     * Calculate Log2 of a BigInteger object value.
     *
     * @param val BigInteger object value which will calculate with log2
     * @return Result of calculating of val as double value
     */
    public static double logBigInteger(BigInteger val) {
        double LOG2 = Math.log(2.0);
        int blex = val.bitLength() - 1022;
        if (blex > 0) {
            val = val.shiftRight(blex);
        }
        double res = Math.log(val.doubleValue());
        return blex > 0 ? res + blex * LOG2 : res;
    }

    /**
     * Convert a char array to block format with defined line length.
     *
     * @param charTextHex Char array from a String to convert
     * @return Return converted block String
     */
    public static String charToBlockString(char[] charTextHex) {
        int lenLine = 64;
        StringBuilder sb = new StringBuilder();
        char[] temp = new char[lenLine];
        for (int i = 0; i < charTextHex.length; i = i + lenLine) {
            if ((charTextHex.length - i) < lenLine) {
                temp = new char[(charTextHex.length - i)];
                System.arraycopy(charTextHex, i, temp, 0, (charTextHex.length - i));
                sb.append(new String(temp));
                sb.append("\n");
            } else {
                System.arraycopy(charTextHex, i, temp, 0, lenLine);
                sb.append(new String(temp));
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * Need Enter/Return key from keyboard input to continues. Used for Console
     * User Interfaces.
     */
    public static void pressEnter() {
        try {
            System.in.read();
        } catch (IOException ex) {
            Logger.getLogger(CryptobyConsole.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Method merge inputs and add a title to return String which can print
     * encrypted text and title in User Interfaces.
     *
     * @param cryptType Type of cryptology implementation as String
     * @param inputKeySize Size of used key as integer
     * @param inputCharTextHex Char array of String which will be converted to
     * block
     * @return Return String of merged input parameter and text
     */
    public static String printHexBlock(String cryptType, int inputKeySize, char[] inputCharTextHex) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append(cryptType).append("-").append(inputKeySize).append(" encrypted Text in Hex form (Copy with '" + EOB + "'):\n");
        sb.append(CryptobyHelper.charToBlockString(inputCharTextHex));
        sb.append(EOB);
        return sb.toString();
    }

    /**
     * Method convert private key String to block format and add a title.
     *
     * @param privateKey String converted to char array and than to block String
     * @return Return block String of privateKey
     */
    public static String printPrivateKeyBlock(String privateKey) {
        StringBuilder sb = new StringBuilder();
        sb.append("\nPrivate Key:\n");
        sb.append(CryptobyHelper.charToBlockString(privateKey.toCharArray()));
        sb.append(EOB);
        return sb.toString();
    }

    /**
     * Method convert public key String to block format and add a title.
     *
     * @param publicKey String converted to char array and than to block String
     * @return Return block String of publicKey
     */
    public static String printPublicKeyBlock(String publicKey) {
        StringBuilder sb = new StringBuilder();
        sb.append("\nPublic Key:\n");
        sb.append(CryptobyHelper.charToBlockString(publicKey.toCharArray()));
        sb.append(EOB);
        return sb.toString();
    }

    /**
     * EndOfBlock String marks the end of a block for Scanner objects
     *
     * @return Get EndOfBlock String
     */
    public static String getEOBString() {
        return EOB;
    }

    /**
     * XOR two byte arrays.
     *
     * @param firstArray First byte array to XOR with secArray
     * @param secArray Second byte array to XOR with firstArray
     * @return Return result of XOR as byte array
     */
    public static byte[] xorByteArrays(byte[] firstArray, byte[] secArray) {
        byte[] xorArray = new byte[firstArray.length];
        int i = 0;
        for (byte b : secArray) {
            xorArray[i] = (byte) (b ^ firstArray[i++]);
        }
        return xorArray;
    }

    /**
     * Print a progress bar and percent of progress to console. In this format:
     * [====================================================================================================]
     * 100%
     *
     * @param percentProgress Percent of progress. Integer value has to be
     * between 0 and 100.
     */
    public static void printProgressBar(int percentProgress) {
        if (percentProgress >= 0 || percentProgress <= 100) {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            for (int i = 0; i < percentProgress; i++) {
                sb.append("=");
            }
            for (int i = 100; i > percentProgress; i--) {
                sb.append(".");
            }
            sb.append("] ");
            sb.append(percentProgress);
            sb.append("%");
            sb.append("\r");
            System.out.print(sb.toString());
        } else {
            System.out.print("Input has to between 0 and 100.");
        }

    }

    /**
     * Print IO Exception Error.
     *
     */
    public static void printIOExp() {
        System.out.println("File not found or other IO Error! Go back to Menu.");
    }

}
