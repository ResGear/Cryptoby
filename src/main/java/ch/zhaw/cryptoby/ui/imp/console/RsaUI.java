/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.zhaw.cryptoby.ui.imp.console;

import ch.zhaw.cryptoby.core.CryptobyHelper;
import java.math.BigInteger;
import java.util.Scanner;

/**
 *
 * @author Toby
 */
public class RsaUI {

    private static final String quit = "QuitCrypt";
    private static final String eob = "EndOfBlock";
    private static Scanner scanner = new Scanner(System.in);
    private static byte[] plainByte;
    private static byte[] cryptByte;
    private static char[] charTextHex;
    private static byte[] privateKeyByte;
    private static byte[] publicKeyByte;
    private static int choice;
    private static int keySize;

    public static void rsaCrypter(CryptobyConsole console) {
        scanner = new Scanner(System.in);

        do {
            System.out.println("\n");
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

        // Input your String Text to encrypt
        plainByte = RsaUI.scanPlainText(console);

        // Input the Public Key to encrypt
        publicKeyByte = RsaUI.scanPublicKey(console);

        // Initial RSA Crypt Object
        RsaUI.initRSAKeyGen(console);

        // Encrypt the String Text with given Key
        cryptByte = console.getCore().getCryptAsym().encrypt(plainByte, publicKeyByte);

        // Convert byte Array into a Hexcode String
        charTextHex = CryptobyHelper.bytesToHexStringUpper(cryptByte).toCharArray();

        // Print encrypted Text in Hex Block form
        RsaUI.printHexBlock(keySize, charTextHex);

        // Enter for Continues
        CryptobyHelper.pressEnter();

        // Back to Menu rsaCrypter
        RsaUI.rsaCrypter(console);
    }

    private static void rsaEncrypterGenKeys(CryptobyConsole console) {
        scanner = new Scanner(System.in);
        String privateKey;
        String publicKey;

        // Set Default Key Size
        keySize = 1024;

        do {
            System.out.println("\n");
            System.out.println("Choose Key Size");
            System.out.println("-------------------------\n");
            System.out.println("1 - 1024");
            System.out.println("2 - 2048");
            System.out.println("3 - 4096");
            System.out.println("4 - Back");
            while (!scanner.hasNextInt()) {
                System.out.println("That's not a number! Enter 1,2,3 or 4:");
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
                RsaUI.rsaCrypter(console);
                break;
            default:
                RsaUI.rsaCrypter(console);
        }

        // Input your String Text to encrypt
        plainByte = RsaUI.scanPlainText(console);

        // Initial RSA Crypt Object
        RsaUI.initRSAKeyGen(console);

        // Get Public Key in Bytecode
        console.getCore().getKeyGenAsym().initGenerator(keySize);
        publicKeyByte = console.getCore().getKeyGenAsym().getPublicKeyByte();

        // Get Public and Private Key as String
        publicKey = console.getCore().getKeyGenAsym().getPublicKey();
        privateKey = console.getCore().getKeyGenAsym().getPrivateKey();

        // Encrypt the String Text with given Key
        cryptByte = console.getCore().getCryptAsym().encrypt(plainByte, publicKeyByte);

        // Convert crypted byte Array into a Hexcode String
        charTextHex = CryptobyHelper.bytesToHexStringUpper(cryptByte).toCharArray();

        // Print encrypted Text in Hex Block form
        RsaUI.printHexBlock(keySize, charTextHex);

        // Print Private and Public Keys
        System.out.println("\nPrivate Key:");
        CryptobyHelper.charToBlockString(privateKey.toCharArray());
        System.out.println(eob);

        System.out.println("\nPublic Key:");
        CryptobyHelper.charToBlockString(publicKey.toCharArray());
        System.out.println(eob);

        // Press Return for Continues
        CryptobyHelper.pressEnter();

        // Back to Menu rsaCrypter
        RsaUI.rsaCrypter(console);
    }

    private static void rsaDecrypter(CryptobyConsole console) {
        scanner = new Scanner(System.in);

        // Input encrypted Hex String Text to decrypt
        System.out.println("\nYour Text to decrypt (Type '" + quit + "' to Escape):");

        // Convert crypted HexString Block to one String
        try {
            String cryptText = "";
            while (!scanner.hasNext(eob)) {
                if (scanner.hasNext(quit)) {
                    RsaUI.rsaCrypter(console);
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
        privateKeyByte = RsaUI.scanPrivateKey(console);

        // Initial RSA Crypt Object
        RsaUI.initRSAKeyGen(console);

        // Decrypt the String Text with given Key
        try {
            plainByte = console.getCore().getCryptAsym().decrypt(cryptByte, privateKeyByte);
        } catch (Exception e) {
            System.out.println("\nUnable to decrypt this String!!");
            plainByte = null;
            // Press Return for Continues
            CryptobyHelper.pressEnter();
            rsaCrypter(console);
        }

        // Print decrypted Text
        System.out.println("\nRSA-" + keySize + " decrypted Text:");
        System.out.println(new String(plainByte));

        // Press Return for Continues
        CryptobyHelper.pressEnter();

        // Back to Menu rsaCrypter
        RsaUI.rsaCrypter(console);
    }
    
    private static void printHexBlock(int inputKeySize,  char[] inputCharTextHex){
        System.out.println("\nRSA-" + inputKeySize + " encrypted Text in Hex form (Copy with '" + eob + "'):");
        CryptobyHelper.charToBlockString(inputCharTextHex);
        System.out.println(eob);
    }

    private static byte[] scanPrivateKey(CryptobyConsole console) {
        byte[] retKey = null;
        do {
            scanner = new Scanner(System.in);
            String keyText = "";
            // Input Private Key for decryption
            System.out.println("\nEnter the private Key (Type '" + quit + "' to Escape):");
            try {
                while (!scanner.hasNext(eob)) {
                    if (scanner.hasNext(quit)) {
                        RsaUI.rsaCrypter(console);
                    }
                    keyText = keyText + scanner.next();
                }
                retKey = new BigInteger(keyText, Character.MAX_RADIX).toByteArray();
                keySize = retKey.length * 2;
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

                while (!scanner.hasNext(eob)) {
                    if (scanner.hasNext(quit)) {
                        RsaUI.rsaCrypter(console);
                    }
                    keyText = keyText + scanner.next();
                }
                retKey = new BigInteger(keyText, Character.MAX_RADIX).toByteArray();
                keySize = retKey.length * 4;
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
            RsaUI.rsaCrypter(console);
        }
        return scanner.next().getBytes();
    }

    private static void initRSAKeyGen(CryptobyConsole console) {
        console.getCore().getClient().setCryptAsymArt("RSA");
        console.getCore().initCryptAsym();
    }

}
