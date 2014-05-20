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
package ch.zhaw.cryptoby.helper;

import ch.zhaw.cryptoby.core.CryptobyCore;
import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * This class provide an implementation of the class Thread to generate big
 * prime numbers in parallel mode.
 *
 * @author Tobias Rees
 */
public class GenPrimeThread extends Thread {

    private final SecureRandom scRandom;
    private BigInteger prime;
    private final CryptobyCore core;
    private final int halfKeyBitSize;
    private final int keyByteSize;

    /**
     * Constructor sets variables and initializes the SecureRandom object.
     *
     * @param appCore Input CryptobyCore object to get a primetest object
     * @param keyBitSize With the half size of this key will generate be a prime
     * number in the run method
     */
    public GenPrimeThread(CryptobyCore appCore, int keyBitSize) {
        scRandom = new SecureRandom();
        halfKeyBitSize = keyBitSize / 2;
        keyByteSize = halfKeyBitSize / 8;
        core = appCore;
    }

    /**
     * Generate a prime number with half size of keyBitSize
     */
    @Override
    public void run() {
        do {
            prime = new BigInteger(halfKeyBitSize - 1, 1, scRandom);
        } while (!(core.getPrimetest().isPrime(prime)) || prime.toByteArray().length != keyByteSize);
    }

    /**
     * Get generated BigInteger prime number
     *
     * @return Return generated prime number as BigInteger
     */
    public BigInteger getPrime() {
        return prime;
    }

}
