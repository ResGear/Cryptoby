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
package ch.zhaw.cryptoby.keygen.itf;

/**
 * Interface for symmetric key generator implementations
 *
 * @author Tobias Rees
 */
public interface KeyGenSym {

    /**
     *
     * @param keySize Size of key which will be generated
     * @return Return key as String
     */
    public String generateKey(int keySize);

    /**
     *
     * @param keySize Size of key which will be generated
     * @param password Password for key
     * @return Return key as String
     */
    public String generateKey(int keySize, String password);

    /**
     *
     * @param keySize Size of key which will be generated
     * @return Return key as byte array
     */
    public byte[] generateKeyByte(int keySize);

    /**
     *
     * @param keySize Size of key which will be generated
     * @param password Password for key
     * @return Return key as byte array
     */
    public byte[] generateKeyByte(int keySize, String password);

}
