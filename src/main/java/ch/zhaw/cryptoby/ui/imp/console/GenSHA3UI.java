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

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GenSHA3UI {
 
        public static void genSHA3Key(CryptobyConsole console) {
        final Scanner scanner = new Scanner(System.in);
        // Initial Variables
        int keySize;
        String pwAns;
        String key;
        String password;
        
        // Input Size of the SHA Key: possible are 224,256,384,512
        do {
            System.out.println("Set Key Size");
            System.out.println("Please enter one of these Sizes: 224 256 384 512");
            while (!scanner.hasNextInt()) {
                System.out.println("That's not a number! Enter a positive number:");
                scanner.next();
            }
            keySize = scanner.nextInt();
        } while (keySize != 224 && keySize != 256 && keySize != 384 && keySize != 512 );
        
        // Input a Password or nothing, in the case it will be used a Secure Random number
        do {
            System.out.println("Do want use password. If not, it will be used a SecureRandom password.");
            System.out.println("Enter y or n:");
            pwAns = scanner.next();
        } while (!pwAns.equals("y") && !pwAns.equals("n"));
        
        if(pwAns.equals("y")){
            System.out.println("Enter Password for the Key:");
            password = scanner.next();
        } else {
            password = "";
        }
        
        // Initial Key Generator
        console.getCore().getClient().setKeySymArt("SHA3");
        console.getCore().initSymKey();
        
        // Get Result of Test
        if(password.equals("")) {
            key = console.getCore().getKeyGenSym().generateKey(keySize);
        } else {
            key = console.getCore().getKeyGenSym().generateKey(keySize, password);
        }
        
        // Print Key
        System.out.println("SHA3-"+keySize+": "+key);
        
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
