package functions;

import java.math.BigInteger;
import org.junit.*;
import tester.PerfMeter;

public class TestBigIntJava {

	int cycles = 100000;
	byte[] big512;
	byte[] big1024;
	byte[] big2048;

	@Before
	public void setUp()
	{ 
		big512 = new byte[64];
		big1024 = new byte[128];
		big2048 = new byte[256];
	}

	public void testBigInt512() {
		
		for(int i = 0; i < cycles; i++)
		{
			BigInteger testBigInt = new BigInteger(big512);
		}
	}

	public void testBigInt1024() {
		for(int i = 0; i < cycles; i++)
		{
			BigInteger testBigInt = new BigInteger(big1024);
		}
	}

	public void testBigInt2048() {
		for(int i = 0; i < cycles; i++)
		{
			BigInteger testBigInt = new BigInteger(big2048);
		}
	}

	@Test
	public void testRun(){
		int rounds = 50;
		
		System.out.println("Test Performance Java BigInteger");
		System.out.println("Rounds: "+rounds);
		System.out.println("Cycle per Round: "+cycles);
		System.out.println ("\nGenerate BigInteger 512 Bit");
		PerfMeter.run(this, rounds, "testBigInt512");
		System.out.println  ("\nGenerate BigInteger 1024 Bit");
		PerfMeter.run(this, rounds, "testBigInt1024");
		System.out.println  ("\nGenerate BigInteger 2048 Bit");
		PerfMeter.run(this, rounds, "testBigInt2048");
	}

}
