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

import ch.zhaw.cryptoby.core.CryptobyCore;
import ch.zhaw.cryptoby.ui.itf.CryptobyUI;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CryptobyConsole implements CryptobyUI {

    private final CryptobyCore core;
    private final Scanner scanner = new Scanner(System.in);

    public CryptobyConsole(CryptobyCore core) {
        this.core = core;
    }

    @Override
    public void run() {
        int choice;

        do {
            System.out.println("Cryptoby - Select a menupoint!");
            System.out.println("-------------------------\n");
            System.out.println("1 - Crypt Files (not implemented yet!!)");
            System.out.println("2 - Crypt Stringtext");
            System.out.println("3 - Generate Key");
            System.out.println("4 - Primetest");
            System.out.println("5 - Quit");
            while (!scanner.hasNextInt()) {
                System.out.println("That's not a number! Enter 1,2,3 or 4:");
                scanner.next();
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > 5);

        switch (choice) {
            case 1:
                this.menuFileCrypt();
                break;
            case 2:
                this.menuStringCrypt();
                break;
            case 3:
                this.menuGenKey();
                break;
            case 4:
                this.menuPrimeTest();
                break;
            case 5:
                this.core.getClient().exitApp();
                break;
            default:
                this.run();
        }
    }

    public void menuFileCrypt() {
        System.out.println("Coming soon!!\n");
        // Enter for Continues
        try {
            System.in.read();
        } catch (IOException ex) {
            Logger.getLogger(CryptobyConsole.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.run();
    }

    public void menuStringCrypt() {
        int choice;

        do {
            System.out.println("Select Cryptology Type");
            System.out.println("-------------------------\n");
            System.out.println("1 - Asymmetric Cryption");
            System.out.println("2 - Symmetric Cryption");
            System.out.println("3 - Back");
            while (!scanner.hasNextInt()) {
                System.out.println("That's not a number! Enter 1,2 or 3:");
                scanner.next();
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > 3);

        switch (choice) {
            case 1:
                this.menuStringAsym();
                break;
            case 2:
                this.menuStringSym();
                break;
            case 3:
                this.run();
                break;
            default:
                this.menuStringCrypt();
        }
    }

    // Symmetric File Cryption Menu
    public void menuFileSym() {
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
                this.menuStringCrypt();
                break;
            default:
                this.menuStringSym();
        }
    }

    // Asymmetric File Cryption Menu
    public void menuFileAsym() {
        int choice;

        do {
            System.out.println("Choose Asymmetric Cryption Methode");
            System.out.println("-------------------------\n");
            System.out.println("1 - RSA");
            System.out.println("2 - Back");
            while (!scanner.hasNextInt()) {
                System.out.println("That's not a number! Enter 1 or 2:");
                scanner.next();
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > 2);

        switch (choice) {
            case 1:
                RsaUI.rsaCrypter(this);
                break;
            case 2:
                this.run();
                break;
            default:
                this.menuStringSym();
        }
    }

    // Symmetric String Cryption Menu
    public void menuStringSym() {
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
                this.menuStringCrypt();
                break;
            default:
                this.menuStringSym();
        }
    }

    // Asymmetric String Cryption Menu
    public void menuStringAsym() {
        int choice;

        do {
            System.out.println("Choose Asymmetric Cryption Methode");
            System.out.println("-------------------------\n");
            System.out.println("1 - RSA");
            System.out.println("2 - Back");
            while (!scanner.hasNextInt()) {
                System.out.println("That's not a number! Enter 1 or 2:");
                scanner.next();
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > 2);

        switch (choice) {
            case 1:
                RsaUI.rsaCrypter(this);
                break;
            case 2:
                this.menuStringCrypt();
                break;
            default:
                this.menuStringSym();
        }
    }

    // Key Generator Menu
    public void menuGenKey() {
        int choice;

        do {
            System.out.println("Choose Key Generator");
            System.out.println("-------------------------\n");
            System.out.println("1 - SHA3-Keccak");
            System.out.println("2 - RSA Private/Public Keys");
            System.out.println("3 - Back");
            while (!scanner.hasNextInt()) {
                System.out.println("That's not a number! Enter 1,2 or 3:");
                scanner.next();
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > 3);

        switch (choice) {
            case 1:
                GenSHA3UI.genSHA3Key(this);
                break;
            case 2:
                GenRsaKeyUI.genRSAKeys(this);
                break;
            case 3:
                this.run();
                break;
            default:
                this.menuGenKey();
        }
    }

    // Prime Test Menu
    public void menuPrimeTest() {
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
                this.menuPrimeTest();
        }
    }

    public CryptobyCore getCore() {
        return core;
    }

}
