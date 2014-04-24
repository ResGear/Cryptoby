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
    private String keySymArt;
    private String keyAsymArt;
    private String cryptSymArt;
    private String cryptAsymArt;
    private String ui;
    
    public CryptobyClient() {
        this.setPrimTestArt("MillerRabin");
        this.setPrimetestrounds(5);
        this.setUi("console");
        this.setCryptSymArt("AES");
        this.setCryptAsymArt("RSA");
        this.setKeySymArt("SHA3");
        this.setKeyAsymArt("RSA");
        this.core = new CryptobyCore(this);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CryptobyClient client = new CryptobyClient();
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
    
    public String getKeySymArt() {
        return keySymArt;
    }

    public void setKeySymArt(String keySymArt) {
        this.keySymArt = keySymArt;
    }

    public String getKeyAsymArt() {
        return keyAsymArt;
    }

    public void setKeyAsymArt(String keyAsymArt) {
        this.keyAsymArt = keyAsymArt;
    }

    public String getCryptSymArt() {
        return cryptSymArt;
    }

    public void setCryptSymArt(String cryptSymArt) {
        this.cryptSymArt = cryptSymArt;
    }

    public String getCryptAsymArt() {
        return cryptAsymArt;
    }

    public void setCryptAsymArt(String cryptAsymArt) {
        this.cryptAsymArt = cryptAsymArt;
    }

}
