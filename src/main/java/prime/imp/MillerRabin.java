/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prime.imp;

import prime.itf.PrimeTest;
import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * This class provides the implementation of the Miller Rabin prime number test.
 *
 * @author Tobias Rees
 */
public class MillerRabin implements PrimeTest {

    private static final BigInteger TWO = BigInteger.valueOf(2);
    private final int rounds;
    private double probability;

    /**
     * Constructor needs input for number of rounds which will be used in
     * isPrime method to increase probability that the number is prime.
     *
     * @param rounds
     */
    public MillerRabin(int rounds) {
        this.rounds = rounds;
    }

    /**
     * Test the input number to be probability a prime number.
     *
     * @param number Number for prime test
     * @return Return true is a probability a prime and false if sure not a
     * prime
     */
    @Override
    public boolean isPrime(BigInteger number) {
        boolean result = false;
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < this.rounds; i++) {
            result = runMillerRabin(number, random);
        }
        if (result == false) {
            this.probability = 0;
        } else {
            this.probability = this.calcProbability(this.rounds);
        }
        return result;
    }

    /**
     * Get probability that's the number is prime in percent.
     *
     * @return Return probability as double
     */
    @Override
    public double getProbability() {
        return this.probability;
    }

    private static boolean runMillerRabin(BigInteger number, SecureRandom random) {
        if (number.compareTo(BigInteger.valueOf(3)) <= 0) {
            return number.compareTo(BigInteger.ONE) != 0;
        }

        // Ensures that temp > 1 and temp < n.
        BigInteger temp = BigInteger.ZERO;
        do {
            temp = new BigInteger(number.bitLength() - 1, random);
        } while (temp.compareTo(BigInteger.ONE) <= 0);

        // Screen out n if our random number happens to share a factor with n.
        if (!number.gcd(temp).equals(BigInteger.ONE)) {
            return false;
        }
        // For debugging, prints out the integer to test with.
        //System.out.println("Testing with " + temp);

        BigInteger d = number.subtract(BigInteger.ONE);

        // Figure s and d Values
        int s = 0;
        while ((d.mod(TWO)).equals(BigInteger.ZERO)) {
            d = d.divide(TWO);
            s++;
        }

        BigInteger curValue = temp.modPow(d, number);

        // If this works out, it's a prime
        if (curValue.equals(BigInteger.ONE)) {
            return true;
        }

        // Otherwise, we will check to see if this value successively 
        // squared ever yields -1.
        for (int r = 0; r < s; r++) {

            // We need to really check n-1 which is equivalent to -1.
            if (curValue.equals(number.subtract(BigInteger.ONE))) {
                return true;
            } // Square this previous number - here I am just doubling the 
            // exponent. A more efficient implementation would store the
            // value of the exponentiation and square it mod n.
            else {
                curValue = curValue.modPow(TWO, number);
            }
        }

        // If none of our tests pass, we return false. The number is 
        // definitively composite if we ever get here.
        return false;
    }

    private double calcProbability(int rounds) {
        return 100 - (1 / (Math.pow(4, rounds))) * 100;
    }

}
