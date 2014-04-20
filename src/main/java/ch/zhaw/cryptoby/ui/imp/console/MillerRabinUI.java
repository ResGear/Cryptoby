/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.zhaw.cryptoby.ui.imp.console;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MillerRabinUI {
    
        public static void testMillerRabin(CryptobyConsole console){
        final Scanner scanner = new Scanner(System.in);
        // Initial Variables
        int rounds;
        String result;
        String percent;
        BigInteger number;
        
        // Input Number for Primenumber Testing
        do {
            System.out.println("Set Primenumber to Test.");
            System.out.println("Please enter a positive number:");
            while (!scanner.hasNextBigInteger()) {
                System.out.println("That's not a number! Enter a positive number:");
                scanner.next();
            }
            number = scanner.nextBigInteger();
        } while (number.compareTo(BigInteger.ONE) < 0 );
        
        // Set the rounds of the Miller Rabin Test
        do {
            System.out.println("Set rounds parameter between 1 and 15.");
            System.out.println("Please enter the number of rounds:");
            while (!scanner.hasNextInt()) {
                System.out.println("That's not a number! Enter a valid number:");
                scanner.next();
            }
            rounds = scanner.nextInt();
        } while (rounds < 1 || rounds > 15);
        
        // Initial Miller Rabin Object
        console.getCore().getClient().setPrimTestArt("MillerRabin");
        console.getCore().getClient().setPrimetestrounds(rounds);
        console.getCore().initPrimeTest();
        
        // Get Result of Test
        if(console.getCore().getPrimetest().isPrime(number)) {
            result = "is probably a Primenumber";
        } else {
            result = "is not a Primenumber";
        }
        
        // Get Probably
        percent = String.valueOf(console.getCore().getPrimetest().getProbability());
        
        // Print Results
        System.out.println("Result: "+result+", Probably: "+percent);
        
        // Enter for Continues
        try {
            System.in.read();
        } catch (IOException ex) {
            Logger.getLogger(CryptobyConsole.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Back to Menu Choose PrimeTest
        console.menuPrimeTest();
    }
    
}
