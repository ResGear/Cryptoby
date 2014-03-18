/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.zhaw.cryptoby.client;

import ch.zhaw.cryptoby.core.CryptobyCore;
import ch.zhaw.cryptoby.ui.imp.CryptobyConsole;
import ch.zhaw.cryptoby.ui.itf.CryptobyUI;

/**
 *
 * @author Toby
 */
public class CryptobyClient {
    
    private CryptobyCore core;
    private CryptobyUI ui;
    private String primetest;
    private int primetestrounds;
    
    public CryptobyClient(){
        this.primetest = "MillerRabin";
        this.primetestrounds = 5;
        this.core = new CryptobyCore(this);
        this.ui = new CryptobyConsole(this);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CryptobyClient client = new CryptobyClient();
        client.getUi().startUI();
        
        
    }
    
    public void stop(){
        System.exit(1);
    }

    public CryptobyCore getCore() {
        return core;
    }

    public void setCore(CryptobyCore core) {
        this.core = core;
    }

    public CryptobyUI getUi() {
        return ui;
    }

    public void setUi(CryptobyConsole ui) {
        this.ui = ui;
    }

    public String getPrimetest() {
        return primetest;
    }

    public void setPrimetest(String primetest) {
        this.primetest = primetest;
    }

    public int getPrimetestrounds() {
        return primetestrounds;
    }

    public void setPrimetestrounds(int primetestrounds) {
        this.primetestrounds = primetestrounds;
    }
    
    
    
    
}
