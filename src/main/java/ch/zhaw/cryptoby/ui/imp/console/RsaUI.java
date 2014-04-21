/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.zhaw.cryptoby.ui.imp.console;

import ch.zhaw.cryptoby.core.CryptobyHelper;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Toby
 */
public class RsaUI {

    public static void rsaCrypter(CryptobyConsole console) {
        final Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("What do want? Encryption or Decryption?");
            System.out.println("-------------------------\n");
            System.out.println("1 - Encryption and generate Keys");
            System.out.println("2 - Encryption with own Key");
            System.out.println("3 - Decryption");
            System.out.println("4 - Back");
            while (!scanner.hasNextInt()) {
                System.out.println("That's not a number! Enter 1,2,3 or 4:");
                scanner.next();
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > 4);

        switch (choice) {
            case 1:
                rsaEncrypterGenKeys(console);
                break;
            case 2:
                rsaEncrypter(console);
                break;
            case 3:
                rsaDecrypter(console);
                break;
            case 4:
                console.menuStringAsym();
                break;
            default:
                rsaCrypter(console);
        }
    }

    private static void rsaEncrypter(CryptobyConsole console) {
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
            // Input Key for decryption
            System.out.println("Enter the public Key:");
            key = new BigInteger(scanner.next(), Character.MAX_RADIX).toByteArray();
            keySize = key.length;
        } while (keySize != 128 && keySize != 256 && keySize != 512);

        // Initial RSA Crypt Object
        console.getCore().getClient().setCryptAsymArt("RSA");
        console.getCore().initCryptAsym();

        // Encrypt the String Text with given Key
        cryptText = console.getCore().getCryptAsym().encrypt(plainText, key);

        // Convert byte Array into a Hexcode String
        cryptTextHex = CryptobyHelper.bytesToHexStringUpper(cryptText);

        // Print encrypted Text in Hex form
        System.out.println("RSA-" + keySize + " encrypted Text in Hex form:");
        System.out.println(cryptTextHex);

        // Enter for Continues
        try {
            System.in.read();
        } catch (IOException ex) {
            Logger.getLogger(CryptobyConsole.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Back to Menu rsaCrypter
        RsaUI.rsaCrypter(console);
    }

    private static void rsaEncrypterGenKeys(CryptobyConsole console) {
        final Scanner scanner = new Scanner(System.in);
        // Initial Variables
        byte[] plainText;
        byte[] cryptText;
        String cryptTextHex;
        String privateKey;
        String publicKey;
        byte[] publicKeyByte;
        int keySize;

        // Input your String Text to encrypt
        System.out.println("Your Text to encrypt:");
        scanner.useDelimiter("\\n");
        plainText = scanner.next().getBytes();
        do {
            // Input your Key for encryption
            System.out.println("Enter the Size of Key. Allowed are 1024, 2048 and 4096:");
            keySize = scanner.nextInt();
        } while (keySize != 1024 && keySize != 2048 && keySize != 4096);

        // Initial RSA Crypt Object
        console.getCore().getClient().setCryptAsymArt("RSA");
        console.getCore().initCryptAsym();

        // Get Public Key in Bytecode
        console.getCore().getKeyGenAsym().initGenerator(keySize);
        publicKeyByte = console.getCore().getKeyGenAsym().getPublicKeyByte();
        // Get Public Key as String
        publicKey = console.getCore().getKeyGenAsym().getPublicKey();
        privateKey = console.getCore().getKeyGenAsym().getPrivateKey();

        // Encrypt the String Text with given Key
        cryptText = console.getCore().getCryptAsym().encrypt(plainText, publicKeyByte);

        // Convert byte Array into a Hexcode String
        cryptTextHex = CryptobyHelper.bytesToHexStringUpper(cryptText);

        // Print encrypted Text in Hex form
        System.out.println("RSA-" + keySize + " encrypted Text in Hex form:");
        System.out.println(cryptTextHex);
        System.out.println("\n\n");
        // Print Private and Public Keys
        System.out.println("Private Key: " + privateKey + "\n");
        System.out.println("Public Key: " + publicKey + "\n");

        // Enter for Continues
        try {
            System.in.read();
        } catch (IOException ex) {
            Logger.getLogger(CryptobyConsole.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Back to Menu rsaCrypter
        RsaUI.rsaCrypter(console);
    }

    private static void rsaDecrypter(CryptobyConsole console) {
        Scanner scanner = new Scanner(new BufferedInputStream(System.in, 20*1024*1024), "utf-8");
        // Initial Variables
        byte[] plainText = null;
        byte[] cryptText;
        String cryptTextHex;
        byte[] key;
        int keySize;

        // Input String Text to decrypt
        System.out.println("Your Text to decrypt:");
        
        cryptTextHex = scanner.nextLine();

        do {
            // Input Key for decryption
            System.out.println("Enter the private Key:");
            key = new BigInteger(scanner.next(), Character.MAX_RADIX).toByteArray();
            keySize = key.length;
        } while (keySize != 512 && keySize != 1024 && keySize != 2048);

        // Initial RSA Crypt Object
        console.getCore().getClient().setCryptAsymArt("RSA");
        console.getCore().initCryptAsym();

        // Convert Hexcode String to Byte Array
        cryptText = CryptobyHelper.hexStringToBytes(cryptTextHex);

        // Decrypt the String Text with given Key
        try {
            plainText = console.getCore().getCryptAsym().decrypt(cryptText, key);
        } catch (Exception e) {
            System.out.println("Unable to decrypt this String!!");
            // Enter for Continues
            try {
                System.in.read();
            } catch (IOException ex) {
                Logger.getLogger(CryptobyConsole.class.getName()).log(Level.SEVERE, null, ex);
            }
            rsaCrypter(console);
        }

        // Print decrypted Text
        System.out.println("RSA-" + keySize * 8 + " decrypted Text:");
        System.out.println(new String(plainText));

        // Enter for Continues
        try {
            System.in.read();
        } catch (IOException ex) {
            Logger.getLogger(CryptobyConsole.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Back to Menu rsaCrypter
        RsaUI.rsaCrypter(console);
    }

}
