package encrypts;

import static org.junit.Assert.assertArrayEquals;

import java.math.BigInteger;
import org.junit.*;

import asym.imp.CryptRSA;
import client.CryptobyClient;
import core.CryptobyCore;
import keygen.imp.KeyGenRSA;
import tester.PerfMeter;

public class TestRSA1024 {

	int cycles = 1;
	byte[] preData;
	byte[] modData;
	byte[] resData;
	int kb100;
	int kb500;
	int mb1;
	int keySize = 1024;
	byte[] pubKey;
	byte[] privKey;
	
	CryptobyClient client;
	CryptobyCore core;
	KeyGenRSA generator;
	CryptRSA rsa;
	

	@Before
	public void setUp()
	{ 
		kb100 = 1024 * 100;
		kb500 = kb100 * 5;
		mb1 = kb100 * 10;
        client = new CryptobyClient();
        core = new CryptobyCore(client);
        generator = new KeyGenRSA(core);
        rsa = new CryptRSA();
	}

	public void testRSA1024Enc() {
		modData = rsa.encrypt(preData, pubKey);
	}
	
	public void testRSA1024Dec() {
		resData = rsa.decrypt(modData, privKey);
	}


	@Test
	public void testRun(){
		int rounds = 1;
		
		System.out.println("Test Performance RSA Encryption/Decryption");
		System.out.println("Rounds: "+rounds);
		System.out.println("Cycle per Round: "+cycles);
		
		generator.initGenerator(keySize);
		pubKey = generator.getPublicKeyByte();
		privKey = generator.getPrivateKeyByte();
		
		preData = new byte[kb100];
		System.out.println ("\nEncrypt RSA 1024 Bit 100KB");
		PerfMeter.run(this, rounds, "testRSA1024Enc");
		System.out.println ("\nDecrypt RSA 1024 Bit 100KB");
		PerfMeter.run(this, rounds, "testRSA1024Dec");
		assertArrayEquals(preData, resData);
		
		modData = null;
		resData = null;
		preData = new byte[kb500];
		System.out.println ("\nEncrypt RSA 1024 Bit 500KB");
		PerfMeter.run(this, rounds, "testRSA1024Enc");
		System.out.println ("\nDecrypt RSA 1024 Bit 500KB");
		PerfMeter.run(this, rounds, "testRSA1024Dec");
		assertArrayEquals(preData, resData);
		
		modData = null;
		resData = null;
		preData = new byte[mb1];
		System.out.println ("\nEncrypt RSA 1024 Bit 1MB");
		PerfMeter.run(this, rounds, "testRSA1024Enc");
		System.out.println ("\nDecrypt RSA 1024 Bit 1MB");
		PerfMeter.run(this, rounds, "testRSA1024Dec");
		assertArrayEquals(preData, resData);
	}

}
