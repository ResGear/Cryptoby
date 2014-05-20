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

import ch.zhaw.cryptoby.filemgr.CryptobyFileManager;
import ch.zhaw.cryptoby.helper.CryptobyHelper;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;

/**
 *
 * @author Toby
 */
public class RsaUI {

    private static Scanner scanner = new Scanner(System.in);
    private static final String quit = "QuitCrypt";
    private static byte[] plainByte;
    private static byte[] cryptByte;
    private static byte[] privateKeyByte;
    private static byte[] publicKeyByte;
    private static String plainFilePath;
    private static String cryptFilePath;
    private static String privateKeyPath;
    private static String publicKeyPath;
    private static int choice;
    private static int keySize;
    private static char[] charTextHex;

    // UI for File Cryption Menu
    public static void rsaCrypterFile(CryptobyConsole console) {
        scanner = new Scanner(System.in);

        do {
            System.out.println("\n");
            System.out.println("RSA File Cryption");
            System.out.println("-----------------\n");
            System.out.println("1 - Encryption and generate Keys");
            System.out.println("2 - Encryption with own Key");
            System.out.println("3 - Decryption");
            System.out.println("4 - Back");
            System.out.print("Enter Number: ");
            while (!scanner.hasNextInt()) {
                System.out.print("That's not a number! Enter 1,2,3 or 4: ");
                scanner.next();
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > 4);

        switch (choice) {
            case 1:
                rsaEncGenKeysFile(console);
                break;
            case 2:
                rsaEncrypterFile(console);
                break;
            case 3:
                rsaDecrypterFile(console);
                break;
            case 4:
                console.menuFileAsym();
                break;
            default:
                rsaCrypterFile(console);
        }
    }

    private static void rsaEncrypterFile(CryptobyConsole console) {

        // Input Path to File for encryption
        scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");
        System.out.println("Enter Path to File for Encryption (Type '" + quit + "' to Escape):");
        if (scanner.hasNext(quit)) {
            rsaCrypterFile(console);
        }
        plainFilePath = scanner.next();

        // Get Bytes from PlainFile
        try {
            plainByte = CryptobyFileManager.getBytesFromFile(plainFilePath);
        } catch (IOException ex) {
            CryptobyHelper.printIOExp();
            rsaCrypterFile(console);
        }

        // Input Path to Public Key File for encryption
        scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");
        System.out.println("Enter Path to Public Key File (Type '" + quit + "' to Escape):");
        if (scanner.hasNext(quit)) {
            rsaCrypterFile(console);
        }
        publicKeyPath = scanner.next();

        // Get Bytes from Public Key File
        try {
            publicKeyByte = CryptobyFileManager.getKeyFromFile(publicKeyPath);
        } catch (IOException ex) {
            CryptobyHelper.printIOExp();
            rsaCrypterFile(console);
        }

        // Input Path to save encrypted File
        scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");
        System.out.println("Enter Path to save encrypted File (Type '" + quit + "' to Escape):");
        if (scanner.hasNext(quit)) {
            rsaCrypterFile(console);
        }
        cryptFilePath = scanner.next();

        // Initial RSA Crypt Object
        initRSAKeyGen(console);

        // Encrypt the File with given Public Key
        System.out.println("\nEncrypting in progress...");
        cryptByte = console.getCore().getCryptAsym().encrypt(plainByte, publicKeyByte);

        System.out.println("\nEncryption successfull. Saving File now...");

        // Put encrypted Bytes to File
        try {
            CryptobyFileManager.putBytesToFile(cryptFilePath, cryptByte);
        } catch (IOException ex) {
            CryptobyHelper.printIOExp();
            rsaCrypterFile(console);
        }

        System.out.println("\nEncrypted File saved to this Path:");
        System.out.println(cryptFilePath);

        // Reset Variables
        initRSAKeyGen(console);
        cryptByte = null;
        plainByte = null;
        publicKeyByte = null;

        // Back to Menu rsaCrypter with Enter (Return) Key
        System.out.println("\nGo back to RSA File Crypter Menu: Press Enter");
        CryptobyHelper.pressEnter();
        rsaCrypterFile(console);
    }

    private static void rsaEncGenKeysFile(CryptobyConsole console) {
        scanner = new Scanner(System.in);

        // Set Default Key Size
        keySize = 1024;

        do {
            System.out.println("\n");
            System.out.println("Choose Key Size in Bit");
            System.out.println("----------------------\n");
            System.out.println("1 - 1024");
            System.out.println("2 - 2048");
            System.out.println("3 - 4096");
            System.out.println("4 - Back");
            System.out.print("Enter Number: ");
            while (!scanner.hasNextInt()) {
                System.out.print("That's not a number! Enter 1,2,3 or 4: ");
                scanner.next();
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > 4);

        switch (choice) {
            case 1:
                keySize = 1024;
                break;
            case 2:
                keySize = 2048;
                break;
            case 3:
                keySize = 4096;
                break;
            case 4:
                rsaCrypterFile(console);
                break;
            default:
                rsaEncGenKeysFile(console);
        }

        // Input Path to File for encryption
        scanner = new Scanner(System.in);
        System.out.println("Enter Path to File for encryption (Type '" + quit + "' to Escape):");
        scanner.useDelimiter("\n");
        if (scanner.hasNext(quit)) {
            rsaCrypterFile(console);
        }
        plainFilePath = scanner.next();

        // Input Path to File for encryption
        scanner = new Scanner(System.in);
        System.out.println("Enter Path to saving encrypted File (Type '" + quit + "' to Escape):");
        scanner.useDelimiter("\n");
        if (scanner.hasNext(quit)) {
            rsaCrypterFile(console);
        }
        cryptFilePath = scanner.next();

        // Input Path for saving Private Key
        scanner = new Scanner(System.in);
        System.out.println("Enter Path to saving Private Key (Type '" + quit + "' to Escape):");
        scanner.useDelimiter("\n");
        if (scanner.hasNext(quit)) {
            rsaCrypterFile(console);
        }
        privateKeyPath = scanner.next();

        // Input Path for saving Public Key
        scanner = new Scanner(System.in);
        System.out.println("Enter Path to saving Public Key (Type '" + quit + "' to Escape):");
        scanner.useDelimiter("\n");

        publicKeyPath = scanner.next();

        // Get Bytes from PlainFile
        try {
            plainByte = CryptobyFileManager.getBytesFromFile(plainFilePath);
        } catch (IOException ex) {
            CryptobyHelper.printIOExp();
            rsaCrypterFile(console);
        }

        // Initial RSA Crypt Object
        initRSAKeyGen(console);

        // Get Public Key in Bytecode
        console.getCore().getKeyGenAsym().initGenerator(keySize);
        publicKeyByte = console.getCore().getKeyGenAsym().getPublicKeyByte();

        // Get Public and Private Key as String
        String publicKey = console.getCore().getKeyGenAsym().getPublicKey();
        String privateKey = console.getCore().getKeyGenAsym().getPrivateKey();

        // Encrypt the File with given Key
        System.out.println("\nEncrypting in progress...");
        cryptByte = console.getCore().getCryptAsym().encrypt(plainByte, publicKeyByte);

        System.out.println("\nEncryption successfull. Saving File now...");

        //Put encrypted Bytes to File
        try {
            CryptobyFileManager.putBytesToFile(cryptFilePath, cryptByte);
        } catch (IOException ex) {
            CryptobyHelper.printIOExp();
            rsaCrypterFile(console);
        }
        System.out.println("\nEncrypted File saved to this Path:");
        System.out.println(cryptFilePath);

        //Put private Key to File
        try {
            CryptobyFileManager.putKeyToFile(privateKeyPath, privateKey);
        } catch (IOException ex) {
            CryptobyHelper.printIOExp();
            rsaCrypterFile(console);
        }
        System.out.println("\nPrivate Key File saved to this Path:");
        System.out.println(privateKeyPath);

        //Put public Key to File
        try {
            CryptobyFileManager.putKeyToFile(publicKeyPath, publicKey);
        } catch (IOException ex) {
            CryptobyHelper.printIOExp();
            rsaCrypterFile(console);
        }
        System.out.println("\nPublic Key File saved to this Path:");
        System.out.println(publicKeyPath);

        // Reset Variables
        initRSAKeyGen(console);
        cryptByte = null;
        plainByte = null;
        publicKeyByte = null;

        // Back to Menu rsaCrypter with Enter (Return) Key
        System.out.println("\nGo back to RSA File Crypter Menu: Press Enter");
        CryptobyHelper.pressEnter();
        rsaCrypterFile(console);
    }

    private static void rsaDecrypterFile(CryptobyConsole console) {

        // Input Path to File for decryption
        scanner = new Scanner(System.in);
        System.out.println("Enter Path to File for Decryption (Type '" + quit + "' to Escape):");
        scanner.useDelimiter("\n");
        if (scanner.hasNext(quit)) {
            rsaCrypterFile(console);
        }
        cryptFilePath = scanner.next();

        // Get Bytes from PlainFile
        try {
            cryptByte = CryptobyFileManager.getBytesFromFile(cryptFilePath);
        } catch (IOException ex) {
            CryptobyHelper.printIOExp();
            rsaCrypterFile(console);
        }

        // Input Path to Key File for decryption
        scanner = new Scanner(System.in);
        System.out.println("Enter Path to Private Key File (Type '" + quit + "' to Escape):");
        scanner.useDelimiter("\n");
        if (scanner.hasNext(quit)) {
            rsaCrypterFile(console);
        }
        privateKeyPath = scanner.next();

        // Get Bytes from Private Key File
        try {
            privateKeyByte = CryptobyFileManager.getKeyFromFile(privateKeyPath);
        } catch (IOException ex) {
            CryptobyHelper.printIOExp();
            rsaCrypterFile(console);
        }

        // Input Path saving Path
        scanner = new Scanner(System.in);
        System.out.println("Enter saving Path for decrypted File (Type '" + quit + "' to Escape):");
        scanner.useDelimiter("\n");
        if (scanner.hasNext(quit)) {
            rsaCrypterFile(console);
        }
        plainFilePath = scanner.next();

        // Initial RSA Crypt Object
        initRSAKeyGen(console);

        // Encrypt the File with given Key
        System.out.println("\nDecrypting in progress...");
        try {
            plainByte = console.getCore().getCryptAsym().decrypt(cryptByte, privateKeyByte);
        } catch (Exception e) {
            System.out.println("\nUnable to decrypt this File!!");
            plainByte = null;
            // Press Return for Continues
            CryptobyHelper.pressEnter();
            rsaCrypterFile(console);
        }

        System.out.println("\nDecryption finished. Saving File now...");

        try {
            //Put encrypted Bytes to File
            CryptobyFileManager.putBytesToFile(plainFilePath, plainByte);
        } catch (IOException ex) {
            CryptobyHelper.printIOExp();
            rsaCrypterFile(console);
        }
        System.out.println("\nDecrypted File saved to this Path:");
        System.out.println(plainFilePath);

        // Reset Variables
        initRSAKeyGen(console);
        cryptByte = null;
        plainByte = null;
        publicKeyByte = null;

        // Back to Menu rsaCrypter with Enter (Return) Key
        System.out.println("\nGo back to RSA File Crypter Menu: Press Enter");
        CryptobyHelper.pressEnter();
        rsaCrypterFile(console);
    }

    // UIs for Text Cryption Menu
    public static void rsaCrypterText(CryptobyConsole console) {
        scanner = new Scanner(System.in);

        do {
            System.out.println("\n");
            System.out.println("RSA Text Cryption");
            System.out.println("-----------------\n");
            System.out.println("1 - Encryption and generate Keys");
            System.out.println("2 - Encryption with own Key");
            System.out.println("3 - Decryption");
            System.out.println("4 - Back");
            System.out.print("Enter Number: ");
            while (!scanner.hasNextInt()) {
                System.out.print("That's not a number! Enter 1,2,3 or 4: ");
                scanner.next();
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > 4);

        switch (choice) {
            case 1:
                rsaEncGenKeysText(console);
                break;
            case 2:
                rsaEncrypterText(console);
                break;
            case 3:
                rsaDecrypterText(console);
                break;
            case 4:
                console.menuTextAsym();
                break;
            default:
                rsaCrypterText(console);
        }
    }

    private static void rsaEncrypterText(CryptobyConsole console) {

        // Input your String Text to encrypt
        plainByte = scanPlainText(console);

        // Input the Public Key to encrypt
        publicKeyByte = scanPublicKey(console);

        // Initial RSA Crypt Object
        initRSAKeyGen(console);

        // Encrypt the String Text with given Key
        System.out.println("\nEncrypting in progress...");
        cryptByte = console.getCore().getCryptAsym().encrypt(plainByte, publicKeyByte);

        // Convert byte Array into a Hexcode String
        charTextHex = CryptobyHelper.bytesToHexStringUpper(cryptByte).toCharArray();

        // Print encrypted Text in Hex Block form
        System.out.println("\n" + CryptobyHelper.printHexBlock("RSA", keySize, charTextHex));

        // Back to Menu rsaCrypter with Enter (Return) Key
        System.out.println("\nGo back to RSA Text Crypter Menu: Press Enter");
        CryptobyHelper.pressEnter();
        rsaCrypterText(console);
    }

    private static void rsaEncGenKeysText(CryptobyConsole console) {
        scanner = new Scanner(System.in);
        String privateKey;
        String publicKey;

        // Set Default Key Size
        keySize = 1024;

        do {
            System.out.println("\n");
            System.out.println("Choose Key Size in Bit");
            System.out.println("----------------------\n");
            System.out.println("1 - 1024");
            System.out.println("2 - 2048");
            System.out.println("3 - 4096");
            System.out.println("4 - Back");
            System.out.print("Enter Number: ");
            while (!scanner.hasNextInt()) {
                System.out.print("That's not a number! Enter 1,2,3 or 4: ");
                scanner.next();
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > 4);

        switch (choice) {
            case 1:
                keySize = 1024;
                break;
            case 2:
                keySize = 2048;
                break;
            case 3:
                keySize = 4096;
                break;
            case 4:
                rsaCrypterText(console);
                break;
            default:
                rsaEncGenKeysText(console);
        }

        // Input your String Text to encrypt
        plainByte = scanPlainText(console);

        // Initial RSA Crypt Object
        initRSAKeyGen(console);

        // Get Public Key in Bytecode
        console.getCore().getKeyGenAsym().initGenerator(keySize);
        publicKeyByte = console.getCore().getKeyGenAsym().getPublicKeyByte();

        // Get Public and Private Key as String
        publicKey = console.getCore().getKeyGenAsym().getPublicKey();
        privateKey = console.getCore().getKeyGenAsym().getPrivateKey();

        // Encrypt the String Text with given Key
        System.out.println("\nEncrypting in progress...");
        cryptByte = console.getCore().getCryptAsym().encrypt(plainByte, publicKeyByte);

        // Convert crypted byte Array into a Hexcode String
        charTextHex = CryptobyHelper.bytesToHexStringUpper(cryptByte).toCharArray();

        // Print encrypted Text in Hex Block form
        System.out.println("\n" + CryptobyHelper.printHexBlock("RSA", keySize, charTextHex));

        // Print Private Keys
        System.out.println(CryptobyHelper.printPrivateKeyBlock(privateKey));
        // Print Public Keys
        System.out.println(CryptobyHelper.printPublicKeyBlock(publicKey));

        // Back to Menu rsaCrypter with Enter (Return) Key
        System.out.println("\nGo back to RSA Text Crypter Menu: Press Enter");
        CryptobyHelper.pressEnter();
        rsaCrypterText(console);
    }

    private static void rsaDecrypterText(CryptobyConsole console) {
        scanner = new Scanner(System.in);

        // Input encrypted Hex String Text to decrypt
        System.out.println("\nYour Text to decrypt (Type '" + quit + "' to Escape):");

        // Convert crypted HexString Block to one String
        try {
            String cryptText = "";
            while (!scanner.hasNext(CryptobyHelper.getEOBString())) {
                if (scanner.hasNext(quit)) {
                    rsaCrypterText(console);
                }
                cryptText = cryptText + scanner.next();
            }
            cryptByte = CryptobyHelper.hexStringToBytes(cryptText);

        } // Catch false format of Input
        catch (NumberFormatException exp) {
            System.out.println("\nNot allowed Crypted Text! Must be a Upper Hex String!");
            cryptByte = BigInteger.ZERO.toByteArray();
        }

        // Input the Private Key
        privateKeyByte = scanPrivateKey(console);

        // Initial RSA Crypt Object
        initRSAKeyGen(console);

        // Decrypt the String Text with given Key
        System.out.println("\nDecrypting in progress...");
        try {
            plainByte = console.getCore().getCryptAsym().decrypt(cryptByte, privateKeyByte);
        } catch (Exception e) {
            System.out.println("\nUnable to decrypt this String!!");
            plainByte = null;
            // Press Return for Continues
            CryptobyHelper.pressEnter();
            rsaCrypterText(console);
        }

        // Print decrypted Text
        System.out.println("\nRSA-" + keySize * 4 + " decrypted Text:");
        System.out.println(new String(plainByte));

        // Reset RSA Crypt Object to release Memory
        initRSAKeyGen(console);

        // Back to Menu rsaCrypter with Enter (Return) Key
        System.out.println("\nGo back to RSA Text Crypter Menu: Press Enter");
        CryptobyHelper.pressEnter();
        rsaCrypterText(console);
    }

    // Help Functions
    private static byte[] scanPrivateKey(CryptobyConsole console) {
        byte[] retKey = null;
        do {
            scanner = new Scanner(System.in);
            String keyText = "";
            // Input Private Key for decryption
            System.out.println("\nEnter the private Key (Type '" + quit + "' to Escape):");
            try {
                while (!scanner.hasNext(CryptobyHelper.getEOBString())) {
                    if (scanner.hasNext(quit)) {
                        rsaCrypterText(console);
                    }
                    keyText = keyText + scanner.next();
                }
                retKey = CryptobyHelper.hexStringToBytes(keyText);
                keySize = retKey.length * 4;
            } // Catch false format of Input
            catch (NumberFormatException exp) {
                System.out.println("Not allowed Characters in Private Key! Just lower alphanumeric Characters!");
                retKey = BigInteger.ZERO.toByteArray();
                keySize = 0;
            } catch (NullPointerException exp) {
                System.out.println("NullPointerException catched! Try again!");
                retKey = BigInteger.ZERO.toByteArray();
                keySize = 0;
            }
        } while (keySize != 1024 && keySize != 2048 && keySize != 4096);
        return retKey;
    }

    private static byte[] scanPublicKey(CryptobyConsole console) {
        byte[] retKey = null;
        do {
            scanner = new Scanner(System.in);
            String keyText = "";
            // Input Key for decryption
            System.out.println("\nEnter the public Key (Type '" + quit + "' to Escape):");
            try {

                while (!scanner.hasNext(CryptobyHelper.getEOBString())) {
                    if (scanner.hasNext(quit)) {
                        rsaCrypterText(console);
                    }
                    keyText = keyText + scanner.next();
                }
                retKey = CryptobyHelper.hexStringToBytes(keyText);
                keySize = retKey.length * 8;
            } catch (NumberFormatException exp) {
                System.out.println("Not allowed Characters in Private Key! Just lower alphanumeric Characters!");
                retKey = BigInteger.ZERO.toByteArray();
                keySize = 0;
            } catch (NullPointerException exp) {
                System.out.println("NullPointerException catched! Try again!");
                privateKeyByte = BigInteger.ZERO.toByteArray();
                keySize = 0;
            }
        } while (keySize != 1024 && keySize != 2048 && keySize != 4096);
        return retKey;

    }

    private static byte[] scanPlainText(CryptobyConsole console) {
        scanner = new Scanner(System.in);
        System.out.println("Your Text to encrypt (Type '" + quit + "' to Escape):");
        scanner.useDelimiter("\n");
        if (scanner.hasNext(quit)) {
            rsaCrypterText(console);
        }
        return scanner.next().getBytes();
    }

    private static void initRSAKeyGen(CryptobyConsole console) {
        console.getCore().getClient().setCryptAsymArt("RSA");
        console.getCore().initCryptAsym();
    }

}
