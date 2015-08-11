package encrypts;

import static org.junit.Assert.assertArrayEquals;

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
	int kb10;
	int kb50;
	int kb100;
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
		kb10 = 1024 * 10;
		kb50 = kb10 * 5;
		kb100 = kb10 * 10;
        client = new CryptobyClient();
        core = new CryptobyCore(client);
        generator = new KeyGenRSA(core);
        rsa = new CryptRSA();
	}

	public void testRSAEnc() {
		modData = rsa.encrypt(preData, pubKey);
	}
	
	public void testRSADec() {
		resData = rsa.decrypt(modData, privKey);
	}


	@Test
	public void testRun(){
		int rounds = 50;
		
		System.out.println("Test Performance RSA Enc/Dec");
		System.out.println("Rounds: "+rounds);
		System.out.println("Cycle per Round: "+cycles);
		
		generator.initGenerator(keySize);
		pubKey = generator.getPublicKeyByte();
		privKey = generator.getPrivateKeyByte();
		
		preData = new byte[kb10];
		System.out.println ("\nEncrypt RSA 1024 Bit 10KB");
		PerfMeter.run(this, rounds, "testRSAEnc");
		System.out.println ("\nDecrypt RSA 1024 Bit 10KB");
		PerfMeter.run(this, rounds, "testRSADec");
		assertArrayEquals(preData, resData);
		
		modData = null;
		resData = null;
		preData = new byte[kb50];
		System.out.println ("\nEncrypt RSA 1024 Bit 50KB");
		PerfMeter.run(this, rounds, "testRSAEnc");
		System.out.println ("\nDecrypt RSA 1024 Bit 50KB");
		PerfMeter.run(this, rounds, "testRSADec");
		assertArrayEquals(preData, resData);
		
		modData = null;
		resData = null;
		preData = new byte[kb100];
		System.out.println ("\nEncrypt RSA 1024 Bit 100KB");
		PerfMeter.run(this, rounds, "testRSAEnc");
		System.out.println ("\nDecrypt RSA 1024 Bit 100KB");
		PerfMeter.run(this, rounds, "testRSADec");
		assertArrayEquals(preData, resData);
	}
	
	

}
