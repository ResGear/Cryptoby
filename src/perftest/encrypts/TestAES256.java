package encrypts;

import static org.junit.Assert.assertArrayEquals;
import org.junit.*;

import keygen.imp.KeyGenSHA3;
import sym.imp.CryptAES;
import tester.PerfMeter;

public class TestAES256 {

	int cycles = 1;
	byte[] preData;
	byte[] modData;
	byte[] resData;
	int mb1;
	int mb5;
	int mb10;
	int keySize = 256;
	byte[] privKey;
	
	CryptAES aes;
	

	@Before
	public void setUp()
	{ 
		mb1 = 1024 * 1024;
		mb5 = mb1 * 5;
		mb10 = mb1 * 10;
		aes = new CryptAES();
		
	}

	public void testAESEnc() {
		modData = aes.encrypt(preData, privKey);
	}
	
	public void testAESDec() {
		resData = aes.decrypt(modData, privKey);
	}


	@Test
	public void testRun(){
		int rounds = 50;
		
		System.out.println("Test Performance AES Enc/Dec");
		System.out.println("Rounds: "+rounds);
		System.out.println("Cycle per Round: "+cycles);
		KeyGenSHA3 keyGen = new KeyGenSHA3();
		privKey = keyGen.generateKeyByte(keySize, "password");
		
		preData = new byte[mb1];
		System.out.println ("\nEncrypt AES 256 Bit 1MB");
		PerfMeter.run(this, rounds, "testAESEnc");
		System.out.println ("\nDecrypt AES 256 Bit 1MB");
		PerfMeter.run(this, rounds, "testAESDec");			
		assertArrayEquals(preData, resData);
		
		preData = new byte[mb5];
		System.out.println ("\nEncrypt AES 256 Bit 5MB");
		PerfMeter.run(this, rounds, "testAESEnc");
		System.out.println ("\nDecrypt AES 256 Bit 5MB");
		PerfMeter.run(this, rounds, "testAESDec");
		assertArrayEquals(preData, resData);
		
		preData = new byte[mb10];
		System.out.println ("\nEncrypt AES 256 Bit 10MB");
		PerfMeter.run(this, rounds, "testAESEnc");
		System.out.println ("\nDecrypt AES 256 Bit 10MB");
		PerfMeter.run(this, rounds, "testAESDec");
		assertArrayEquals(preData, resData);
	}	

}