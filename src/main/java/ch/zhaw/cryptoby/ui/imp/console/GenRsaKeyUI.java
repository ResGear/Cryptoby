/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.zhaw.cryptoby.ui.imp.console;

import ch.zhaw.cryptoby.core.CryptobyHelper;
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
        String pubKey;
        String privKey;
        int choice;
        
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
        pubKey = console.getCore().getKeyGenAsym().getPublicKey();
        privKey = console.getCore().getKeyGenAsym().getPrivateKey();
        
        // Print Keys
        System.out.println("\nPublic Key RSA-"+keySize+":");
        System.out.println(pubKey);
        System.out.println("\nPrivate Key RSA-"+keySize+":");
        System.out.println(privKey);
        
        // Enter for Continues
        CryptobyHelper.pressEnter();
        
        // Back to Menu Choose PrimeTest
        console.menuGenKey();
    }
    
}
