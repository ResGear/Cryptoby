package keygen;

import static org.junit.Assert.*;
import org.junit.*;

import client.CryptobyClient;
import core.CryptobyCore;
import keygen.imp.KeyGenRSA;
import keygen.imp.KeyGenSHA3;
import tester.PerfMeter;

public class TestSHA3KeyGen {

	int cycles = 10000;
	int keySize;
	byte[] privKey;
	KeyGenSHA3 keyGen;
	

	@Before
	public void setUp()
	{ 
		keyGen = new KeyGenSHA3();
	}

	public void testGenSHA3Key() {
		for(int i = 0; i < cycles; i++){
			privKey = keyGen.generateKeyByte(keySize,"password");
		}
	}


	@Test
	public void testRun(){
		int rounds = 20;
		
		System.out.println("Test Performance SHA3 Key Generation");
		System.out.println("Rounds: "+rounds);
		System.out.println("Cycle per Round: "+cycles);

		keySize = 224;
		System.out.println ("\nGenerate SHA3 Key 224 Bit");
		PerfMeter.run(this, rounds, "testGenSHA3Key");		
		assertEquals(privKey.length*8,keySize );
		
		keySize = 256;
		System.out.println ("\nGenerate SHA3 Key 256 Bit");
		PerfMeter.run(this, rounds, "testGenSHA3Key");		
		assertEquals(privKey.length*8,keySize );
		
		keySize = 384;
		System.out.println ("\nGenerate SHA3 Key 384 Bit");
		PerfMeter.run(this, rounds, "testGenSHA3Key");		
		assertEquals(privKey.length*8,keySize );

		keySize = 512;
		System.out.println ("\nGenerate SHA3 Key 512 Bit");
		PerfMeter.run(this, rounds, "testGenSHA3Key");		
		assertEquals(privKey.length*8,keySize );
		
	}	

}