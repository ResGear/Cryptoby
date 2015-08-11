package prime;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.*;

import prime.imp.MillerRabin;
import tester.PerfMeter;

public class TestMillerRabin {

	int cycles = 1;
	MillerRabin ptest;
	BigInteger prime;
	int round5 = 5;
	int round10 = 10;
	int round15 = 15;
	Boolean bolprime = false;

	@Before
	public void setUp() {

	}

	public void testPrimeProbe() {
		for (int i = 0; i < cycles; i++) {
			bolprime = ptest.isPrime(prime);
		}
	}

	@Test
	public void testRun() {
		int rounds = 50;

		System.out.println("Test Performance Miller Rabin Prime Probe");
		System.out.println("Rounds: " + rounds);
		System.out.println("Cycle per Round: " + cycles);

		
		prime = BigPrimes.dig100;
		ptest = new MillerRabin (round5);
		System.out.println("\nTest 100 Number Prime 5 Rounds");
		PerfMeter.run(this, rounds, "testPrimeProbe");
		ptest = new MillerRabin (round10);
		System.out.println("\nTest 100 Number Prime 10 Rounds");
		PerfMeter.run(this, rounds, "testPrimeProbe");
		ptest = new MillerRabin (round15);
		System.out.println("\nTest 100 Number Prime 15 Rounds");
		PerfMeter.run(this, rounds, "testPrimeProbe");
		assertEquals(prime.isProbablePrime(round15), bolprime);
		
		prime = BigPrimes.dig500;
		ptest = new MillerRabin (round5);
		System.out.println("\nTest 500 Number Prime 5 Rounds");
		PerfMeter.run(this, rounds, "testPrimeProbe");
		ptest = new MillerRabin (round10);
		System.out.println("\nTest 500 Number Prime 10 Rounds");
		PerfMeter.run(this, rounds, "testPrimeProbe");
		ptest = new MillerRabin (round15);
		System.out.println("\nTest 500 Number Prime 15 Rounds");
		PerfMeter.run(this, rounds, "testPrimeProbe");
		assertEquals(prime.isProbablePrime(round15), bolprime);

		prime = BigPrimes.dig1000;
		ptest = new MillerRabin (round5);
		System.out.println("\nTest 1000 Number Prime 5 Rounds");
		PerfMeter.run(this, rounds, "testPrimeProbe");
		ptest = new MillerRabin (round10);
		System.out.println("\nTest 1000 Number Prime 10 Rounds");
		PerfMeter.run(this, rounds, "testPrimeProbe");
		ptest = new MillerRabin (round15);
		System.out.println("\nTest 1000 Number Prime 15 Rounds");
		PerfMeter.run(this, rounds, "testPrimeProbe");
		assertEquals(prime.isProbablePrime(round15), bolprime);
		
	}

}