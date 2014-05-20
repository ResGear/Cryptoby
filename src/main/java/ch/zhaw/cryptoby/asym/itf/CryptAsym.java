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

package ch.zhaw.cryptoby.asym.itf;

/**
 * Interface for asymmetric cryptology implementations
 *
 * @author Tobias Rees
 */
public interface CryptAsym {
    
    /**
     * Implementation of asymmetric encrypt method
     *
     * @param plainInput Byte array input to encrypt in implemented mode
     * @param publicKey Public key to encrypt plainInput parameter
     * @return Encrypted byte array
     */
    public byte[] encrypt(byte[] plainInput, byte[] publicKey);

    /**
     * Implementation of asymmetric decrypt method
     *
     * @param cryptInput Encrypted byte array to decrypt in implemented mode
     * @param privateKey Private key to decrypt cryptInput parameter
     * @return Decrypted byte array
     */
    public byte[] decrypt(byte[] cryptInput, byte[] privateKey);
    
}
