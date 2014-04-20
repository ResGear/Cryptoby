/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.zhaw.cryptoby.asym.imp;

import ch.zhaw.cryptoby.asym.itf.CryptAsym;
import java.math.BigInteger;

/**
 *
 * @author Toby
 */
public class CryptRSA implements CryptAsym {

    private final static BigInteger E = BigInteger.valueOf(65537);
    
    @Override
    public byte[] encrypt(byte[] plainInput, byte[] publicKey) {
        BigInteger n = new BigInteger(publicKey);
        return (new BigInteger(plainInput)).modPow(E, n).toByteArray(); 
    }
    
    @Override
    public byte[] decrypt(byte[] cryptInput, byte[] privateKey) {
        byte[] dByteArray = getDfromKey(privateKey);
        byte[] nByteArray = getNfromKey(privateKey);
        
        BigInteger d = new BigInteger(dByteArray);
        BigInteger n = new BigInteger(nByteArray);      
        
        return (new BigInteger(cryptInput)).modPow(d, n).toByteArray(); 
    }
    
    private byte[] getDfromKey(byte[] privateKey) {
        // Get D from the first Part of the PrivateKey
        int midOfKey = privateKey.length/2;
        byte[] dByteArray = new byte[midOfKey];
        System.arraycopy(privateKey, 0, dByteArray, 0, midOfKey);
        return dByteArray;
    }
    
    private byte[] getNfromKey(byte[] privateKey) {
        // Get D from the first Part of the PrivateKey
        int midOfKey = privateKey.length/2;
        byte[] nByteArray = new byte[midOfKey];
        System.arraycopy(privateKey, midOfKey, nByteArray,0 , midOfKey);
        return nByteArray;
    }    
    
}
