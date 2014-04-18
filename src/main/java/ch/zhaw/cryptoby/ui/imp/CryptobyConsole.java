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
            System.out.println("Cryptoby - Select a menupoint!");
            System.out.println("-------------------------\n");
            System.out.println("1 - Symmetric Encryption");
            System.out.println("2 - Generate Key");
            System.out.println("3 - Primetest");
            System.out.println("4 - Quit");
            while (!scanner.hasNextInt()) {
                System.out.println("That's not a number! Enter 1,2,3 or 4:");
                scanner.next();
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > 4);
        
        switch (choice) {
            case 1:
                this.chooseSymEnc();
                break;
            case 2:
                this.chooseGenKey();
                break;
            case 3:
                this.choosePrimeTest();
                break;
            case 4:
                this.core.getClient().exitApp();
                break;
            default:
                this.run();
        }
    }
    
    // Symmetrich Cryption Menus
    public void chooseSymEnc(){
        int choice;
        
        do {
            System.out.println("Choose Symmetric Cryption Methode");
            System.out.println("-------------------------\n");
            System.out.println("1 - AES");
            System.out.println("2 - Back");
            while (!scanner.hasNextInt()) {
                System.out.println("That's not a number! Enter 1 or 2:");
                scanner.next();
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > 2);
        
        switch (choice) {
            case 1:
                AesUI.aesCrypter(this);
                break;
            case 2:
                this.run();
                break;
            default:
                this.chooseSymEnc();
        }
    }
      
    // Key Generator Menus
    public void chooseGenKey(){
        int choice;
        
        do {
            System.out.println("Choose Key Generator");
            System.out.println("-------------------------\n");
            System.out.println("1 - SHA3-Keccak");
            System.out.println("2 - Back");
            while (!scanner.hasNextInt()) {
                System.out.println("That's not a number! Enter 1 or 2:");
                scanner.next();
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > 2);
        
        switch (choice) {
            case 1:
                GenSHA3UI.genSHA3Key(this);
                break;
            case 2:
                this.run();
                break;
            default:
                this.chooseGenKey();
        }
    }
    
    public void choosePrimeTest() {
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
                MillerRabinUI.testMillerRabin(this);
                break;
            case 2:
                this.run();
                break;
            default:
                this.choosePrimeTest();
        }
    }
    
    public CryptobyCore getCore() {
        return core;
    }
    
}