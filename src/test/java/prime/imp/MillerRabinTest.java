/*
 * Copyright (C) 2014 Toby
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package prime.imp;

import prime.imp.MillerRabin;
import java.math.BigInteger;
import java.security.SecureRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tobias Rees
 */
public class MillerRabinTest {
    
    /**
     * Test of getProbability method, of class MillerRabin.
     */
    @Test
    public void testGetProbability() {
        System.out.println("getProbability");
        SecureRandom random = new SecureRandom();
        MillerRabin instance = new MillerRabin(5);
        BigInteger number = BigInteger.probablePrime(1000, random);
        instance.isPrime(number);
        double expResult = 100 - (1/(Math.pow(4, 5)))*100;
        double result = instance.getProbability();
        assertEquals(expResult, result, 10^-5);
    }

    /**
     * Test of isPrime method, of class MillerRabin.
     */
    @Test
    public void testIsPrime_BigInteger() {
        System.out.println("isPrime");
        SecureRandom random = new SecureRandom();
        BigInteger number = BigInteger.probablePrime(1000, random);
        MillerRabin instance = new MillerRabin(5);
        boolean expResult = true;
        boolean result = instance.isPrime(number);
        assertEquals(expResult, result);
        // Test number 2, expects true
        number = BigInteger.valueOf(2);
        result = instance.isPrime(number);
        assertEquals(expResult, result);
        // Test number 3, expects true
        number = BigInteger.valueOf(3);
        result = instance.isPrime(number);
        assertEquals(expResult, result);
        // positive false test
        expResult = false;
        number = BigInteger.ONE.add(BigInteger.probablePrime(1000, random));
        result = instance.isPrime(number);
        assertEquals(expResult, result);
        // Test number 1, expects false
        result = instance.isPrime(BigInteger.ONE);
        assertEquals(expResult, result);
    }
    
}
