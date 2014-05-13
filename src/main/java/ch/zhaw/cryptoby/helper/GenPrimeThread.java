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
 *
 * @author Toby
 */
public class GenPrimeThread extends Thread {

    private final SecureRandom scRandom;
    private BigInteger prime;
    private final CryptobyCore core;
    private final int keyBitSize;
    private final int keyByteSize;

    public GenPrimeThread(CryptobyCore core, int keyBitSize) {
        scRandom = new SecureRandom();
        this.keyBitSize = keyBitSize/2;
        this.keyByteSize = this.keyBitSize / 8;
        this.core = core;
    }

    @Override
    public void run() {
        do {
            prime = new BigInteger(keyBitSize - 1, 1 , scRandom);
        } while (!(core.getPrimetest().isPrime(prime)) || prime.toByteArray().length != keyByteSize);
    }

    public BigInteger getPrime() {
        return prime;
    }

}
