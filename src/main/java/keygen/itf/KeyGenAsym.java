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
package keygen.itf;

/**
 * Interface for asymmetric key generator implementations. With the
 * initGenerator the keys will be generated and the getter methods provides the
 * generated keys
 *
 * @author Tobias Rees
 */
public interface KeyGenAsym {

    /**
     *
     * @param keyBitSize Size of key which will be generated
     */
    public void initGenerator(int keyBitSize);

    /**
     *
     * @return Return private key as String
     */
    public String getPrivateKey();

    /**
     *
     * @return Return public key as String
     */
    public String getPublicKey();

    /**
     *
     * @return Return private key as byte array
     */
    public byte[] getPrivateKeyByte();

    /**
     *
     * @return Return public key as byte array
     */
    public byte[] getPublicKeyByte();
}
