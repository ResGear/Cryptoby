/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.zhaw.cryptoby.ui.imp.console;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        
        // Input Size of the SHA Key: possible are 224,256,384,512
        do {
            System.out.println("Set Key Size");
            System.out.println("Please enter one of these Sizes: 1024 2048 4096");
            while (!scanner.hasNextInt()) {
                System.out.println("That's not a number! Enter a positive number:");
                scanner.next();
            }
            keySize = scanner.nextInt();
        } while (keySize != 1024 && keySize != 2048 && keySize != 4096);
        
        // Initial Key Generator
        console.getCore().getClient().setKeyAsymArt("RSA");
        console.getCore().initAsymKey();
        
        // Generate Keys
        console.getCore().getKeyGenAsym().initGenerator(keySize);
        pubKey = console.getCore().getKeyGenAsym().getPublicKey();
        privKey = console.getCore().getKeyGenAsym().getPrivateKey();
        
        // Print Keys
        System.out.println("Public Key RSA-"+keySize+": "+pubKey);
        System.out.println("Private Key RSA-"+keySize+": "+privKey);
        
        // Enter for Continues
        try {
            System.in.read();
        } catch (IOException ex) {
            Logger.getLogger(CryptobyConsole.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Back to Menu Choose PrimeTest
        console.menuGenKey();
    }
    
}
