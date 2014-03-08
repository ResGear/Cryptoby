/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.zhaw.cryptoby.prime.itf;

import java.math.BigInteger;

/**
 *
 * @author Toby
 */
public interface PrimeTest {
    
    public boolean isPrime(BigInteger n);
    public boolean isPrime(BigInteger number, int rounds);
    public double  getProbability();
    
}
