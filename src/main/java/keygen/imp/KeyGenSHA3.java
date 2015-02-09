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
package keygen.imp;

import helper.CryptobyHelper;
import keygen.itf.KeyGenSym;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * This class provides SHA3-Keccak hash permutation.
 * 
 * @author Tobias Rees
 */
public class KeyGenSHA3 implements KeyGenSym {

    private static final long[] KeccakRoundConstants = {
        0x0000000000000001L, 0x0000000000008082L, 0x800000000000808AL, 0x8000000080008000L,
        0x000000000000808BL, 0x0000000080000001L, 0x8000000080008081L, 0x8000000000008009L,
        0x000000000000008AL, 0x0000000000000088L, 0x0000000080008009L, 0x000000008000000AL,
        0x000000008000808BL, 0x800000000000008BL, 0x8000000000008089L, 0x8000000000008003L,
        0x8000000000008002L, 0x8000000000000080L, 0x000000000000800AL, 0x800000008000000AL,
        0x8000000080008081L, 0x8000000000008080L, 0x0000000080000001L, 0x8000000080008008L,};

    private static final int[] KeccakRhoOffsets = {0, 1, 62, 28, 27, 36, 44, 6, 55, 20,
        3, 10, 43, 25, 39, 41, 45, 15, 21, 8, 18, 2, 61, 56, 14};

    private static final int nrRounds = 24;
    private static final int KeccakPermutationSize = 1600;
    private static final int KeccakPermutationSizeInBytes = (KeccakPermutationSize / 8);
    private static final int KeccakMaximumRate = 1152;
    private static final int KeccakMaximumRateInBytes = (KeccakMaximumRate / 8);

    private static final byte[] state = new byte[KeccakPermutationSizeInBytes];
    private static final long[] stateAsWords = new long[KeccakPermutationSize / 64];
    private static final byte[] dataQueue = new byte[KeccakMaximumRateInBytes];
    private static final long[] B = new long[25];
    private static final long[] C = new long[5];
    private static final long[] D = new long[5];
    private static int rate;
    private static int capacity;
    private static byte diversifier;
    private static int hashLength;
    private static int bitsInQueue;

    /**
     * Generate a random SHA3 Hash/Key as String.
     * 
     * @param keySize Size of hash. Allowed are 224, 256, 384 and 512.
     * @return SHA3 hash as Hex String
     */
    @Override
    public String generateKey(int keySize) {
        SecureRandom scRandom = new SecureRandom();
        byte[] randomPW = new byte[40];
        scRandom.nextBytes(randomPW);
        KeyGenSHA3.init(keySize);
        KeyGenSHA3.update(randomPW, randomPW.length * 8);
        String output = CryptobyHelper.bytesToHexString(KeyGenSHA3.getHash());
        return output;
    }

    /**
     * Generate a SHA3 Hash/Key depend on password input as String.
     *
     * @param keySize Size of hash. Allowed are 224, 256, 384 and 512.
     * @param password String password which will be hashed
     * @return SHA3 hash as Hex String
     */
    @Override
    public String generateKey(int keySize, String password) {
        byte[] bytePW = password.getBytes();
        KeyGenSHA3.init(keySize);
        KeyGenSHA3.update(bytePW, bytePW.length * 8);
        String output = CryptobyHelper.bytesToHexString(KeyGenSHA3.getHash());
        return output;
    }

    /**
     * Generate a random SHA3 Hash/Key as byte array.
     *
     * @param keySize Size of hash. Allowed are 224, 256, 384 and 512.
     * @return SHA3 hash as byte array
     */
    @Override
    public byte[] generateKeyByte(int keySize) {
        SecureRandom scRandom = new SecureRandom();
        byte[] randomPW = new byte[40];
        scRandom.nextBytes(randomPW);
        KeyGenSHA3.init(keySize);
        KeyGenSHA3.update(randomPW, randomPW.length * 8);
        byte[] output = KeyGenSHA3.getHash();
        return output;
    }

    /**
     * Generate a SHA3 Hash/Key depend on password input as byte array.
     *
     * @param keySize Size of hash. Allowed are 224, 256, 384 and 512.
     * @param password String password which will be hashed
     * @return SHA3 hash as byte array
     */
    @Override
    public byte[] generateKeyByte(int keySize, String password) {
        byte[] bytePW = password.getBytes();
        KeyGenSHA3.init(keySize);
        KeyGenSHA3.update(bytePW, bytePW.length * 8);
        byte[] output = KeyGenSHA3.getHash();
        return output;
    }

    private static void init(int hashLength) {
        switch (hashLength) {
            case 224:
                capacity = 448;
                break;
            case 256:
                capacity = 512;
                break;
            case 384:
                capacity = 768;
                break;
            case 512:
                capacity = 1024;
                break;
            default:
                throw new RuntimeException("Not allowed Hash Length!");
        }
        rate = KeccakPermutationSize - capacity;
        diversifier = (byte) (hashLength / 8);
        KeyGenSHA3.hashLength = hashLength;
        Arrays.fill(state, (byte) 0);
        Arrays.fill(dataQueue, (byte) 0);
        bitsInQueue = 0;
    }

