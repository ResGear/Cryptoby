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
public class GenRsaKeyUI {

    public static void genRSAKeys(CryptobyConsole console) {
        final Scanner scanner = new Scanner(System.in);
        
        // Initial Variables
        int keySize;
        int choice;
        String publicKey;
        String privateKey;
        String eob = "EndOfBlock";

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
        System.out.println("\nPrivate Key:");
        CryptobyHelper.charToBlockString(privateKey.toCharArray());
        System.out.println(eob);

        // Print Public Keys
        System.out.println("\nPublic Key:");
        CryptobyHelper.charToBlockString(publicKey.toCharArray());
        System.out.println(eob);

        // Enter for Continues
        CryptobyHelper.pressEnter();

        // Back to Menu Choose PrimeTest
        console.menuGenKey();
    }

}
