/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.zhaw.cryptoby.ui.imp;

import ch.zhaw.cryptoby.client.CryptobyClient;
import ch.zhaw.cryptoby.prime.imp.MillerRabin;
import ch.zhaw.cryptoby.ui.itf.CryptobyUI;
import java.math.BigInteger;
import java.util.Scanner;


/**
 *
 * @author Toby
 */
public class CryptobyConsole implements CryptobyUI {

    private final CryptobyClient client;
    
    public CryptobyConsole(CryptobyClient client){
        this.client = client;
    }
    
    @Override
    public void startUI(){
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        System.out.println("Choose from these choices");
        System.out.println("-------------------------\n");
        System.out.println("1 - Enter an number");
        System.out.println("2 - Quit");
        switch (choice) {
            case 1:
                
                break;
            case 2:
                this.client.stop();
                break;
            default:
                this.startUI();
        }
    }

    private void choosePrimeTest() {
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        System.out.println("Choose PrimeTest Methode");
        System.out.println("-------------------------\n");
        System.out.println("1 - Miller Rabin");
        System.out.println("2 - Back");
        switch (choice) {
            case 1:
                testMillerRabin();
                break;
            case 2:
                this.startUI();
                break;
            default:
                // The user input an unexpected choice.
        }
    }

    private String testMillerRabin() {
        int rounds = 5;
        this.client.getCore().setPrimetest(new MillerRabin(rounds));
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        System.out.println("Insert Number for Testing");
        System.out.println("-------------------------\n");
        System.out.println("-------------------------\n");
        System.out.println("1 - Miller Rabin");
        System.out.println("2 - Back");
        switch (choice) {
            case 1:
                testMillerRabin();
                break;
            case 2:
                this.startUI();
                break;
            default:
                // The user input an unexpected choice.
        }
        String result;
        if(this.client.getCore().getPrimetest().isPrime(BigInteger.ZERO)) {
            result = "is probably a Primenumber";
        } else {
            result = "is not a Primenumber";
        }
        String percent = String.valueOf(this.client.getCore().getPrimetest().getProbability());
        return "Result: "+result+", Probably: "+percent;
    }
   

    
}
