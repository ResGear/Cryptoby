/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.zhaw.cryptoby.ui.imp;

import ch.zhaw.cryptoby.core.CryptobyHelper;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Toby
 */
public class AesUI {

    public static void aesCrypter(CryptobyConsole console) {
        final Scanner scanner = new Scanner(System.in);
        int choice;

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
                console.run();
                break;
            default:
                aesCrypter(console);
        }
    }

    private static void aesEncrypter(CryptobyConsole console) {
        final Scanner scanner = new Scanner(System.in);
        // Initial Variables
        byte[] plainText;
        byte[] cryptText;
        String cryptTextHex;
        byte[] key;
        int keySize;

        // Input your String Text to encrypt
        System.out.println("Your Text to encrypt:");
        scanner.useDelimiter("\\n");
        plainText = scanner.next().getBytes();
        do {
            // Input your Key for encryption
            System.out.println("Enter the Key. The Key Size has to be 128,192 or 256bit in Hex Code");
            key = scanner.next().getBytes();
            keySize = key.length * 4;
            System.out.println(keySize);
        } while (keySize != 128 && keySize != 192 && keySize != 256);

        // Initial AES Crypt Object
        console.getCore().getClient().setCryptSymArt("AES");
        console.getCore().initCryptSym();

        // Encrypt the String Text with given Key
        cryptText = console.getCore().getCryptSym().encrypt(plainText, key);

        // Convert byte Array into a Hexcode String
        cryptTextHex = CryptobyHelper.bytesToHexString(cryptText);

        // Print encrypted Text in Hex form
        System.out.println("AES-" + keySize + " encrypted Text in Hex form:");
        System.out.println(cryptTextHex);

        // Enter for Continues
        try {
            System.in.read();
        } catch (IOException ex) {
            Logger.getLogger(CryptobyConsole.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Back to Menu Choose PrimeTest
        console.chooseSymEnc();
    }

    private static void aesDecrypter(CryptobyConsole console) {
        final Scanner scanner = new Scanner(System.in);
        // Initial Variables
        byte[] plainText = null;
        byte[] cryptText;
        String cryptTextHex;
        byte[] key;
        int keySize;

        // Input String Text to decrypt
        System.out.println("Your Text to decrypt:");
        cryptTextHex = scanner.next();
        do {
            // Input Key for decryption
            System.out.println("Enter the Key. The Key Size has to be 128,192 or 256bit in Hex Code");
            key = scanner.next().getBytes();
            keySize = key.length * 4;
        } while (keySize != 128 && keySize != 192 && keySize != 256);

        // Initial AES Crypt Object
        console.getCore().getClient().setCryptSymArt("AES");
        console.getCore().initCryptSym();

        // Convert Hexcode String to Byte Array
        cryptText = CryptobyHelper.hexStringToBytes(cryptTextHex);
        System.out.println(CryptobyHelper.bytesToHexString(cryptText));

        // Decrypt the String Text with given Key
        try {
            plainText = console.getCore().getCryptSym().decrypt(cryptText, key);
        } catch (Exception e) {
            System.out.println("Unable to decrypt this String!!");
            // Enter for Continues
            try {
                System.in.read();
            } catch (IOException ex) {
                Logger.getLogger(CryptobyConsole.class.getName()).log(Level.SEVERE, null, ex);
            }
            aesCrypter(console);
        }

        // Print decrypted Text
        System.out.println("AES-" + keySize + " decrypted Text:");
        System.out.println(new String(plainText));

        // Enter for Continues
        try {
            System.in.read();
        } catch (IOException ex) {
            Logger.getLogger(CryptobyConsole.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Back to Menu Choose PrimeTest
        console.chooseSymEnc();
    }

}
