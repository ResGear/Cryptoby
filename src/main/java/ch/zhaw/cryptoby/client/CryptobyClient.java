/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.zhaw.cryptoby.client;

import ch.zhaw.cryptoby.core.CryptobyCore;


/**
 *
 * @author Toby
 */
public final class CryptobyClient {
    
    private CryptobyCore core;
    private String primTestArt;
    private int primetestrounds;
    private String keyGenArt;
    private String cryptSymArt;
    private String ui;
    
    public CryptobyClient() {
        this.setPrimTestArt("MillerRabin");
        this.setPrimetestrounds(5);
        this.setUi("console");
        this.core = new CryptobyCore(this);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CryptobyClient client = new CryptobyClient();
        client.getCore().initPrimeTest();
        client.getCore().initUI();
        client.getCore().getUi().run();
    }
    
    public void exitApp(){
        System.exit(1);
    }

    public CryptobyCore getCore() {
        return core;
    }

    public void setCore(CryptobyCore core) {
        this.core = core;
    }

    public String getPrimTestArt() {
        return primTestArt;
    }

    public void setPrimTestArt(String primTestArt) {
        this.primTestArt = primTestArt;
    }

    public int getPrimetestrounds() {
        return primetestrounds;
    }

    public void setPrimetestrounds(int primetestrounds) {
        this.primetestrounds = primetestrounds;
    }

    public String getUi() {
        return ui;
    }

    public void setUi(String ui) {
        this.ui = ui;
    }
    
    public String getKeyGenArt() {
        return keyGenArt;
    }

    public void setKeyGenArt(String keyGenArt) {
        this.keyGenArt = keyGenArt;
    }

    public String getCryptSymArt() {
        return cryptSymArt;
    }

    public void setCryptSymArt(String cryptSymArt) {
        this.cryptSymArt = cryptSymArt;
    }

}
