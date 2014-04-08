/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.zhaw.cryptoby.core;

import ch.zhaw.cryptoby.client.CryptobyClient;
import ch.zhaw.cryptoby.keygen.imp.KeyGenPrivSHA3;
import ch.zhaw.cryptoby.keygen.itf.KeyGenPriv;
import ch.zhaw.cryptoby.prime.imp.MillerRabin;
import ch.zhaw.cryptoby.prime.itf.PrimeTest;
import ch.zhaw.cryptoby.sym.imp.CryptAES;
import ch.zhaw.cryptoby.sym.itf.CryptSym;
import ch.zhaw.cryptoby.ui.imp.CryptobyConsole;
import ch.zhaw.cryptoby.ui.itf.CryptobyUI;

/**
 *
 * @author Toby
 */
public final class CryptobyCore {
    
    private CryptSym cryptSym;
    private KeyGenPriv keyGenPriv;
    private PrimeTest primetest;
    private CryptobyClient client;
    private CryptobyUI ui;
    
    public CryptobyCore(CryptobyClient client){
        this.setClient(client);    
    }
    
    public void initCryptSym() {
        if(this.client.getCryptSymArt().equals("AES")){
            this.setCryptSym(new CryptAES());
        } else {
            this.setCryptSym(new CryptAES());
        }
    }
    
    public void initKeyGen() {
        if(this.client.getKeyGenArt().equals("SHA3")){
            this.setKeyGenPriv(new KeyGenPrivSHA3());
        } else {
            this.setKeyGenPriv(new KeyGenPrivSHA3());
        }
    }

    public void initPrimeTest() {
        if(this.client.getPrimTestArt().equals("MillerRabin")){
            this.setPrimetest(new MillerRabin(this.client.getPrimetestrounds()));
        } else {
            this.setPrimetest(new MillerRabin(this.client.getPrimetestrounds()));
        }
    }
    
    public void initUI() {
        if(this.client.getUi().equals("console")){
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
    
    public KeyGenPriv getKeyGenPriv() {
        return keyGenPriv;
    }

    public void setKeyGenPriv(KeyGenPriv keyGenPriv) {
        this.keyGenPriv = keyGenPriv;
    }

    public CryptSym getCryptSym() {
        return cryptSym;
    }

    public void setCryptSym(CryptSym cryptSym) {
        this.cryptSym = cryptSym;
    }
    
}
