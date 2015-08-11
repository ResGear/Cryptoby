package keygen;

import static org.junit.Assert.*;
import org.junit.*;

import client.CryptobyClient;
import core.CryptobyCore;
import keygen.imp.KeyGenRSA;
import tester.PerfMeter;

public class TestRSAKeyGen {

	int cycles = 1;
	int keySize;
	byte[] privKey;
	byte[] pubKey;
	KeyGenRSA keyGen;
	

	@Before
	public void setUp()
	{ 
        CryptobyClient client = new CryptobyClient();
        CryptobyCore core = new CryptobyCore(client);
		keyGen = new KeyGenRSA(core);
	}

	public void testGenRSAKey() {
		keyGen.initGenerator(keySize);
	}


	@Test
	public void testRun(){
		int rounds = 20;
		
		System.out.println("Test Performance RSA Key Generation");
		System.out.println("Rounds: "+rounds);
		System.out.println("Cycle per Round: "+cycles);

		keySize = 1024;
		System.out.println ("\nGenerate RSA Key 1024 Bit");
		PerfMeter.run(this, rounds, "testGenRSAKey");		
		privKey = keyGen.getPrivateKeyByte();
		pubKey = keyGen.getPublicKeyByte();
		assertEquals(privKey.length*4,keySize );
		assertEquals(pubKey.length*8,keySize );
		
		keySize = 2048;
		System.out.println ("\nGenerate RSA Key 2048 Bit");
		PerfMeter.run(this, rounds, "testGenRSAKey");		
		privKey = keyGen.getPrivateKeyByte();
		pubKey = keyGen.getPublicKeyByte();
		assertEquals(privKey.length*4,keySize );
		assertEquals(pubKey.length*8,keySize );
		
		keySize = 4096;
		System.out.println ("\nGenerate RSA Key 4096 Bit");
		PerfMeter.run(this, rounds, "testGenRSAKey");		
		privKey = keyGen.getPrivateKeyByte();
		pubKey = keyGen.getPublicKeyByte();
		assertEquals(privKey.length*4,keySize );
		assertEquals(pubKey.length*8,keySize );

	}	

}