    private static void update(byte[] data, int databitlen) {
        if ((bitsInQueue % 8) != 0) {
            throw new RuntimeException("Only the last call may contain a partial byte");
        }
        int k = 0;
        while (k < databitlen) {
            if ((bitsInQueue == 0) && (databitlen >= rate) && (k <= (databitlen - rate))) {
                int wholeBlocks = (databitlen - k) / rate;
                int curData = (int) (k / 8);
                for (int j = 0; j < wholeBlocks; j++, curData += rate / 8) {
                    for (int i = 0; i < rate / 8; i++) {
                        state[i] ^= data[i + curData];
                    }
                    keccakPermutation();
                }
                k += wholeBlocks * rate;
            } else {
                int partialBlock = databitlen - k;
                if (partialBlock + bitsInQueue > rate) {
                    partialBlock = rate - bitsInQueue;
                }
                int partialByte = partialBlock % 8;
                partialBlock -= partialByte;
                System.arraycopy(data, k / 8, dataQueue, bitsInQueue / 8, partialBlock / 8);
                bitsInQueue += partialBlock;
                k += partialBlock;
                if (bitsInQueue == rate) {
                    absorbQueue();
                }
                if (partialByte > 0) {
                    // Align the last partial byte to the least significant bits
                    byte lastByte = (byte) ((data[k / 8] & 0xFF) >>> (8 - partialByte));
                    dataQueue[bitsInQueue / 8] = lastByte;
                    bitsInQueue += partialByte;
                    k += partialByte;
                }
            }
        }
    }

    private static byte[] getHash() {
        keccakPad();
        byte[] hashval = new byte[hashLength / 8];
        if (hashLength > 0) {
            System.arraycopy(dataQueue, 0, hashval, 0, hashLength / 8);
        }
        return hashval;
    }

    private static void keccakPermutation() {
        fromBytesToWords();
        for (int i = 0; i < nrRounds; i++) {
            theta();
            rho();
            pi();
            chi();
            iota(i);
        }
        fromWordsToBytes();
    }

    private static void theta() {
        for (int x = 0; x < 5; x++) {
            C[x] = 0;
            for (int y = 0; y < 5; y++) {
                C[x] ^= stateAsWords[index(x, y)];
            }
            D[x] = rot(C[x], 1);
        }
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                stateAsWords[index(x, y)] ^= D[(x + 1) % 5] ^ C[(x + 4) % 5];
            }
        }
    }

    private static void rho() {
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                stateAsWords[index(x, y)] = rot(stateAsWords[index(x, y)], KeccakRhoOffsets[index(x, y)]);
            }
        }
    }

    private static void pi() {
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                B[index(x, y)] = stateAsWords[index(x, y)];
            }
        }
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                stateAsWords[index(0 * x + 1 * y, 2 * x + 3 * y)] = B[index(x, y)];
            }
        }
    }

    private static void chi() {
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                C[x] = stateAsWords[index(x, y)] ^ ((~stateAsWords[index(x + 1, y)]) & stateAsWords[index(x + 2, y)]);
            }
            for (int x = 0; x < 5; x++) {
                stateAsWords[index(x, y)] = C[x];
            }
        }
    }

    private static void iota(int indexRound) {
        stateAsWords[index(0, 0)] ^= KeccakRoundConstants[indexRound];
    }

    private static void keccakPad() {
        if ((bitsInQueue % 8) != 0) {
            // The bits are numbered from 0=LSB to 7=MSB
            byte padByte = (byte) (1 << (bitsInQueue % 8));
            dataQueue[bitsInQueue / 8] |= padByte;
            bitsInQueue += 8 - (bitsInQueue % 8);
        } else {
            dataQueue[bitsInQueue / 8] = 0x01;
            bitsInQueue += 8;
        }
        if (bitsInQueue == rate) {
            absorbQueue();
        }
        dataQueue[bitsInQueue / 8] = diversifier;
        bitsInQueue += 8;
        if (bitsInQueue == rate) {
            absorbQueue();
        }
        dataQueue[bitsInQueue / 8] = (byte) (rate / 8);
        bitsInQueue += 8;
        if (bitsInQueue == rate) {
            absorbQueue();
        }
        dataQueue[bitsInQueue / 8] = 0x01;
        bitsInQueue += 8;
        if (bitsInQueue > 0) {
            absorbQueue();
        }
        System.arraycopy(state, 0, dataQueue, 0, rate / 8);
    }

    private static void absorbQueue() {
        // bitsInQueue is assumed to be a multiple of 8
        Arrays.fill(dataQueue, bitsInQueue / 8, rate / 8, (byte) 0);
        for (int i = 0; i < rate / 8; i++) {
            state[i] ^= dataQueue[i];
        }
        keccakPermutation();
        bitsInQueue = 0;
    }

    // Helper Functions
    private static long rot(long a, int offset) {
        return (a << offset) | (a >>> -offset);
    }

    private static void fromBytesToWords() {
        for (int i = 0, j = 0; i < (KeccakPermutationSize / 64); i++, j += 8) {
            stateAsWords[i] = ((long) state[j] & 0xFFL)
                    | ((long) state[j + 1] & 0xFFL) << 8
                    | ((long) state[j + 2] & 0xFFL) << 16
                    | ((long) state[j + 3] & 0xFFL) << 24
                    | ((long) state[j + 4] & 0xFFL) << 32
                    | ((long) state[j + 5] & 0xFFL) << 40
                    | ((long) state[j + 6] & 0xFFL) << 48
                    | ((long) state[j + 7] & 0xFFL) << 56;
        }
    }

    private static void fromWordsToBytes() {
        for (int i = 0, j = 0; i < (KeccakPermutationSize / 64); i++, j += 8) {
            state[j] = (byte) (stateAsWords[i]);
            state[j + 1] = (byte) (stateAsWords[i] >> 8);
            state[j + 2] = (byte) (stateAsWords[i] >> 16);
            state[j + 3] = (byte) (stateAsWords[i] >> 24);
            state[j + 4] = (byte) (stateAsWords[i] >> 32);
            state[j + 5] = (byte) (stateAsWords[i] >> 40);
            state[j + 6] = (byte) (stateAsWords[i] >> 48);
            state[j + 7] = (byte) (stateAsWords[i] >> 56);
        }
    }

    private static int index(int x, int y) {
        return (((x) % 5) + 5 * ((y) % 5));
    }

}
