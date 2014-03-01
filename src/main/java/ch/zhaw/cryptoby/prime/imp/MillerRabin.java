/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.zhaw.cryptoby.prime.imp;

import ch.zhaw.cryptoby.prime.itf.PrimeTest;
import java.math.BigInteger;

/**
 * http://www4.in.tum.de/lehre/vorlesungen/perlen/SS08/Unterlagen/Miller-Rabin.pdf
 * @author Toby
 */
public class MillerRabin implements PrimeTest {
    
    BigInteger testNumber;
    
    public MillerRabin(BigInteger importNumber){
        if (importNumber.intValue() > 2) {
            this.testNumber = importNumber;
        } else {
            // Show Message
        }
    }

    @Override
    public int getProbability() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isPrime() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
