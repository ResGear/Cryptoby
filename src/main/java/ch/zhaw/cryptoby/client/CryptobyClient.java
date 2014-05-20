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
 * This class start the Cryptoby application with initial parameters
 *
 * @author Tobias Rees
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

    /**
     * Constructor set default implementations of classes, which will be
     * initialed in the CryptobyCore class.
     */
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
     * Start CryptobyClient class and run User Interface in core object
     *
     * @param args no arguments yet
     */
    public static void main(String[] args) {
        CryptobyClient client = new CryptobyClient();
        client.getCore().getUi().run();
    }

    /**
     * Close application
     */
    public void exitApp() {
        System.exit(1);
    }

    /**
     *
     * @return Get Core object of application
     */
    public CryptobyCore getCore() {
        return core;
    }

    /**
     *
     * @param core Set a new Core object in application
     */
    public void setCore(CryptobyCore core) {
        this.core = core;
    }

    /**
     *
     * @return Get implementation of Primetest
     */
    public String getPrimTestArt() {
        return primTestArt;
    }

    /**
     *
     * @param primTestArt Set implementation of Primetest
     */
    public void setPrimTestArt(String primTestArt) {
        this.primTestArt = primTestArt;
    }

    /**
     *
     * @return Get rounds which used Primetest implementation
     */
    public int getPrimetestrounds() {
        return primetestrounds;
    }

    /**
     *
     * @param primetestrounds Set rounds for Primetest implementation if needed
     */
    public void setPrimetestrounds(int primetestrounds) {
        this.primetestrounds = primetestrounds;
    }

    /**
     *
     * @return Get implementation of User Interface
     */
    public String getUi() {
        return ui;
    }

    /**
     *
     * @param ui Set implementation of User Interface
     */
    public void setUi(String ui) {
        this.ui = ui;
    }

    /**
     *
     * @return Get implementation of key generator for a symmetric cryptologic
     * mode
     */
    public String getKeySymArt() {
        return keySymArt;
    }

    /**
     *
     * @param keySymArt Set implementation of key generator for a symmetric
     * cryptologic mode
     */
    public void setKeySymArt(String keySymArt) {
        this.keySymArt = keySymArt;
    }

    /**
     *
     * @return Get implementation of key generator for an asymmetric cryptologic
     * mode
     */
    public String getKeyAsymArt() {
        return keyAsymArt;
    }

    /**
     *
     * @param keyAsymArt Set implementation of key generator for an asymmetric
     * cryptologic mode
     */
    public void setKeyAsymArt(String keyAsymArt) {
        this.keyAsymArt = keyAsymArt;
    }

    /**
     *
     * @return Get implementation of a symmetric cryptologic mode
     */
    public String getCryptSymArt() {
        return cryptSymArt;
    }

    /**
     *
     * @param cryptSymArt Set implementation of a symmetric cryptologic mode
     */
    public void setCryptSymArt(String cryptSymArt) {
        this.cryptSymArt = cryptSymArt;
    }

    /**
     *
     * @return Get implementation of an asymmetric cryptologic mode
     */
    public String getCryptAsymArt() {
        return cryptAsymArt;
    }

    /**
     *
     * @param cryptAsymArt Set implementation of an asymmetric cryptologic mode
     */
    public void setCryptAsymArt(String cryptAsymArt) {
        this.cryptAsymArt = cryptAsymArt;
    }

}
