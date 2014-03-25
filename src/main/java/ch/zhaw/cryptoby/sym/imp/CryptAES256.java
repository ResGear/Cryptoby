/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.zhaw.cryptoby.sym.imp;

import java.math.BigInteger;


/**
 *
 * @author Toby
 */
public class CryptAES256 {
    
    // Precomputed Rijndael S-BOX
    private static final char sbox[] = { 0x63, 0x7c, 0x77, 0x7b, 0xf2, 0x6b, 0x6f, 0xc5, 0x30, 0x01, 0x67, 0x2b, 0xfe,
        0xd7, 0xab, 0x76, 0xca, 0x82, 0xc9, 0x7d, 0xfa, 0x59, 0x47, 0xf0, 0xad, 0xd4, 0xa2, 0xaf, 0x9c, 0xa4, 0x72,
        0xc0, 0xb7, 0xfd, 0x93, 0x26, 0x36, 0x3f, 0xf7, 0xcc, 0x34, 0xa5, 0xe5, 0xf1, 0x71, 0xd8, 0x31, 0x15, 0x04,
        0xc7, 0x23, 0xc3, 0x18, 0x96, 0x05, 0x9a, 0x07, 0x12, 0x80, 0xe2, 0xeb, 0x27, 0xb2, 0x75, 0x09, 0x83, 0x2c,
        0x1a, 0x1b, 0x6e, 0x5a, 0xa0, 0x52, 0x3b, 0xd6, 0xb3, 0x29, 0xe3, 0x2f, 0x84, 0x53, 0xd1, 0x00, 0xed, 0x20,
        0xfc, 0xb1, 0x5b, 0x6a, 0xcb, 0xbe, 0x39, 0x4a, 0x4c, 0x58, 0xcf, 0xd0, 0xef, 0xaa, 0xfb, 0x43, 0x4d, 0x33,
        0x85, 0x45, 0xf9, 0x02, 0x7f, 0x50, 0x3c, 0x9f, 0xa8, 0x51, 0xa3, 0x40, 0x8f, 0x92, 0x9d, 0x38, 0xf5, 0xbc,
        0xb6, 0xda, 0x21, 0x10, 0xff, 0xf3, 0xd2, 0xcd, 0x0c, 0x13, 0xec, 0x5f, 0x97, 0x44, 0x17, 0xc4, 0xa7, 0x7e,
        0x3d, 0x64, 0x5d, 0x19, 0x73, 0x60, 0x81, 0x4f, 0xdc, 0x22, 0x2a, 0x90, 0x88, 0x46, 0xee, 0xb8, 0x14, 0xde,
        0x5e, 0x0b, 0xdb, 0xe0, 0x32, 0x3a, 0x0a, 0x49, 0x06, 0x24, 0x5c, 0xc2, 0xd3, 0xac, 0x62, 0x91, 0x95, 0xe4,
        0x79, 0xe7, 0xc8, 0x37, 0x6d, 0x8d, 0xd5, 0x4e, 0xa9, 0x6c, 0x56, 0xf4, 0xea, 0x65, 0x7a, 0xae, 0x08, 0xba,
        0x78, 0x25, 0x2e, 0x1c, 0xa6, 0xb4, 0xc6, 0xe8, 0xdd, 0x74, 0x1f, 0x4b, 0xbd, 0x8b, 0x8a, 0x70, 0x3e, 0xb5,
        0x66, 0x48, 0x03, 0xf6, 0x0e, 0x61, 0x35, 0x57, 0xb9, 0x86, 0xc1, 0x1d, 0x9e, 0xe1, 0xf8, 0x98, 0x11, 0x69,
        0xd9, 0x8e, 0x94, 0x9b, 0x1e, 0x87, 0xe9, 0xce, 0x55, 0x28, 0xdf, 0x8c, 0xa1, 0x89, 0x0d, 0xbf, 0xe6, 0x42,
        0x68, 0x41, 0x99, 0x2d, 0x0f, 0xb0, 0x54, 0xbb, 0x16 };
    // Precomputed inverted Rijndael S-BOX
    private static final char rsbox[] = { 0x52, 0x09, 0x6a, 0xd5, 0x30, 0x36, 0xa5, 0x38, 0xbf, 0x40, 0xa3, 0x9e, 0x81,
        0xf3, 0xd7, 0xfb, 0x7c, 0xe3, 0x39, 0x82, 0x9b, 0x2f, 0xff, 0x87, 0x34, 0x8e, 0x43, 0x44, 0xc4, 0xde, 0xe9,
        0xcb, 0x54, 0x7b, 0x94, 0x32, 0xa6, 0xc2, 0x23, 0x3d, 0xee, 0x4c, 0x95, 0x0b, 0x42, 0xfa, 0xc3, 0x4e, 0x08,
        0x2e, 0xa1, 0x66, 0x28, 0xd9, 0x24, 0xb2, 0x76, 0x5b, 0xa2, 0x49, 0x6d, 0x8b, 0xd1, 0x25, 0x72, 0xf8, 0xf6,
        0x64, 0x86, 0x68, 0x98, 0x16, 0xd4, 0xa4, 0x5c, 0xcc, 0x5d, 0x65, 0xb6, 0x92, 0x6c, 0x70, 0x48, 0x50, 0xfd,
        0xed, 0xb9, 0xda, 0x5e, 0x15, 0x46, 0x57, 0xa7, 0x8d, 0x9d, 0x84, 0x90, 0xd8, 0xab, 0x00, 0x8c, 0xbc, 0xd3,
        0x0a, 0xf7, 0xe4, 0x58, 0x05, 0xb8, 0xb3, 0x45, 0x06, 0xd0, 0x2c, 0x1e, 0x8f, 0xca, 0x3f, 0x0f, 0x02, 0xc1,
        0xaf, 0xbd, 0x03, 0x01, 0x13, 0x8a, 0x6b, 0x3a, 0x91, 0x11, 0x41, 0x4f, 0x67, 0xdc, 0xea, 0x97, 0xf2, 0xcf,
        0xce, 0xf0, 0xb4, 0xe6, 0x73, 0x96, 0xac, 0x74, 0x22, 0xe7, 0xad, 0x35, 0x85, 0xe2, 0xf9, 0x37, 0xe8, 0x1c,
        0x75, 0xdf, 0x6e, 0x47, 0xf1, 0x1a, 0x71, 0x1d, 0x29, 0xc5, 0x89, 0x6f, 0xb7, 0x62, 0x0e, 0xaa, 0x18, 0xbe,
        0x1b, 0xfc, 0x56, 0x3e, 0x4b, 0xc6, 0xd2, 0x79, 0x20, 0x9a, 0xdb, 0xc0, 0xfe, 0x78, 0xcd, 0x5a, 0xf4, 0x1f,
        0xdd, 0xa8, 0x33, 0x88, 0x07, 0xc7, 0x31, 0xb1, 0x12, 0x10, 0x59, 0x27, 0x80, 0xec, 0x5f, 0x60, 0x51, 0x7f,
        0xa9, 0x19, 0xb5, 0x4a, 0x0d, 0x2d, 0xe5, 0x7a, 0x9f, 0x93, 0xc9, 0x9c, 0xef, 0xa0, 0xe0, 0x3b, 0x4d, 0xae,
        0x2a, 0xf5, 0xb0, 0xc8, 0xeb, 0xbb, 0x3c, 0x83, 0x53, 0x99, 0x61, 0x17, 0x2b, 0x04, 0x7e, 0xba, 0x77, 0xd6,
        0x26, 0xe1, 0x69, 0x14, 0x63, 0x55, 0x21, 0x0c, 0x7d };

