package functions;

import java.security.SecureRandom;
import org.junit.*;
import tester.PerfMeter;

public class TestSecRandJava {

	int cycles = 10000;
	SecureRandom secRand;
	byte[] big512;
	byte[] big1024;
	byte[] big2048;

	@Before
	public void setUp()
	{ 
		secRand = new SecureRandom();
		big512 = new byte[64];
		big1024 = new byte[128];
		big2048 = new byte[256];
	}

	public void testSecRand512() {
		for(int i = 0; i < cycles; i++)
		{
			secRand.nextBytes (big512);
		}
	}

	public void testSecRand1024() {
		for(int i = 0; i < cycles; i++)
		{
			secRand.nextBytes (big1024);
		}
	}

	public void testSecRand2048() {
		for(int i = 0; i < cycles; i++)
		{
			secRand.nextBytes (big2048);
		}
	}

	@Test
	public void testRun(){
		int rounds = 50;

		System.out.println("Test Performance Java SecureRandom");
		System.out.println("Rounds: "+rounds);
		System.out.println("Cycle per Round: "+cycles);
		System.out.println ("\nGenerate Secure Random 512 Bit");
		PerfMeter.run(this, rounds, "testSecRand512");
		System.out.println  ("\nGenerate Secure Random 1024 Bit");
		PerfMeter.run(this, rounds, "testSecRand1024");
		System.out.println  ("\nGenerate Secure Random 2048 Bit");
		PerfMeter.run(this, rounds, "testSecRand2048");
	}

}
