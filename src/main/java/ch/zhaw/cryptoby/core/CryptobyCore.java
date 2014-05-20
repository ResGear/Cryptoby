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
package ch.zhaw.cryptoby.core;

import ch.zhaw.cryptoby.asym.imp.CryptRSA;
import ch.zhaw.cryptoby.asym.itf.CryptAsym;
import ch.zhaw.cryptoby.client.CryptobyClient;
import ch.zhaw.cryptoby.keygen.imp.KeyGenRSA;
import ch.zhaw.cryptoby.keygen.imp.KeyGenSHA3;
import ch.zhaw.cryptoby.keygen.itf.KeyGenAsym;
import ch.zhaw.cryptoby.keygen.itf.KeyGenSym;
import ch.zhaw.cryptoby.prime.imp.MillerRabin;
import ch.zhaw.cryptoby.prime.itf.PrimeTest;
import ch.zhaw.cryptoby.sym.imp.CryptAES;
import ch.zhaw.cryptoby.sym.itf.CryptSym;
import ch.zhaw.cryptoby.ui.imp.console.CryptobyConsole;
import ch.zhaw.cryptoby.ui.itf.CryptobyUI;

/**
 * This class create objects of all interfaces in this application with in the
 * client object defined implementation of these interfaces. User Interfaces get
 * and set all methods of other interfaces over this core object.
 *
 * @author Tobias Rees
 */
public final class CryptobyCore {

    private CryptSym cryptSym;
    private CryptAsym cryptAsym;
    private KeyGenSym keyGenSym;
    private KeyGenAsym keyGenAsym;
    private PrimeTest primetest;
    private CryptobyClient client;
    private CryptobyUI ui;

    /**
     * Constructor set given CryptobyClient object to client variable and
     * initialize every interface with from client given implementation
     *
     * @param client Need CryptobyClient object with defined implementation of
     * used interfaces
     */
    public CryptobyCore(CryptobyClient client) {
        this.setClient(client);
        this.initCryptSym();
        this.initCryptAsym();
        this.initSymKey();
        this.initAsymKey();
        this.initPrimeTest();
        this.initUI();
    }

    /**
     * Initialize symmetric cryptologic mode which is defined in client object
     */
    public void initCryptSym() {
        if (this.client.getCryptSymArt().equals("AES")) {
            this.setCryptSym(new CryptAES());
        } else {
            this.setCryptSym(new CryptAES());
        }
    }

    /**
     * Initialize asymmetric cryptologic mode which is defined in client object
     */
    public void initCryptAsym() {
        if (this.client.getCryptAsymArt().equals("RSA")) {
            this.setCryptAsym(new CryptRSA());
        } else {
            this.setCryptAsym(new CryptRSA());
        }
    }

    /**
     * Initialize symmetric key generator which is defined in client object
     */
    public void initSymKey() {
        if (this.client.getKeySymArt().equals("SHA3")) {
            this.setKeyGenSym(new KeyGenSHA3());
        } else {
            this.setKeyGenSym(new KeyGenSHA3());
        }
    }

    /**
     * Initialize asymmetric key generator which is defined in client object
     */
    public void initAsymKey() {
        if (this.client.getKeySymArt().equals("RSA")) {
            this.setKeyGenAsym(new KeyGenRSA(this));
        } else {
            this.setKeyGenAsym(new KeyGenRSA(this));
        }
    }

    /**
     * Initialize Primetest mode which is defined in client object
     */
    public void initPrimeTest() {
        if (this.client.getPrimTestArt().equals("MillerRabin")) {
            this.setPrimetest(new MillerRabin(this.client.getPrimetestrounds()));
        } else {
            this.setPrimetest(new MillerRabin(this.client.getPrimetestrounds()));
        }
    }

    /**
     * Initialize User Interface which is defined in client object
     */
    public void initUI() {
        if (this.client.getUi().equals("console")) {
            this.ui = new CryptobyConsole(this);
        } else {
            this.ui = new CryptobyConsole(this);
        }
    }

    /**
     *
     * @return Get Primetest object
     */
    public PrimeTest getPrimetest() {
        return primetest;
    }

    /**
     *
     * @param primetest Set implementation Primetest object
     */
    public void setPrimetest(PrimeTest primetest) {
        this.primetest = primetest;
    }

    /**
     *
     * @return Get CryptobyClient object
     */
    public CryptobyClient getClient() {
        return client;
    }

    private void setClient(CryptobyClient client) {
        this.client = client;
    }

    /**
     *
     * @return Get User Interface object
     */
    public CryptobyUI getUi() {
        return ui;
    }

    /**
     *
     * @param ui Set implementation User Interface object
     */
    public void setUi(CryptobyUI ui) {
        this.ui = ui;
    }

    /**
     *
     * @return Get object of symmetric key generator implementation
     */
    public KeyGenSym getKeyGenSym() {
        return keyGenSym;
    }

    /**
     *
     * @param keyGenSym Set object of symmetric key generator implementation
     */
    public void setKeyGenSym(KeyGenSym keyGenSym) {
        this.keyGenSym = keyGenSym;
    }

    /**
     *
     * @return Get object of asymmetric key generator implementation
     */
    public KeyGenAsym getKeyGenAsym() {
        return keyGenAsym;
    }

    /**
     *
     * @param keyGenAsym Set object of asymmetric key generator implementation
     */
    public void setKeyGenAsym(KeyGenAsym keyGenAsym) {
        this.keyGenAsym = keyGenAsym;
    }

    /**
     *
     * @return Get object of symmetric cryptology mode implementation
     */
    public CryptSym getCryptSym() {
        return cryptSym;
    }

    /**
     *
     * @param cryptSym Set object of symmetric cryptology mode implementation
     */
    public void setCryptSym(CryptSym cryptSym) {
        this.cryptSym = cryptSym;
    }

    /**
     *
     * @return Get object of asymmetric cryptology mode implementation
     */
    public CryptAsym getCryptAsym() {
        return cryptAsym;
    }

    /**
     *
     * @param cryptAsym Set object of asymmetric cryptology mode implementation
     */
    public void setCryptAsym(CryptAsym cryptAsym) {
        this.cryptAsym = cryptAsym;
    }

}