    public CryptAES256() {        
        
    }
   // AES RoundKey pass
    private static byte[][] AddRoundKey(byte[][] state, byte[] key) {
            for (int i = 0; i < 4; i++)
                    for (int j = 0; j < 4; j++)
                            state[j][i] ^= key[i * 4 + j];
            return state;
    }

    // AES SubBytes and InvSubBytes passes
    private static byte[][] SubBytes(byte[][] state, boolean inverted) {
            // Select the correct s-box, either inverted or not.
            char[] _sbox = inverted ? rsbox : sbox;
            for (int i = 0; i < 4; i++)
                    for (int j = 0; j < 4; j++)
                            state[i][j] = (byte) _sbox[state[i][j] & 0xFF];
            // The mask is used to shift the byte value to the unsigned (positive)
            // one
            return state;
    }

    // AES ShiftRow and InvShiftRow passes
    private static byte[][] ShiftRows(byte[][] state, boolean inverted) {
            byte[] t = new byte[4];
            for (int i = 1; i < 4; i++) {
                    for (int j = 0; j < 4; j++)
                            t[inverted ? (j + i) % 4 : j] = state[i][!inverted ? (j + i) % 4 : j];
                    for (int j = 0; j < 4; j++)
                            state[i][j] = t[j];
            }
            return state;
    }

    // Multiplication function over GF(2^8) used in the MixColumns pass
    public static byte GFMult(byte a, byte b) {
            byte r = 0, t;
            while (a != 0) {
                    if ((a & 1) != 0)
                            r = (byte) (r ^ b);
                    t = (byte) (b & 0x80);
                    b = (byte) (b << 1);
                    if (t != 0)
                            b = (byte) (b ^ 0x1b);
                    a = (byte) ((a & 0xff) >> 1);
            }
            return r;
    }

