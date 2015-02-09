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

import helper.CryptobyHelper;
import java.math.BigInteger;
import java.util.Scanner;

/**
 * This class provides menus in console UI for Miller Rabin implementation.
 *
 * @author Tobias Rees
 */
public class MillerRabinUI {

    /**
     *
     * @param console
     */
    public static void testMillerRabin(CryptobyConsole console){
        final Scanner scanner = new Scanner(System.in);
        // Initial Variables
        int rounds;
        String percent;
        BigInteger number;
        
        // Input Number for Primenumber Testing
        do {
            System.out.println("Set Primenumber to Test.");
            System.out.print("Please enter a positive number: ");
            while (!scanner.hasNextBigInteger()) {
                System.out.print("That's not a number! Enter a positive number: ");
                scanner.next();
            }
            number = scanner.nextBigInteger();
        } while (number.compareTo(BigInteger.ONE) < 0 );
        
        // Set the rounds of the Miller Rabin Test
        do {
            System.out.println("Set rounds parameter between 1 and 15.");
            System.out.print("Please enter the number of rounds: ");
            while (!scanner.hasNextInt()) {
                System.out.print("That's not a number! Enter a valid number: ");
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
            percent = String.valueOf(console.getCore().getPrimetest().getProbability());
            System.out.println("\nResult: Number is probably a Primenumber, probability: "+percent+"%");
        } else {
            System.out.println("\nResult: Number is NOT a Primenumber");
        }
        
        // Back to Menu Choose PrimeTest
        System.out.println("\nGo back to Primetest Menu: Press Enter");
        CryptobyHelper.pressEnter();
        console.menuPrimeTest();
    }
    
}
