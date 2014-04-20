/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.zhaw.cryptoby.keygen.itf;

/**
 *
 * @author Toby
 */
public interface KeyGenSym {
    
    public String generateKey(int keySize);
    public String generateKey(int keySize, String password);
    
}
