/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.zhaw.cryptoby.asym.itf;

/**
 *
 * @author Toby
 */
public interface CryptAsym {
    
    public byte[] encrypt(byte[] plainInput, byte[] publicKey);
    public byte[] decrypt(byte[] cryptInput, byte[] privateKey);
    
}
