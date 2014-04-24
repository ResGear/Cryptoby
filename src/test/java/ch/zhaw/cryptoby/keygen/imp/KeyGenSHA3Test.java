
package ch.zhaw.cryptoby.keygen.imp;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Toby
 */
public class KeyGenSHA3Test {
    
    public KeyGenSHA3Test() {
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

    /**
     * Test of generateKey method, of class KeyGenSHA3.
     */
    @Test
    public void testGenerateKey_int_random_length224() {
        System.out.println("generate random 224bit Key");
        int keySize = 224;
        KeyGenSHA3 instance = new KeyGenSHA3();
        int expResult = 224;
        int result = instance.generateKey(keySize).getBytes().length*4;
        System.out.println(result);
        assertEquals(expResult, result);
    }
    
     /**
     * Test of generateKey method, of class KeyGenSHA3.
     */
    @Test
    public void testGenerateKey_int_random_length256() {
        System.out.println("generate random 256bit Key");
        int keySize = 256;
        KeyGenSHA3 instance = new KeyGenSHA3();
        int expResult = 256;
        int result = instance.generateKey(keySize).getBytes().length*4;
        System.out.println(result);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of generateKey method, of class KeyGenSHA3.
     */
    @Test
    public void testGenerateKey_int_random_length384() {
        System.out.println("generate random 384bit Key");
        int keySize = 384;
        KeyGenSHA3 instance = new KeyGenSHA3();
        int expResult = 384;
        int result = instance.generateKey(keySize).getBytes().length*4;
        System.out.println(result);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of generateKey method, of class KeyGenSHA3.
     */
    @Test
    public void testGenerateKey_int_random_length512() {
        System.out.println("generate random 512bit Key");
        int keySize = 512;
        KeyGenSHA3 instance = new KeyGenSHA3();
        int expResult = 512;
        int result = instance.generateKey(keySize).getBytes().length*4;
        System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of generateKey method, of class KeyGenSHA3.
     */
    @Test
    public void testGenerateKey_int_String() {
        System.out.println("generate 256bit Key and comparse with given Key");
        int keySize = 256;
        String password = "testest";
        KeyGenSHA3 instance = new KeyGenSHA3();
        String expResult = "e195622d04525e14469076f4175b990a72995ea7c9f379c465670c330b4f8b60";
        String result = instance.generateKey(keySize, password);
        System.out.println(result);
        assertEquals(expResult, result);
    }
    
       /**
     * Test of generateKey method, of class KeyGenSHA3.
     */
    @Test
    public void testGenerateKey_int_String_false() {
        System.out.println("generate 256bit false Key and comparse with given Key");
        int keySize = 256;
        String password = "falsePW";
        KeyGenSHA3 instance = new KeyGenSHA3();
        String expResult = "e195622d04525e14469076f4175b990a72995ea7c9f379c465670c330b4f8b60";
        String result = instance.generateKey(keySize, password);
        System.out.println(result);
        assertFalse(expResult.equals(result));
    }
    
}