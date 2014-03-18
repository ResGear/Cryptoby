/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.zhaw.cryptoby.prime.imp;

import ch.zhaw.cryptoby.prime.itf.PrimeTest;
import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * 
 * @author Toby
 */
public class MillerRabin implements PrimeTest {
    
    public static final BigInteger ZERO = BigInteger.ZERO;  // declaring constants
    public static final BigInteger ONE  = BigInteger.ONE;
    public static final BigInteger TWO = BigInteger.valueOf(2);
    int rounds;
    double probably;

    public MillerRabin(int rounds){
        this.rounds = rounds;
    }

    private static boolean runMillerRabin(BigInteger number, SecureRandom random) {
        if(number.compareTo(BigInteger.valueOf(3)) <= 0 ){
            return number.compareTo(BigInteger.ONE) != 0 ;
        }

        // Ensures that temp > 1 and temp < n.
        BigInteger temp = BigInteger.ZERO;
        do {
            temp = new BigInteger(number.bitLength()-1, random);
        } while (temp.compareTo(BigInteger.ONE) <= 0);

        // Screen out n if our random number happens to share a factor with n.
        if (!number.gcd(temp).equals(BigInteger.ONE)) return false;
        // For debugging, prints out the integer to test with.
        //System.out.println("Testing with " + temp);

        BigInteger base = number.subtract(BigInteger.ONE);

        // Figure out the largest power of two that divides evenly into n-1.
        int k=0;
        while ( (base.mod(TWO)).equals(BigInteger.ZERO)) {
            base = base.divide(TWO);
            k++;
        }

        // This is the odd value r, as described in our text.
        //System.out.println("base is " + base);

        BigInteger curValue = temp.modPow(base,number);

        // If this works out, we just say it's prime.
        if (curValue.equals(BigInteger.ONE))
            return true;

        // Otherwise, we will check to see if this value successively 
        // squared ever yields -1.
        for (int i=0; i<k; i++) {

            // We need to really check n-1 which is equivalent to -1.
            if (curValue.equals(number.subtract(BigInteger.ONE)))
                return true;

            // Square this previous number - here I am just doubling the 
            // exponent. A more efficient implementation would store the
            // value of the exponentiation and square it mod n.
            else
                curValue = curValue.modPow(TWO, number);
        }

        // If none of our tests pass, we return false. The number is 
        // definitively composite if we ever get here.
        return false;
    }
	
    public static boolean millerRabin(BigInteger number, int rounds) {

        SecureRandom random = new SecureRandom();

        // Run Miller-Rabin numTimes number of times.
        for (int i=0; i<rounds; i++) {
                if (!runMillerRabin(number,random)) {
                    return false;
                }
        }
        // If we get here, we assume n is prime. This will be incorrect with
        // a probability no greater than 1/4^numTimes.
        return true;
    }

    private double calcProbability(int rounds) {
        return 100 - (1/(Math.pow(4, rounds)))*100;
    }
        
    @Override
    public double getProbability() {
        return this.probably;
    }

    @Override
    public boolean isPrime(BigInteger number) {
        boolean result = MillerRabin.millerRabin(number, this.rounds);
        if(result == false){
            this.probably = this.calcProbability(0);
        } else {
            this.probably = this.calcProbability(this.rounds);
        }
        return result;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public int getRounds() {
        return rounds;
    }

    public double getProbably() {
        return probably;
    }

    public void setProbably(double probably) {
        this.probably = probably;
    }
}
