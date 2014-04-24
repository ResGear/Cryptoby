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

import ch.zhaw.cryptoby.helper.CryptobyHelper;
import java.util.Scanner;

/**
 *
 * @author Toby
 */
public class AesUI {

    private static byte[] plainText = null;
    private static byte[] cryptText;
    private static byte[] key;
    private static int keySize;
    private static int choice;
    private static String cryptTextHex;
    private static final String quit = "QuitCrypt";
    private static Scanner scanner = new Scanner(System.in);

    public static void aesCrypter(CryptobyConsole console) {
        scanner = new Scanner(System.in);

        do {
            System.out.println("What do want? Encryption or Decryption?");
            System.out.println("-------------------------\n");
            System.out.println("1 - Encryption");
            System.out.println("2 - Decryption");
            System.out.println("3 - Back");
            while (!scanner.hasNextInt()) {
                System.out.println("That's not a number! Enter 1,2 or 3:");
                scanner.next();
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > 3);

        switch (choice) {
            case 1:
                aesEncrypter(console);
                break;
            case 2:
                aesDecrypter(console);
                break;
            case 3:
                console.menuStringSym();
                break;
            default:
                aesCrypter(console);
        }
    }

    private static void aesEncrypter(CryptobyConsole console) {
        scanner = new Scanner(System.in);

        // Input your String Text to encrypt
        plainText = AesUI.scanPlainText(console);

        // Input your Key for encryption
        key = AesUI.scanKey(console);

        // Initial AES Crypt Object
        AesUI.initAESKeyGen(console);

        // Encrypt the String Text with given Key
        cryptText = console.getCore().getCryptSym().encrypt(plainText, key);

        // Convert byte Array into a Hexcode String
        cryptTextHex = CryptobyHelper.bytesToHexStringUpper(cryptText);

        // Print encrypted Text in Hex form
        System.out.println("AES-" + keySize + " encrypted Text in Hex form:");
        System.out.println(cryptTextHex);

        // Enter for Continues
        CryptobyHelper.pressEnter();

        // Back to Menu Choose PrimeTest
        console.menuStringSym();
    }

    private static void aesDecrypter(CryptobyConsole console) {
        scanner = new Scanner(System.in);

        // Input String Text to decrypt
        System.out.println("Your Text to decrypt:");
        if (scanner.hasNext(quit)) {
            AesUI.aesCrypter(console);
        }
        cryptTextHex = scanner.next();

        // Input your Key for encryption
        key = AesUI.scanKey(console);

        // Initial AES Crypt Object
        AesUI.initAESKeyGen(console);

        // Convert Hexcode String to Byte Array                
        cryptText = CryptobyHelper.hexStringToBytes(cryptTextHex);

        // Decrypt the String Text with given Key
        try {
            plainText = console.getCore().getCryptSym().decrypt(cryptText, key);
        } catch (Exception e) {
            System.out.println("Unable to decrypt this String!!");
            // Enter for Continues
            CryptobyHelper.pressEnter();
            aesCrypter(console);
        }

        // Print decrypted Text
        System.out.println("AES-" + keySize + " decrypted Text:");
        System.out.println(new String(plainText));

        // Enter for Continues
        CryptobyHelper.pressEnter();

        // Back to Menu Choose PrimeTest
        console.menuStringSym();
    }

    private static byte[] scanPlainText(CryptobyConsole console) {
        scanner = new Scanner(System.in);
        System.out.println("Your Text to encrypt (Type '" + quit + "' to Escape):");
        scanner.useDelimiter("\n");
        if (scanner.hasNext(quit)) {
            AesUI.aesCrypter(console);
        }
        return scanner.next().getBytes();
    }

    private static byte[] scanKey(CryptobyConsole console) {
        scanner = new Scanner(System.in);
        byte[] tempKey;
        do {
            System.out.println("Enter the Key. The Key Size has to be 128,192 or 256bit in Hex Code");
            if (scanner.hasNext(quit)) {
                AesUI.aesCrypter(console);
            }
            tempKey = scanner.next().getBytes();
            keySize = tempKey.length * 4;
        } while (keySize != 128 && keySize != 192 && keySize != 256);
        return tempKey;
    }

    private static void initAESKeyGen(CryptobyConsole console) {
        console.getCore().getClient().setCryptSymArt("AES");
        console.getCore().initCryptSym();
    }

}
