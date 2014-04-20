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
public interface KeyGenAsym {
    
    public void initGenerator(int keyBitSize);
    
    public String getPrivateKey();
    public String getPublicKey();
    
    public byte[] getPrivateKeyByte();
    public byte[] getPublicKeyByte();
}
