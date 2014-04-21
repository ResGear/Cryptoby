/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.zhaw.cryptoby.keygen.imp;

import ch.zhaw.cryptoby.core.CryptobyCore;
import ch.zhaw.cryptoby.core.CryptobyHelper;
import ch.zhaw.cryptoby.keygen.itf.KeyGenAsym;
import java.math.BigInteger;
import java.security.SecureRandom;

/**
 *
 * @author Toby
 */
public class KeyGenRSA implements KeyGenAsym {

    private final SecureRandom scRandom;
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

    public KeyGenRSA(CryptobyCore core) {
        scRandom = new SecureRandom();
        this.core = core;

        this.core.getClient().setPrimetestrounds(5);
        this.core.getClient().setPrimTestArt("MillerRabin");
        this.core.initPrimeTest();
    }

    @Override
    public void initGenerator(int keyBitSize) {
        int keyByteSize = keyBitSize / 8;
        if (keyByteSize != 128 && keyByteSize != 256 && keyByteSize != 512) {
            throw new IllegalArgumentException("Just Keys with Size of 1024,2048 or 4096 are allowed!");
        }

        generateKeys(keyByteSize);
        
        // Generate Public Key to alphanumeric String
        pubKeyByte = n.toByteArray();
        pubKey = new BigInteger(pubKeyByte).toString(Character.MAX_RADIX);

        byte[] dByte = d.toByteArray();
        
        privKeyByte = new byte[dByte.length + pubKeyByte.length];
        // Copy D ByteArray into first Part and N ByteArray into second Part
        System.arraycopy(dByte, 0, privKeyByte, 0, dByte.length);
        System.arraycopy(pubKeyByte, 0, privKeyByte, dByte.length, pubKeyByte.length);
        // Generate Private Key to alphanumeric String
        privKey = new BigInteger(privKeyByte).toString(Character.MAX_RADIX);
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

    private void generateKeys(int keyByteSize) {
        // Generate Primes for Q and P
        do {
            do {
                q = new BigInteger(1, scRandom.generateSeed(keyByteSize)).nextProbablePrime();
            } while (!(core.getPrimetest().isPrime(q)) || q.toByteArray().length != keyByteSize);
            do {
                do {
                    p = new BigInteger(1, scRandom.generateSeed(keyByteSize));
                } while (p.compareTo(q) < 1);
                p = p.nextProbablePrime();
            } while (!(core.getPrimetest().isPrime(p)) || p.toByteArray().length != keyByteSize);

            log2ofPQ = CryptobyHelper.logBigInteger(p) - CryptobyHelper.logBigInteger(q);
            // Calculate n Module
            calcN();
            // Calculate Phi Module
            calcPhi();
            // Calculate D Module
            calcD();
        } while (p.compareTo(q) == 0
                || 0.1 >= log2ofPQ
                || 30 <= log2ofPQ
                || n.toByteArray().length != (keyByteSize * 2)
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

}
