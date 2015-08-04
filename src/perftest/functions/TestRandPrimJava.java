package functions;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.junit.*;
import tester.PerfMeter;

public class TestRandPrimJava {

	int cycles = 1;
	SecureRandom secRand;
	int big512 = 512;
	int big1024 = 1024;
	int big2048 = 2048;

	@Before
	public void setUp() {
		secRand = new SecureRandom();
	}

	public void testRandPrim512() {
		for (int i = 0; i < cycles; i++) {
			BigInteger bigPrime = new BigInteger(big512, 1, secRand);
		}
	}

	public void testRandPrim1024() {
		for (int i = 0; i < cycles; i++) {
			BigInteger bigPrime = new BigInteger(big1024, 1, secRand);
		}
	}

	public void testRandPrim2048() {
		for (int i = 0; i < cycles; i++) {
			BigInteger bigPrime = new BigInteger(big2048, 1, secRand);
		}
	}

	@Test
	public void testRun() {
		int rounds = 20;
		
		System.out.println("Test Performance Java BigInteger Primes with SecureRandom");
		System.out.println("Rounds: "+rounds);
		System.out.println("Cycle per Round: "+cycles);
		System.out.println("\nGenerate Secure Random Prime 512 Bit");
		PerfMeter.run(this, rounds, "testRandPrim512");
		System.out.println("\nGenerate Secure Random Prime 1024 Bit");
		PerfMeter.run(this, rounds, "testRandPrim1024");
		System.out.println("\nGenerate Secure Random Prime 2048 Bit");
		PerfMeter.run(this, rounds, "testRandPrim2048");
	}

}