    // AES MixColums and InvMixColumns passes
    private static byte[][] MixColumns(byte[][] state, boolean inverted) {
            int[] tmp = new int[4];
            // In this way I can use a single method to do both the inverted and the
            // straight version. I choose the correct first operand of the
            // multiplication by checking the boolean "inverted" flag.
            byte a = (byte) (inverted ? 0x0b : 0x03);
            byte b = (byte) (inverted ? 0x0d : 0x01);
            byte c = (byte) (inverted ? 0x09 : 0x01);
            byte d = (byte) (inverted ? 0x0e : 0x02);

            for (int i = 0; i < 4; i++) {
                    tmp[0] = GFMult(d, state[0][i]) ^ GFMult(a, state[1][i]) ^ GFMult(b, state[2][i]) ^ GFMult(c, state[3][i]);
                    tmp[1] = GFMult(c, state[0][i]) ^ GFMult(d, state[1][i]) ^ GFMult(a, state[2][i]) ^ GFMult(b, state[3][i]);
                    tmp[2] = GFMult(b, state[0][i]) ^ GFMult(c, state[1][i]) ^ GFMult(d, state[2][i]) ^ GFMult(a, state[3][i]);
                    tmp[3] = GFMult(a, state[0][i]) ^ GFMult(b, state[1][i]) ^ GFMult(c, state[2][i]) ^ GFMult(d, state[3][i]);
                    for (int j = 0; j < 4; j++)
                            state[j][i] = (byte) (tmp[j]);
            }

            return state;
    }

    // Method used to recover the key using the known plaintext attack. The
    // known plaintext must be at least 16 characters long.
    private static byte[] recoverKey(String cipher, String plain) {
            byte[][] _cipher = arrayToMatrix(hexStringToByteArray(cipher));
            byte[][] _plain = arrayToMatrix(hexStringToByteArray(stringToHexString(plain)));

            _plain = SubBytes(_plain, false);
            _plain = ShiftRows(_plain, false);
            _plain = MixColumns(_plain, false);

            _cipher = ShiftRows(_cipher, true);
            _cipher = SubBytes(_cipher, true);

            byte[] __plain = matrixToArray(_plain);
            byte[] __cipher = matrixToArray(_cipher);
            for (int i = 0; i < __plain.length; i++)
                    __plain[i] ^= __cipher[i];

            return __plain;
    }
    
    // Method used to decrypt ciphertext using the given key.
    public static String decrypt(String cipher, byte[] key) {
        String decryptedText = new String();
//        byte[][] state;
//        for (int i = 0; i < cipher.length(); i += 32) {
//                state = arrayToMatrix(hexStringToByteArray(cipher.substring(i, i + 32)));
//
//                state = ShiftRows(state, true);
//                state = SubBytes(state, true);
//                state = AddRoundKey(state, key);
//                state = MixColumns(state, true);
//                state = ShiftRows(state, true);
//                state = SubBytes(state, true);
//
//                decryptedText += new String(matrixToArray(state));
//        }

        return decryptedText;
    }
    
    public static String encrypt(String cipher, byte[] key) {
        String encryptedText = new String();

        return encryptedText;
    }

    // Help Functions

    // Converts the given byte array to a 4 by 4 matrix by column
    private static byte[][] arrayToMatrix(byte[] array) {
            byte[][] matrix = new byte[4][4];
            for (int i = 0; i < 4; i++)
                    for (int j = 0; j < 4; j++)
                            matrix[j][i] = array[i * 4 + j];
            return matrix;
    }

    // Converts the given matrix to the corresponding array (by columns)
    private static byte[] matrixToArray(byte[][] matrix) {
            byte[] array = new byte[16];
            for (int i = 0; i < 4; i++)
                    for (int j = 0; j < 4; j++)
                            array[i * 4 + j] = matrix[j][i];
            return array;
    }

    // Converts a string to the equivalent hex representation
    public static String stringToHexString(String string) {
            return String.format("%x", new BigInteger(1, string.getBytes()));
    }

    // Converts the given string containing an hex representation to the
    // corresponding byte array
    public static byte[] hexStringToByteArray(String hexString) {
            int len = hexString.length();
            byte[] data = new byte[len / 2];
            for (int i = 0; i < len; i += 2) {
                    data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character.digit(
                                    hexString.charAt(i + 1), 16));
            }
            return data;
    }

    // Converts the given byte array to the corresponding textual form
    public static String byteArrayToHexString(byte[] hexArray) {
            String hexString = new String();
            for (byte hex : hexArray)
                    hexString += Integer.toString(hex & 0xFF, 16);

            return hexString;
    }
}
