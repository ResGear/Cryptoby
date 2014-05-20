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

package ch.zhaw.cryptoby.sym.itf;

/**
 * Interface for symmetric cryptology implementations
 * 
 * @author Tobias Rees
 */
public interface CryptSym {
    
    /**
     * Implementation of symmetric encrypt method
     *
     * @param plainInput Plain byte array to encrypt
     * @param key Key to encrypt plainInput as byte array
     * @return
     */
    public byte[] encrypt(byte[] plainInput, byte[] key);

    /**
     * Implementation of symmetric decrypt method
     * 
     * @param cryptInput Plain byte array to encrypt
     * @param key Key to decrypt plainInput as byte array
     * @return
     */
    public byte[] decrypt(byte[] cryptInput, byte[] key);
    
}
