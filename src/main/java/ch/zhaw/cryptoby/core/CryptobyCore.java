/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.zhaw.cryptoby.core;

import ch.zhaw.cryptoby.client.CryptobyClient;
import ch.zhaw.cryptoby.prime.imp.MillerRabin;
import ch.zhaw.cryptoby.prime.itf.PrimeTest;

/**
 *
 * @author Toby
 */
public class CryptobyCore {
    
//    private DecSym decsym;
//    private EncSym encsym;
//    private DecAsym decasym;
//    private EncAsym encasym;
    private PrimeTest primetest;
    private CryptobyClient client;
    
    public CryptobyCore(CryptobyClient client){
        this.setClient(client);
        this.initPrimeTest();
        
    }
    
//    public CryptobyCore(DecSym decsym, EncSym encsym, DecAsym decasym, EncAsym encasym, PrimeTest primetest){
//        this.decsym = decsym;
//        this.encsym = encsym;
//        this.decasym = decasym;
//        this.encasym = encasym;
//        this.primetest = primetest;
//    }
    
       
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

    public void initPrimeTest() {
        if(this.client.getPrimetest().equals("MillerRabin")){
            this.setPrimetest(new MillerRabin(this.client.getPrimetestrounds()));
        } else {
            this.setPrimetest(new MillerRabin(this.client.getPrimetestrounds()));
        }
    }
    
}
