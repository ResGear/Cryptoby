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

import core.CryptobyCore;
import ui.itf.CryptobyUI;
import java.util.Scanner;

/**
 * This class provides main menus for console UI.
 *
 * @author Tobias Rees
 */
public class CryptobyConsole implements CryptobyUI {

    private final CryptobyCore core;
    private final Scanner scanner = new Scanner(System.in);

    /**
     *
     * @param core
     */
    public CryptobyConsole(CryptobyCore core) {
        this.core = core;
    }

    /**
     *
     */
    @Override
    public void run() {
        int choice;

        do {
            System.out.println("\nCryptoby - Crypt your Stuff");
            System.out.println("---------------------------\n");
            System.out.println("1 - Crypt Files");
            System.out.println("2 - Crypt Text");
            System.out.println("3 - Generate Key");
            System.out.println("4 - Primetest");
            System.out.println("5 - Quit");
            System.out.print("Enter Number: ");
            while (!scanner.hasNextInt()) {
                System.out.print("That's not a number! Enter 1,2,3,4 or 5: ");
                scanner.next();
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > 5);

        switch (choice) {
            case 1:
                this.menuFileCrypt();
                break;
            case 2:
                this.menuTextCrypt();
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

    /**
     *
     */
    public void menuTextCrypt() {
        int choice;

        do {
            System.out.println("\n");
            System.out.println("Text Cryption Menu");
            System.out.println("Select Cryptology Type");
            System.out.println("----------------------\n");
            System.out.println("1 - Asymmetric Cryption");
            System.out.println("2 - Symmetric Cryption");
            System.out.println("3 - Back");
            System.out.print("Enter Number: ");
            while (!scanner.hasNextInt()) {
                System.out.print("That's not a number! Enter 1,2 or 3: ");
                scanner.next();
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > 3);

        switch (choice) {
            case 1:
                this.menuTextAsym();
                break;
            case 2:
                this.menuTextSym();
                break;
            case 3:
                this.run();
                break;
            default:
                this.menuTextCrypt();
        }
    }

    // Symmetric String Cryption Menu

    /**
     *
     */
        public void menuTextSym() {
        int choice;

        do {
            System.out.println("\n");
            System.out.println("Text Cryption Menu");
            System.out.println("Select Symmetric Cryption Methode");
            System.out.println("---------------------------------\n");
            System.out.println("1 - AES");
            System.out.println("2 - Back");
            System.out.print("Enter Number: ");
            while (!scanner.hasNextInt()) {
                System.out.print("That's not a number! Enter 1 or 2: ");
                scanner.next();
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > 2);

        switch (choice) {
            case 1:
                AesUI.aesCrypterText(this);
                break;
            case 2:
                this.menuTextCrypt();
                break;
            default:
                this.menuTextSym();
        }
    }

    // Asymmetric String Cryption Menu

    /**
     *
     */
        public void menuTextAsym() {
        int choice;

        do {
            System.out.println("\n");
            System.out.println("Text Cryption Menu");
            System.out.println("Select Asymmetric Cryption Methode");
            System.out.println("----------------------------------\n");
            System.out.println("1 - RSA");
            System.out.println("2 - Back");
            System.out.print("Enter Number: ");
            while (!scanner.hasNextInt()) {
                System.out.print("That's not a number! Enter 1 or 2:");
                scanner.next();
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > 2);

        switch (choice) {
            case 1:
                RsaUI.rsaCrypterText(this);
                break;
            case 2:
                this.menuTextCrypt();
                break;
            default:
                this.menuTextSym();
        }
    }

    /**
     *
     */
    public void menuFileCrypt() {
        int choice;

        do {
            System.out.println("\n");
            System.out.println("File Cryption Menu");
            System.out.println("Select Cryptology Type");
            System.out.println("----------------------\n");
            System.out.println("1 - Asymmetric Cryption");
            System.out.println("2 - Symmetric Cryption");
            System.out.println("3 - Back");
            System.out.print("Enter Number: ");
            while (!scanner.hasNextInt()) {
                System.out.print("That's not a number! Enter 1,2 or 3: ");
                scanner.next();
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > 3);

        switch (choice) {
            case 1:
                this.menuFileAsym();
                break;
            case 2:
                this.menuFileSym();
                break;
            case 3:
                this.run();
                break;
            default:
                this.menuFileCrypt();
        }
    }

    // Symmetric File Cryption Menu

    /**
     *
     */
        public void menuFileSym() {
        int choice;

        do {
            System.out.println("\n");
            System.out.println("File Cryption Menu");
            System.out.println("Select Symmetric Cryption Methode");
            System.out.println("---------------------------------\n");
            System.out.println("1 - AES");
            System.out.println("2 - Back");
            System.out.print("Enter Number: ");
            while (!scanner.hasNextInt()) {
                System.out.print("That's not a number! Enter 1 or 2: ");
                scanner.next();
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > 2);

        switch (choice) {
            case 1:
                AesUI.aesCrypterFile(this);
                break;
            case 2:
                this.menuFileCrypt();
                break;
            default:
                this.menuFileSym();
        }
    }

    // Asymmetric File Cryption Menu

    /**
     *
     */
        public void menuFileAsym() {
        int choice;

        do {
            System.out.println("\n");
            System.out.println("File Cryption Menu");
            System.out.println("Select Asymmetric Cryption Methode");
            System.out.println("----------------------------------\n");
            System.out.println("1 - RSA");
            System.out.println("2 - Back");
            System.out.print("Enter Number: ");
            while (!scanner.hasNextInt()) {
                System.out.print("That's not a number! Enter 1 or 2: ");
                scanner.next();
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > 2);

        switch (choice) {
            case 1:
                RsaUI.rsaCrypterFile(this);
                break;
            case 2:
                this.menuFileCrypt();
                break;
            default:
                this.menuFileAsym();
        }
    }

    // Key Generator Menu

    /**
     *
     */
        public void menuGenKey() {
        int choice;

        do {
            System.out.println("\n");
            System.out.println("Select Key Generator");
            System.out.println("--------------------\n");
            System.out.println("1 - SHA3-Keccak Key as Text");
            System.out.println("2 - SHA3-Keccak Key as File");
            System.out.println("3 - RSA Keys as Text");
            System.out.println("4 - RSA Keys as Files");
            System.out.println("5 - Back");
            System.out.print("Enter Number: ");
            while (!scanner.hasNextInt()) {
                System.out.print("That's not a number! Enter 1,2,3,4 or 5:");
                scanner.next();
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > 5);

        switch (choice) {
            case 1:
                GenSHA3UI.genSHA3KeyText(this);
                break;
            case 2:
                GenSHA3UI.genSHA3KeyFile(this);
                break;
            case 3:
                GenRsaKeyUI.genRSAKeysText(this);
                break;
            case 4:
                GenRsaKeyUI.genRSAKeysFile(this);
                break;
            case 5:
                this.run();
                break;
            default:
                this.menuGenKey();
        }
    }

    // Prime Test Menu

    /**
     *
     */
        public void menuPrimeTest() {
        int choice;

        do {
            System.out.println("\n");
            System.out.println("Select PrimeTest Methode");
            System.out.println("------------------------\n");
            System.out.println("1 - Miller Rabin");
            System.out.println("2 - Back");
            System.out.print("Enter Number: ");
            while (!scanner.hasNextInt()) {
                System.out.print("That's not a number! Enter 1 or 2: ");
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

    /**
     *
     * @return
     */
    public CryptobyCore getCore() {
        return core;
    }

}
