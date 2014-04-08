/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.zhaw.cryptoby.sym.imp;

import org.apache.commons.codec.DecoderException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.apache.commons.codec.binary.Hex;

/**
 *
 * @author Toby
 */
public class CryptAESTest {
    
    public CryptAESTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testEncryptDecrypt224() throws DecoderException {
        System.out.println("encrypt and decrypt testphrase");
        byte[] plainInput = "Text to Test for Testing from Tester by Testcase".getBytes();
        String hexKey = "218730F26D36BC8F7225046709D9D6FB73C08ECC698513C8A259DB34";
        System.out.println(hexKey);
        CryptAES instance = new CryptAES();
        byte[] expResult = plainInput;
        byte[] result = instance.encrypt(plainInput, hexKey.getBytes());
        System.out.println(new String(result));
        char[] resChar = Hex.encodeHex(result);
        System.out.println(String.copyValueOf(resChar));
        String resString = String.copyValueOf(resChar);
        resChar = resString.toCharArray();
        result = Hex.decodeHex(resChar);
        result = instance.decrypt(result, hexKey.getBytes());
        System.out.println(new String(result));
        assertArrayEquals(expResult, result);
        assertEquals(new String(expResult), new String(result));
    }
    
    @Test
    public void testEncryptDecrypt256() throws DecoderException {
        System.out.println("encrypt and decrypt testphrase");
        byte[] plainInput = "Text to Test for Testing from Tester by Testcase".getBytes();
        String hexKey = "2F38CDDB64D26063D576C7CB45D0BF67881D90AA4F6E813E42110A77CF54C0F0";
        System.out.println(hexKey);
        CryptAES instance = new CryptAES();
        byte[] expResult = plainInput;
        byte[] result = instance.encrypt(plainInput, hexKey.getBytes());
        System.out.println(new String(result));
        char[] resChar = Hex.encodeHex(result);
        System.out.println(String.copyValueOf(resChar));
        String resString = String.copyValueOf(resChar);
        resChar = resString.toCharArray();
        result = Hex.decodeHex(resChar);
        result = instance.decrypt(result, hexKey.getBytes());
        System.out.println(new String(result));
        assertArrayEquals(expResult, result);
        assertEquals(new String(expResult), new String(result));
    }
    
    
    @Test
    public void testEncryptDecrypt384() throws DecoderException {
        System.out.println("encrypt and decrypt testphrase");
        byte[] plainInput = "Text to Test for Testing from Tester by Testcase".getBytes();
        String hexKey = "49C3A39404E40991539089F4CA6C2B57939F6013780BAEC7BFBAEF4E7E0806421E6F4CC261E01C521202665ECC367A85";
        System.out.println(hexKey);
        CryptAES instance = new CryptAES();
        byte[] expResult = plainInput;
        byte[] result = instance.encrypt(plainInput, hexKey.getBytes());
        System.out.println(new String(result));
        char[] resChar = Hex.encodeHex(result);
        System.out.println(String.copyValueOf(resChar));
        String resString = String.copyValueOf(resChar);
        resChar = resString.toCharArray();
        result = Hex.decodeHex(resChar);
        result = instance.decrypt(result, hexKey.getBytes());
        System.out.println(new String(result));
        assertArrayEquals(expResult, result);
        assertEquals(new String(expResult), new String(result));
    }
    
    @Test
    public void testEncryptDecrypt512() throws DecoderException {
        System.out.println("encrypt and decrypt testphrase");
        byte[] plainInput = "Text to Test for Testing from Tester by Testcase".getBytes();
        String hexKey = "ACCF63DAE697F256110304A2F243B421AFD7275A0983748EEFEA1A0F4B040C4198C75FF0603BB0541C4737EAFDC32EE24864DDEEC0B2226A4B6AC6025DB4B672";
        System.out.println(hexKey);
        CryptAES instance = new CryptAES();
        byte[] expResult = plainInput;
        byte[] result = instance.encrypt(plainInput, hexKey.getBytes());
        System.out.println(new String(result));
        char[] resChar = Hex.encodeHex(result);
        System.out.println(String.copyValueOf(resChar));
        String resString = String.copyValueOf(resChar);
        resChar = resString.toCharArray();
        result = Hex.decodeHex(resChar);
        result = instance.decrypt(result, hexKey.getBytes());
        System.out.println(new String(result));
        assertArrayEquals(expResult, result);
        assertEquals(new String(expResult), new String(result));
    }
    
}
