/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.zhaw.cryptoby.ui.imp;

import ch.zhaw.cryptoby.core.CryptobyCore;
import ch.zhaw.cryptoby.ui.itf.CryptobyUI;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Toby
 */
public class CryptobyConsole implements CryptobyUI {

    private final CryptobyCore core;
    private final Scanner scanner = new Scanner(System.in);
    
    public CryptobyConsole(CryptobyCore core){
        this.core = core;
    }
    
    @Override
    public void run(){
        int choice;
        
        do {
            System.out.println("Cryptoby - Enter one these menus!");
            System.out.println("-------------------------\n");
            System.out.println("1 - Primetest");
            System.out.println("2 - Quit");
            while (!scanner.hasNextInt()) {
                System.out.println("That's not a number! Enter 1 or 2:");
                scanner.next();
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > 2);
        
        switch (choice) {
            case 1:
                this.choosePrimeTest();
                break;
            case 2:
                this.core.getClient().exitApp();
                break;
            default:
                this.run();
        }
    }

    private void choosePrimeTest() {
        int choice;
        
        do {
            System.out.println("Choose PrimeTest Methode");
            System.out.println("-------------------------\n");
            System.out.println("1 - Miller Rabin");
            System.out.println("2 - Back");
            while (!scanner.hasNextInt()) {
                System.out.println("That's not a number! Enter 1 or 2:");
                scanner.next();
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > 2);
        
        switch (choice) {
            case 1:
                testMillerRabin();
                break;
            case 2:
                this.run();
                break;
            default:
                this.choosePrimeTest();
        }
    }

    private void testMillerRabin(){
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
        } while (number.compareTo(BigInteger.ONE) <= 0 );
        
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
        this.core.getClient().setPrimTestArt("MillerRabin");
        this.core.getClient().setPrimetestrounds(rounds);
        this.core.initPrimeTest();
        
        // Get Result of Test
        if(this.core.getPrimetest().isPrime(number)) {
            result = "is probably a Primenumber";
        } else {
            result = "is not a Primenumber";
        }
        
        // Get Probably
        percent = String.valueOf(this.core.getPrimetest().getProbability());
        
        // Print Results
        System.out.println("Result: "+result+", Probably: "+percent);
        
        // Enter for Continues
        try {
            System.in.read();
        } catch (IOException ex) {
            Logger.getLogger(CryptobyConsole.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Back to Menu Choose PrimeTest
        this.choosePrimeTest();
    }
}
