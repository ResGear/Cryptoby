/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 *
 * @author Toby
 */
public final class CryptobyCore {

    private CryptSym cryptSym;
    private CryptAsym cryptAsym;
    private KeyGenSym keyGenSym;
    private KeyGenAsym keyGenAsym;
    private PrimeTest primetest;
    private CryptobyClient client;
    private CryptobyUI ui;

    public CryptobyCore(CryptobyClient client) {
        this.setClient(client);
        this.initCryptSym();
        this.initCryptAsym();
        this.initSymKey();
        this.initAsymKey();
        this.initPrimeTest();
        this.initUI();
    }

    public void initCryptSym() {
        if (this.client.getCryptSymArt().equals("AES")) {
            this.setCryptSym(new CryptAES());
        } else {
            this.setCryptSym(new CryptAES());
        }
    }

    public void initCryptAsym() {
        if (this.client.getCryptAsymArt().equals("RSA")) {
            this.setCryptAsym(new CryptRSA());
        } else {
            this.setCryptAsym(new CryptRSA());
        }
    }

    public void initSymKey() {
        if (this.client.getKeySymArt().equals("SHA3")) {
            this.setKeyGenSym(new KeyGenSHA3());
        } else {
            this.setKeyGenSym(new KeyGenSHA3());
        }
    }

    public void initAsymKey() {
        if (this.client.getKeySymArt().equals("RSA")) {
            this.setKeyGenAsym(new KeyGenRSA(this));
        } else {
            this.setKeyGenAsym(new KeyGenRSA(this));
        }
    }

    public void initPrimeTest() {
        if (this.client.getPrimTestArt().equals("MillerRabin")) {
            this.setPrimetest(new MillerRabin(this.client.getPrimetestrounds()));
        } else {
            this.setPrimetest(new MillerRabin(this.client.getPrimetestrounds()));
        }
    }

    public void initUI() {
        if (this.client.getUi().equals("console")) {
            this.ui = new CryptobyConsole(this);
        } else {
            this.ui = new CryptobyConsole(this);
        }
    }

    public PrimeTest getPrimetest() {
        return primetest;
    }

    public void setPrimetest(PrimeTest primetest) {
        this.primetest = primetest;
    }

    public CryptobyClient getClient() {
        return client;
    }

    private void setClient(CryptobyClient client) {
        this.client = client;
    }

    public CryptobyUI getUi() {
        return ui;
    }

    public void setUi(CryptobyUI ui) {
        this.ui = ui;
    }

    public KeyGenSym getKeyGenSym() {
        return keyGenSym;
    }

    public void setKeyGenSym(KeyGenSym keyGenSym) {
        this.keyGenSym = keyGenSym;
    }

    public KeyGenAsym getKeyGenAsym() {
        return keyGenAsym;
    }

    public void setKeyGenAsym(KeyGenAsym keyGenAsym) {
        this.keyGenAsym = keyGenAsym;
    }

    public CryptSym getCryptSym() {
        return cryptSym;
    }

    public void setCryptSym(CryptSym cryptSym) {
        this.cryptSym = cryptSym;
    }

    public CryptAsym getCryptAsym() {
        return cryptAsym;
    }

    public void setCryptAsym(CryptAsym cryptAsym) {
        this.cryptAsym = cryptAsym;
    }

}
