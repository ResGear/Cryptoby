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
package asym.imp;

import asym.imp.CryptRSA;
import client.CryptobyClient;
import core.CryptobyCore;
import helper.CryptobyHelper;
import keygen.imp.KeyGenRSA;
import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * 
 * @author Tobias Rees
 */
public class CryptRSATest {

    /**
     * Test one Block with 1024Bit Key
     */
    @Test
    public void testRSACrypt1024_oneBlock() {
        System.out.println("RSACrypt1024oneBlock");
        int keySize = 1024;
        CryptobyClient client = new CryptobyClient();
        CryptobyCore core = new CryptobyCore(client);
        KeyGenRSA generator = new KeyGenRSA(core);
        generator.initGenerator(keySize);
        String smallString = "Text to Test for Testing from Tester by Testcase."
                + "Text to Test for Testing from Tester by Testcase.Text to Test";
        byte[] plainInput = smallString.getBytes();
        byte[] publicKey = generator.getPublicKeyByte();
        byte[] privateKey = generator.getPrivateKeyByte();
        CryptRSA rsa = new CryptRSA();
        byte[] expResult = plainInput;
        byte[] result = rsa.encrypt(plainInput, publicKey);
        result = rsa.decrypt(result, privateKey);
        assertArrayEquals(expResult, result);
    }

    /**
     * Test longer String with 1024Bit Key
     */
    @Test
    public void testRSACrypt1024_longString() {
        System.out.println("RSACrypt1024longString");
        int keySize = 1024;
        CryptobyClient client = new CryptobyClient();
        CryptobyCore core = new CryptobyCore(client);
        KeyGenRSA generator = new KeyGenRSA(core);
        generator.initGenerator(keySize);
        String longString = "Warmly little before cousin sussex entire men set. "
                + "Blessing it ladyship on sensible judgment settling outweigh. "
                + "Worse linen an of civil jokes leave offer. Parties all clothes"
                + " removal cheered calling prudent her. And residence for met "
                + "the estimable disposing. Mean if he they been no hold mr. Is "
                + "at much do made took held help. Latter person am secure of "
                + "estate genius at.Six started far placing saw respect females "
                + "old. Civilly why how end viewing attempt related enquire visitor."
                + " Man particular insensible celebrated conviction stimulated "
                + "principles day. Sure fail or in said west. Right my front it "
                + "wound cause fully am sorry if. She jointure goodness interest "
                + "debating did outweigh. Is time from them full my gone in went."
                + " Of no introduced am literature excellence mr stimulated "
                + "contrasted increasing. Age sold some full like rich new. "
                + "Amounted repeated as believed in confined juvenile.Started his"
                + " hearted any civilly. So me by marianne admitted speaking. "
                + "Men bred fine call ask. Cease one miles truth day above seven. "
                + "Suspicion sportsmen provision suffering mrs saw engrossed something. "
                + "Snug soon he on plan in be dine some.";
        byte[] plainInput = longString.getBytes();
        byte[] publicKey = generator.getPublicKeyByte();
        byte[] privateKey = generator.getPrivateKeyByte();
        CryptRSA rsa = new CryptRSA();
        byte[] expResult = plainInput;
        byte[] enc = rsa.encrypt(plainInput, publicKey);
        byte[] result = rsa.decrypt(enc, privateKey);
        assertArrayEquals(expResult, result);
    }

    /**
     * Test big Block (10000 Bytes) with 1024Bit Key
     */
    @Test
    public void testRSACrypt1024_BiggerBlock() {
        System.out.println("RSACrypt1024Bigger");
        int keySize = 1024;
        CryptobyClient client = new CryptobyClient();
        CryptobyCore core = new CryptobyCore(client);
        KeyGenRSA generator = new KeyGenRSA(core);
		Random rand = new Random();
		for(int i = 129;i<130;i++){
	        generator.initGenerator(keySize);
	        byte[] expResult = new byte[i];
	        byte[] publicKey = generator.getPublicKeyByte();
	        byte[] privateKey = generator.getPrivateKeyByte();
	        CryptRSA rsa = new CryptRSA();
	        rand.nextBytes(expResult);
	        byte[] result = rsa.encrypt(expResult, publicKey);
	        result = rsa.decrypt(result, privateKey);
	        assertArrayEquals(expResult, result);
		}
    }

    /**
     * Test small String with false 1024Bit Key
     */
    @Test
    public void testRSACrypt1024false() {
        System.out.println("RSACrypt1024false");
        int keySize = 1024;
        CryptobyClient client = new CryptobyClient();
        CryptobyCore core = new CryptobyCore(client);
        KeyGenRSA generator = new KeyGenRSA(core);
        generator.initGenerator(keySize);
        byte[] plainInput = "Text to Test for Testing from Tester by Testcase".getBytes();
        String publicKeyString = generator.getPublicKey();
        byte[] publicKey = CryptobyHelper.hexStringToBytes(publicKeyString);
        generator.initGenerator(keySize);
        String privateKeyString = generator.getPrivateKey();
        byte[] privateKey = CryptobyHelper.hexStringToBytes(privateKeyString);
        CryptRSA rsa = new CryptRSA();
        byte[] expResult = plainInput;
        byte[] result = rsa.encrypt(plainInput, publicKey);
        result = rsa.decrypt(result, privateKey);

        assertFalse(Arrays.equals(expResult, result));
    }

    /**
     * Test small String with 2048Bit Key
     */
    @Test
    public void testRSACrypt2048() {
        System.out.println("RSACrypt2048");
        int keySize = 2048;
        CryptobyClient client = new CryptobyClient();
        CryptobyCore core = new CryptobyCore(client);
        KeyGenRSA generator = new KeyGenRSA(core);
        generator.initGenerator(keySize);
        byte[] plainInput = "Text to Test for Testing from Tester by Testcase".getBytes();
        String publicKeyString = generator.getPublicKey();
        byte[] publicKey = CryptobyHelper.hexStringToBytes(publicKeyString);
        String privateKeyString = generator.getPrivateKey();
        byte[] privateKey = CryptobyHelper.hexStringToBytes(privateKeyString);
        CryptRSA rsa = new CryptRSA();
        byte[] expResult = plainInput;
        byte[] result = rsa.encrypt(plainInput, publicKey);
        result = rsa.decrypt(result, privateKey);
        assertArrayEquals(expResult, result);
    }

    /**
     * Test small String with 4096Bit Key
     */
    @Test
    public void testRSACrypt4096() {
        System.out.println("RSACrypt4096");
        int keySize = 4096;
        CryptobyClient client = new CryptobyClient();
        CryptobyCore core = new CryptobyCore(client);
        KeyGenRSA generator = new KeyGenRSA(core);
        generator.initGenerator(keySize);
        byte[] plainInput = "Text to Test for Testing from Tester by Testcase".getBytes();
        String publicKeyString = generator.getPublicKey();
        byte[] publicKey = CryptobyHelper.hexStringToBytes(publicKeyString);
        String privateKeyString = generator.getPrivateKey();
        byte[] privateKey = CryptobyHelper.hexStringToBytes(privateKeyString);
        CryptRSA rsa = new CryptRSA();
        byte[] expResult = plainInput;
        byte[] result = rsa.encrypt(plainInput, publicKey);
        result = rsa.decrypt(result, privateKey);
        assertArrayEquals(expResult, result);
    }
}
