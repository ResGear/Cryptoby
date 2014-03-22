/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
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
    double probability;

    public MillerRabin(int rounds){
        this.rounds = rounds;
    }
    
    @Override
    public boolean isPrime(BigInteger number) {
        boolean result = false;
        SecureRandom random = new SecureRandom();
        for (int i=0; i < this.rounds; i++) {
            result = runMillerRabin(number,random);
        }
        if(result == false){
            this.probability = 0;
        } else {
            this.probability = this.calcProbability(this.rounds);
        }
        return result;
    }
    
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
            temp = new BigInteger(number.bitLength()-1, random);
        } while (temp.compareTo(BigInteger.ONE) <= 0);

        // Screen out n if our random number happens to share a factor with n.
        if (!number.gcd(temp).equals(BigInteger.ONE)) return false;
        // For debugging, prints out the integer to test with.
        //System.out.println("Testing with " + temp);

        BigInteger d = number.subtract(BigInteger.ONE);

        // Figure s and d Values
        int s = 0;
        while ( (d.mod(TWO)).equals(BigInteger.ZERO)) {
            d = d.divide(TWO);
            s++;
        }

        BigInteger curValue = temp.modPow(d,number);

        // If this works out, it's a prime
        if (curValue.equals(BigInteger.ONE))
            return true;

        // Otherwise, we will check to see if this value successively 
        // squared ever yields -1.
        for (int r = 0; r < s; r++) {

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

    private double calcProbability(int rounds) {
        return 100 - (1/(Math.pow(4, rounds)))*100;
    }

}