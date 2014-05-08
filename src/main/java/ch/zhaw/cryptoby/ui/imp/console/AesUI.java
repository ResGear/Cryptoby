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
package ch.zhaw.cryptoby.ui.imp.console;

import ch.zhaw.cryptoby.filemgr.FileManager;
import ch.zhaw.cryptoby.helper.CryptobyHelper;
import java.math.BigInteger;
import java.util.Scanner;

/**
 *
 * @author Toby
 */
public class AesUI {

    private static Scanner scanner = new Scanner(System.in);
    private static final String quit = "QuitCrypt";
    private static String pathToFile;
    private static byte[] plainByte;
    private static byte[] cryptByte;
    private static byte[] key;
    private static int keySize;
    private static char[] charTextHex;

    public static void aesCrypterFile(CryptobyConsole console) {
        switch (AesUI.choiceText()) {
            case 1:
                aesEncrypterFile(console);
                break;
            case 2:
                aesDecrypterFile(console);
                break;
            case 3:
                console.menuFileSym();
                break;
            default:
                aesCrypterFile(console);
        }
    }

    private static void aesEncrypterFile(CryptobyConsole console) {
        scanner = new Scanner(System.in);

        // Input File to encrypt
        System.out.println("\nEnter Path to File for Encryption (Type '" + quit + "' to Escape):");
        if (scanner.hasNext(quit)) {
            AesUI.aesCrypterText(console);
        }
        pathToFile = scanner.next();

        // Input Key File for encryption
        key = AesUI.scanKeyFile(console);

        // Initial AES Crypt Object
        AesUI.initAESKeyGen(console);

        // Encrypt the String Text with given Key
        cryptByte = console.getCore().getCryptSym().encrypt(plainByte, key);

        // Convert byte Array into a Hexcode String
        charTextHex = CryptobyHelper.bytesToHexStringUpper(cryptByte).toCharArray();

        // Print encrypted Text in Hex Block form
        System.out.print(CryptobyHelper.printHexBlock("AES", keySize, charTextHex));

        // Enter for Continues
        CryptobyHelper.pressEnter();

        // Back to Menu Choose PrimeTest
        console.menuTextSym();
    }

    private static void aesDecrypterFile(CryptobyConsole console) {

    }

    public static void aesCrypterText(CryptobyConsole console) {
        switch (AesUI.choiceText()) {
            case 1:
                aesEncrypterText(console);
                break;
            case 2:
                aesDecrypterText(console);
                break;
            case 3:
                console.menuTextSym();
                break;
            default:
                aesCrypterText(console);
        }
    }

    private static void aesEncrypterText(CryptobyConsole console) {
        scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");

        // Input your String Text to encrypt
        System.out.println("\nYour Text to encrypt (Type '" + quit + "' to Escape):");
        if (scanner.hasNext(quit)) {
            AesUI.aesCrypterText(console);
        }
        plainByte = scanner.next().getBytes();

        // Input your Key for encryption
        key = AesUI.scanKeyText(console);

        // Initial AES Crypt Object
        AesUI.initAESKeyGen(console);

        // Encrypt the String Text with given Key
        cryptByte = console.getCore().getCryptSym().encrypt(plainByte, key);

        // Convert byte Array into a Hexcode String
        charTextHex = CryptobyHelper.bytesToHexStringUpper(cryptByte).toCharArray();

        // Print encrypted Text in Hex Block form
        System.out.println(CryptobyHelper.printHexBlock("AES", keySize, charTextHex));

        // Enter for Continues
        CryptobyHelper.pressEnter();

        // Back to Menu Choose PrimeTest
        console.menuTextSym();
    }

    private static void aesDecrypterText(CryptobyConsole console) {
        scanner = new Scanner(System.in);

        // Input encrypted Hex String Text to decrypt
        System.out.println("\nYour Text to decrypt (Type '" + quit + "' to Escape):");

        // Convert crypted HexString Block to one String
        try {
            String cryptText = "";
            while (!scanner.hasNext(CryptobyHelper.getEOBString())) {
                if (scanner.hasNext(quit)) {
                    AesUI.aesCrypterText(console);
                }
                cryptText = cryptText + scanner.next();
            }
            cryptByte = CryptobyHelper.hexStringToBytes(cryptText);

        } // Catch false format of Input
        catch (NumberFormatException exp) {
            System.out.println("\nNot allowed Crypted Text! Must be a Upper Hex String!");
            cryptByte = BigInteger.ZERO.toByteArray();
        }

        // Input your Key for encryption
        key = AesUI.scanKeyText(console);

        // Initial AES Crypt Object
        AesUI.initAESKeyGen(console);

        // Decrypt the String Text with given Key
        try {
            plainByte = console.getCore().getCryptSym().decrypt(cryptByte, key);
        } catch (Exception e) {
            System.out.println("\nUnable to decrypt this String!!");
            // Enter for Continues
            CryptobyHelper.pressEnter();
            aesCrypterText(console);
        }

        // Print decrypted Text
        System.out.println("\nAES-" + keySize + " decrypted Text:");
        System.out.println(new String(plainByte));

        // Enter for Continues
        CryptobyHelper.pressEnter();

        // Back to Menu Choose PrimeTest
        console.menuTextSym();
    }

    // Helper Functions    
    private static byte[] scanKeyText(CryptobyConsole console) {
        scanner = new Scanner(System.in);
        byte[] tempKey;
        do {
            System.out.println("\nAllowed Key Sizes 128,192 and 256 Bit.");
            System.out.println("Enter the AES Key (Type '" + quit + "' to Escape):");
            if (scanner.hasNext(quit)) {
                AesUI.aesCrypterText(console);
            }
            tempKey = scanner.next().getBytes();
            keySize = tempKey.length * 4;
        } while (keySize != 128 && keySize != 192 && keySize != 256);
        return tempKey;
    }
    
        private static byte[] scanKeyFile(CryptobyConsole console) {
        scanner = new Scanner(System.in);
        byte[] tempKey;
        String pathToKey;
        do {
            System.out.println("\nAllowed Key Sizes 128,192 and 256 Bit.");
            System.out.println("Enter Path to Key File (Type '" + quit + "' to Escape):");
            if (scanner.hasNext(quit)) {
                AesUI.aesCrypterFile(console);
            }
            pathToKey = scanner.next();
            tempKey = FileManager.getBytesFromFile(pathToKey);
            keySize = tempKey.length * 4;
        } while (keySize != 128 && keySize != 192 && keySize != 256);
        return tempKey;
    }

    private static void initAESKeyGen(CryptobyConsole console) {
        console.getCore().getClient().setCryptSymArt("AES");
        console.getCore().initCryptSym();
    }

    private static int choiceText() {
        scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n");
            System.out.println("AES Encryption and Decryption");
            System.out.println("-------------------------\n");
            System.out.println("1 - Encryption");
            System.out.println("2 - Decryption");
            System.out.println("3 - Back");
            System.out.print("Enter Number: ");
            while (!scanner.hasNextInt()) {
                System.out.print("That's not a number! Enter 1,2 or 3: ");
                scanner.next();
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > 3);
        return 0;
    }
}
