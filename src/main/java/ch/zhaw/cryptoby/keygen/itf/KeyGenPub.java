/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.zhaw.cryptoby.keygen.itf;

/**
 *
 * @author Toby
 */
public interface KeyGenPub {
    
    public String genPrivateKey(int keyLength);
    public String genpublicKey(int keyLength);
}
