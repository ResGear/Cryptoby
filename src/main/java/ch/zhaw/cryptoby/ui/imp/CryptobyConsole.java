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
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;


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
    private void chooseSymEnc(){
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
                aesCrypter();
                break;
            case 2:
                this.run();
                break;
            default:
                this.chooseSymEnc();
        }
    }
    
    private void aesCrypter(){
        int choice;
        
        do {
            System.out.println("What do want? Encryption or Decryption?");
            System.out.println("-------------------------\n");
            System.out.println("1 - Encryption");
            System.out.println("2 - Decryption");
            System.out.println("3 - Back");
            while (!scanner.hasNextInt()) {
                System.out.println("That's not a number! Enter 1,2 or 3:");
                scanner.next();
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > 3);
        
        switch (choice) {
            case 1:
                aesEncrypter();
                break;
            case 2:
                aesDecrypter();
                break;
            case 3:
                this.run();
                break;
            default:
                this.chooseSymEnc();
        }
    }
    
    private void aesEncrypter(){
        // Initial Variables
        byte[] plainText;
        byte[] cryptText;
        String cryptTextHex;
        byte[] key;
        int keySize;
              
        // Input your String Text to encrypt
        System.out.println("Your Text to encrypt:");
        plainText = scanner.next().getBytes();
        do {
            // Input your Key for encryption
            System.out.println("Enter the Key. The Key Size has to be 128,192 or 256bit in Hex Code");
            key = scanner.next().getBytes();
            keySize = key.length * 4;
            System.out.println(keySize);
        } while (keySize != 128 && keySize != 192 && keySize != 256);
        
        // Initial AES Crypt Object
        this.core.getClient().setCryptSymArt("AES");
        this.core.initCryptSym();
        
        // Encrypt the String Text with given Key
        cryptText = this.core.getCryptSym().encrypt(plainText, key);
        
        // Convert byte Array into a Hexcode String
        cryptTextHex = new String(Hex.encodeHex(cryptText));
        
        // Print encrypted Text in Hex form
        System.out.println("AES-"+keySize+" encrypted Text in Hex form:");
        System.out.println(cryptTextHex);
        
        // Enter for Continues
        try {
            System.in.read();
        } catch (IOException ex) {
            Logger.getLogger(CryptobyConsole.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Back to Menu Choose PrimeTest
        this.chooseSymEnc();
    }
    
    private void aesDecrypter(){
        // Initial Variables
        byte[] plainText = null;
        byte[] cryptText = null;
        String cryptTextHex;
        byte[] key;
        int keySize;
              
        // Input String Text to decrypt
        System.out.println("Your Text to decrypt:");
        cryptTextHex = scanner.next();
        do {
            // Input Key for decryption
            System.out.println("Enter the Key. The Key Size has to be 128,192 or 256bit in Hex Code");
            key = scanner.next().getBytes();
            keySize = key.length * 4;
        } while (keySize != 128 && keySize != 192 && keySize != 256);
        
        // Initial AES Crypt Object
        this.core.getClient().setCryptSymArt("AES");
        this.core.initCryptSym();
        
        // Convert byte Array into a Hexcode String
        try {
            cryptText = Hex.decodeHex(cryptTextHex.toCharArray());
        } catch (DecoderException ex) {
            Logger.getLogger(CryptobyConsole.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Decrypt the String Text with given Key
        try{
        plainText = this.core.getCryptSym().decrypt(cryptText, key);
        } catch(Exception e){
            System.out.println("Unable to decrypt this String!!");
            // Enter for Continues
            try {
                System.in.read();
            } catch (IOException ex) {
                Logger.getLogger(CryptobyConsole.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.aesCrypter();
        }
        
        // Print decrypted Text
        System.out.println("AES-"+keySize+" decrypted Text:");
        System.out.println(new String(plainText));
        
        // Enter for Continues
        try {
            System.in.read();
        } catch (IOException ex) {
            Logger.getLogger(CryptobyConsole.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Back to Menu Choose PrimeTest
        this.chooseSymEnc();
    }
    
    
    // Key Generator Menus
    private void chooseGenKey(){
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
                genSHA3Key();
                break;
            case 2:
                this.run();
                break;
            default:
                this.chooseGenKey();
        }
    }
    
    private void genSHA3Key() {
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
        
        // Initial Miller Rabin Object
        this.core.getClient().setKeyGenArt("SHA3");
        this.core.initKeyGen();
        
        // Get Result of Test
        if(password.equals("")) {
            key = this.core.getKeyGenPriv().generateKey(keySize);
        } else {
            key = this.core.getKeyGenPriv().generateKey(keySize, password);
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
        this.chooseGenKey();
    }
    
        
    // Primetest Menus
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
