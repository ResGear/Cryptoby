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
package ui.imp.console;

import filemgr.CryptobyFileManager;
import helper.CryptobyHelper;
import java.io.IOException;
import java.util.Scanner;

/**
 * This class provides menus in console UI for RSA generator implementation.
 *
 * @author Tobias Rees
 */
public class GenRsaKeyUI {

    private static final String quit = "QuitCrypt";

    /**
     *
     * @param console
     */
    public static void genRSAKeysText(CryptobyConsole console) {
        final Scanner scanner = new Scanner(System.in);

        // Initial Variables
        int keySize;
        int choice;
        String publicKey;
        String privateKey;

        // Set Default Key Size
        keySize = 1024;

        do {
            System.out.println("\n");
            System.out.println("Choose Key  in Bit");
            System.out.println("-------------------------\n");
            System.out.println("1 - 1024");
            System.out.println("2 - 2048");
            System.out.println("3 - 4096");
            System.out.println("4 - Back");
            System.out.print("Enter Number: ");
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
                console.menuGenKey();
                break;
            default:
                console.menuGenKey();
        }

        // Initial Key Generator
        console.getCore().getClient().setKeyAsymArt("RSA");
        console.getCore().initAsymKey();

        // Generate Keys
        console.getCore().getKeyGenAsym().initGenerator(keySize);
        publicKey = console.getCore().getKeyGenAsym().getPublicKey();
        privateKey = console.getCore().getKeyGenAsym().getPrivateKey();

        // Print Private Keys
        System.out.println(CryptobyHelper.printPrivateKeyBlock(privateKey));
        // Print Public Keys
        System.out.println(CryptobyHelper.printPublicKeyBlock(publicKey));

        // Enter for Continues
        CryptobyHelper.pressEnter();

        // Back to Menu Choose PrimeTest
        scanner.close();
        console.menuGenKey();
    }

    /**
     *
     * @param console
     */
    public static void genRSAKeysFile(CryptobyConsole console) {
        final Scanner scanner = new Scanner(System.in);
        String privateKeyPath;
        String publicKeyPath;

        // Initial Variables
        int keySize;
        int choice;
        String publicKey;
        String privateKey;

        // Set Default Key Size
        keySize = 1024;

        do {
            System.out.println("\n");
            System.out.println("Choose Key  in Bit");
            System.out.println("-------------------------\n");
            System.out.println("1 - 1024");
            System.out.println("2 - 2048");
            System.out.println("3 - 4096");
            System.out.println("4 - Back");
            System.out.print("Enter Number: ");
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
                console.menuGenKey();
                break;
            default:
                console.menuGenKey();
        }

        // Input Path for saving Private Key
        System.out.println("Enter Path to saving Private Key (Type '" + quit + "' to Escape):");
        scanner.useDelimiter("\n");
        if (scanner.hasNext(quit)) {
            RsaUI.rsaCrypterFile(console);
        }
        privateKeyPath = scanner.next();

        // Input Path for saving Public Key
        System.out.println("Enter Path to saving Public Key (Type '" + quit + "' to Escape):");
        scanner.useDelimiter("\n");

        publicKeyPath = scanner.next();

        // Initial Key Generator
        console.getCore().getClient().setKeyAsymArt("RSA");
        console.getCore().initAsymKey();

        // Generate Keys
        console.getCore().getKeyGenAsym().initGenerator(keySize);
        publicKey = console.getCore().getKeyGenAsym().getPublicKey();
        privateKey = console.getCore().getKeyGenAsym().getPrivateKey();

        //Put private Key to File
        try {
            CryptobyFileManager.putKeyToFile(privateKeyPath, privateKey);
        } catch (IOException ex) {
            CryptobyHelper.printIOExp();
            console.menuGenKey();
        }
        System.out.println("\nPrivate Key File saved to this Path:");
        System.out.println(privateKeyPath);

        //Put public Key to File
        try {
            CryptobyFileManager.putKeyToFile(publicKeyPath, publicKey);
        } catch (IOException ex) {
            CryptobyHelper.printIOExp();
            console.menuGenKey();
        }
        System.out.println("\nPublic Key File saved to this Path:");
        System.out.println(publicKeyPath);

        // Enter for Continues
        CryptobyHelper.pressEnter();
        scanner.close();
        // Back to Menu Choose PrimeTest
        console.menuGenKey();
    }

}
