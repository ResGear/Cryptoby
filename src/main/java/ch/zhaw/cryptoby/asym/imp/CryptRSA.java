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
