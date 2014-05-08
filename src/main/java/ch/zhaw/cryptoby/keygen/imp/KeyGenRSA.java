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

package ch.zhaw.cryptoby.keygen.imp;

import ch.zhaw.cryptoby.core.CryptobyCore;
import ch.zhaw.cryptoby.helper.CryptobyHelper;
import ch.zhaw.cryptoby.helper.GenPrimeThread;
import ch.zhaw.cryptoby.keygen.itf.KeyGenAsym;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Toby
 */
public class KeyGenRSA implements KeyGenAsym {

    private final CryptobyCore core;
    private BigInteger p;
    private BigInteger q;
    private BigInteger n;
    private BigInteger phi;
    private final static BigInteger E = BigInteger.valueOf(65537);
    private BigInteger d;
    private double log2ofPQ;
    private String privKey;
    private String pubKey;
    private byte[] privKeyByte;
    private byte[] pubKeyByte;
    private int keyByteSize;
    private final int cores;

    public KeyGenRSA(CryptobyCore core) {
        this.core = core;
        this.cores = Runtime.getRuntime().availableProcessors();
    }

    @Override
    public void initGenerator(int keyBitSize) {
        if (keyBitSize != 1024 && keyBitSize != 2048 && keyBitSize != 4096) {
            throw new IllegalArgumentException("Just Keys with Size of 1024,2048 or 4096 are allowed!");
        }

        generateKeys(keyBitSize);

        // Generate Public Key to Hex String
        pubKeyByte = n.toByteArray();
        pubKey = CryptobyHelper.bytesToHexString(pubKeyByte);

        byte[] dByte = d.toByteArray();

        privKeyByte = new byte[dByte.length + pubKeyByte.length];
        
        // Copy D ByteArray into first Part and N ByteArray into second Part
        System.arraycopy(dByte, 0, privKeyByte, 0, dByte.length);
        System.arraycopy(pubKeyByte, 0, privKeyByte, dByte.length, pubKeyByte.length);
        
        // Generate Private Key to Hex String
        privKey = CryptobyHelper.bytesToHexString(privKeyByte);
    }

    @Override
    public String getPrivateKey() {
        try {
            return privKey;
        } catch (NullPointerException exp) {
            throw new IllegalArgumentException("KeyGenerator is not initialised! First use initGenerator!");
        }
    }

    @Override
    public String getPublicKey() {
        try {
            return pubKey;
        } catch (NullPointerException exp) {
            throw new IllegalArgumentException("KeyGenerator is not initialised! First use initGenerator!");
        }
    }

    @Override
    public byte[] getPrivateKeyByte() {
        try {
            return privKeyByte;
        } catch (NullPointerException exp) {
            throw new IllegalArgumentException("KeyGenerator is not initialised! First use initGenerator!");
        }
    }

    @Override
    public byte[] getPublicKeyByte() {
        try {
            return pubKeyByte;
        } catch (NullPointerException exp) {
            throw new IllegalArgumentException("KeyGenerator is not initialised! First use initGenerator!");
        }
    }

    private void generateKeys(int keyBitSize) {
        keyByteSize = keyBitSize / 8;
        // Generate Primes for Q and P
        do {
            // Use Cores parallel, if there are more than 1
            if (cores > 1) {
                do {
                    BigInteger[] primes = getPrimesParallel(keyBitSize);
                    p = primes[0];
                    q = primes[1];
                } while (p.compareTo(q) == 0);
            } else {
                do {
                    p = getPrimesParallel(keyBitSize)[0];
                    q = getPrimesParallel(keyBitSize)[0];
                    log2ofPQ = Math.abs(CryptobyHelper.logBigInteger(p) - CryptobyHelper.logBigInteger(q));
                } while (log2ofPQ <= 0.1 || log2ofPQ >= 30);
            }

            // Calculate n Module
            calcN();
            // Calculate Phi Module
            calcPhi();
            // Calculate D Module
            calcD();
        } while (n.toByteArray().length != (keyByteSize * 2)
                || d.toByteArray().length != (keyByteSize * 2));
    }

    private void calcN() {
        n = p.multiply(q);
    }

    private void calcPhi() {
        // Calc phi of n
        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
    }

    private void calcD() {
        d = E.modInverse(phi);
    }

    private BigInteger[] getPrimesParallel(int keyBitSize) {

        GenPrimeThread[] primeThreads = new GenPrimeThread[cores];
        BigInteger[] primes = new BigInteger[cores];
        BigInteger[] retPrime = new BigInteger[2];

        for (int i = 0; i < cores; i++) {
            primeThreads[i] = new GenPrimeThread(core, keyBitSize);
        }

        // Start Threads
        for (int i = 0; i < cores; i++) {
            primeThreads[i].start();
        }
        
        for (int i = 0; i < cores; i++) {
            try {
                primeThreads[i].join();

            } catch (InterruptedException ex) {
                Logger.getLogger(KeyGenRSA.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }

        for (int i = 0; i < cores; i++) {
            primes[i] = primeThreads[i].getPrime();
        }

        for (int i = 0; i < cores; i++) {
            for (int j = cores - 1; j >= 0; j--) {
                log2ofPQ = Math.abs(CryptobyHelper.logBigInteger(primes[i])
                        - CryptobyHelper.logBigInteger(primes[j]));
                if (log2ofPQ >= 0.1 || log2ofPQ <= 30) {
                    retPrime[0] = primes[i];
                    retPrime[1] = primes[j];
                    return retPrime;
                }
            }
        }

        retPrime[0] = BigInteger.ZERO;
        retPrime[1] = BigInteger.ZERO;
        return retPrime;
    }

}
