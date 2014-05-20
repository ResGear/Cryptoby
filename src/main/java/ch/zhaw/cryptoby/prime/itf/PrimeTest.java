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

package ch.zhaw.cryptoby.prime.itf;

import java.math.BigInteger;

/**
 * Interface for prime test implementations.
 *
 * @author Tobias Rees
 */
public interface PrimeTest {
    
    /**
     * In this method the input number will be tested.
     *
     * @param number Input number to test
     * @return True or false if the number is a prime
     */
    public boolean isPrime(BigInteger number);

    /**
     * Get probability that input number in isPrime method is a prime.
     *
     * @return Return probability in percent as double
     */
    public double  getProbability();
    
}
