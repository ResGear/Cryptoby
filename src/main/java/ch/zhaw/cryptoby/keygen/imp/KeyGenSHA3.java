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

package ch.zhaw.cryptoby.keygen.imp;

import ch.zhaw.cryptoby.core.CryptobyHelper;
import ch.zhaw.cryptoby.keygen.itf.KeyGenSym;
import java.security.SecureRandom;
import java.util.Arrays;


/**
 *
 * @author Toby
 */
public class KeyGenSHA3 implements KeyGenSym {

    static final long[] KeccakRoundConstants = {
        0x0000000000000001L, 0x0000000000008082L, 0x800000000000808AL, 0x8000000080008000L,
        0x000000000000808BL, 0x0000000080000001L, 0x8000000080008081L, 0x8000000000008009L,
        0x000000000000008AL, 0x0000000000000088L, 0x0000000080008009L, 0x000000008000000AL,
        0x000000008000808BL, 0x800000000000008BL, 0x8000000000008089L, 0x8000000000008003L,
        0x8000000000008002L, 0x8000000000000080L, 0x000000000000800AL, 0x800000008000000AL,
        0x8000000080008081L, 0x8000000000008080L, 0x0000000080000001L, 0x8000000080008008L,
    };

    static final int[] KeccakRhoOffsets = {0, 1, 62, 28, 27, 36, 44, 6, 55, 20, 3, 10, 43, 25, 39, 41, 45, 15, 21, 8, 18, 2, 61, 56, 14};

    static final int nrRounds = 24;
    static final int KeccakPermutationSize = 1600;
    static final int KeccakPermutationSizeInBytes = (KeccakPermutationSize/8);
    static final int KeccakMaximumRate = 1152;
    static final int KeccakMaximumRateInBytes = (KeccakMaximumRate/8);

    byte[] state = new byte[KeccakPermutationSizeInBytes];
    long[] stateAsWords = new long[KeccakPermutationSize/64];
    byte[] dataQueue = new byte[KeccakMaximumRateInBytes];
    long[] B = new long[25];
    long[] C = new long[5];
    long[] D = new long[5];
    int rate;
    int capacity;
    byte diversifier;
    int hashbitlen;
    int bitsInQueue;
    boolean squeezing;
    int bitsAvailableForSqueezing;

    @Override
    public String generateKey(int keySize) {
        SecureRandom scRandom = new SecureRandom();
        byte[] randomPW = new byte[136];
        scRandom.nextBytes(randomPW);
        this.init(keySize);
        this.update(randomPW, randomPW.length*8);
        String output = CryptobyHelper.bytesToHexString(this.getHash(null));
        return output;
        
    }

    @Override
    public String generateKey(int keySize, String password) {
        byte[] bytePW = password.getBytes();
        this.init(keySize);
        this.update(bytePW, bytePW.length*8);
        String output = CryptobyHelper.bytesToHexString(this.getHash(null));
        return output;
    }
    
    private void init(int hashbitlen) {
        switch(hashbitlen) {
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
            throw new RuntimeException("BAD_HASHLEN");
        }
        rate = KeccakPermutationSize - capacity;
        diversifier = (byte)(hashbitlen/8);
        this.hashbitlen = hashbitlen;
        Arrays.fill(state, (byte)0);
        Arrays.fill(dataQueue, (byte)0);
        bitsInQueue = 0;
        squeezing = false;
        bitsAvailableForSqueezing = 0;
    }

    private void update(byte[] data, int databitlen) {
        if ((bitsInQueue % 8) != 0) {
            throw new RuntimeException("Only the last call may contain a partial byte");
        }
        if (squeezing) {
            throw new RuntimeException("Too late for additional input");
        }
        int k = 0;
        while (k < databitlen) {
            if ((bitsInQueue == 0) && (databitlen >= rate) && (k <= (databitlen-rate))) {
                int wholeBlocks = (databitlen - k)/rate;
                int curData = (int)(k/8);
                for (int j=0; j<wholeBlocks; j++, curData+=rate/8) {
                    for (int i = 0; i < rate/8; i++) {
                        state[i] ^= data[i+curData];
                    }
                    KeccakPermutation();
                }
                k += wholeBlocks*rate;
            } else {
                int partialBlock = databitlen - k;
                if (partialBlock+bitsInQueue > rate) {
                    partialBlock = rate-bitsInQueue;
                }
                int partialByte = partialBlock%8;
                partialBlock -= partialByte;
                System.arraycopy(data, k/8, dataQueue, bitsInQueue/8, partialBlock/8);
                bitsInQueue += partialBlock;
                k += partialBlock;
                if (bitsInQueue == rate) {
                    AbsorbQueue();
                }
                if (partialByte > 0) {
                    // Align the last partial byte to the least significant bits
                    byte lastByte = (byte)((data[k/8] & 0xFF) >>> (8-partialByte));
                    dataQueue[bitsInQueue/8] = lastByte;
                    bitsInQueue += partialByte;
                    k += partialByte;
                }
            }
        }
    }

    private byte[] getHash(byte[] hashval) {
        if (!squeezing) {
            KeccakPad();
            squeezing = true;
        }
        if (hashval == null) {
            hashval = new byte[hashbitlen/8];
        }
        if (hashbitlen > 0) {
            System.arraycopy(dataQueue, 0, hashval, 0, hashbitlen/8);
        }
        return hashval;
    }

    private void theta() {
        for (int x=0; x<5; x++) {
            C[x] = 0; 
            for (int y=0; y<5; y++) 
                C[x] ^= stateAsWords[index(x, y)];
            D[x] = ROL64(C[x], 1);
        }
        for (int x=0; x<5; x++)
            for (int y=0; y<5; y++)
                stateAsWords[index(x, y)] ^= D[(x+1)%5] ^ C[(x+4)%5];
    }
    
    private void rho() {
        for (int x=0; x<5; x++) for (int y=0; y<5; y++)
            stateAsWords[index(x, y)] = ROL64(stateAsWords[index(x, y)], KeccakRhoOffsets[index(x, y)]);
    }
    
    private void pi() {
        for (int x=0; x<5; x++) for (int y=0; y<5; y++)
            B[index(x, y)] = stateAsWords[index(x, y)];
        for (int x=0; x<5; x++) for (int y=0; y<5; y++)
            stateAsWords[index(0*x+1*y, 2*x+3*y)] = B[index(x, y)];
    }
    
    private void chi() {
        for (int y=0; y<5; y++) { 
            for (int x=0; x<5; x++)
                C[x] = stateAsWords[index(x, y)] ^ ((~stateAsWords[index(x+1, y)]) & stateAsWords[index(x+2, y)]);
            for (int x=0; x<5; x++)
                stateAsWords[index(x, y)] = C[x];
        }
    }
    
    private void iota(int indexRound) {
        stateAsWords[index(0, 0)] ^= KeccakRoundConstants[indexRound];
    }

    private void KeccakPermutation() {
        fromBytesToWords();
        for (int i=0; i<nrRounds; i++) {
            theta();
            rho();
            pi();
            chi();
            iota(i);
        }
        fromWordsToBytes();
    }

    private void AbsorbQueue() {
        // bitsInQueue is assumed to be a multiple of 8
        Arrays.fill(dataQueue, bitsInQueue/8, rate/8, (byte)0);
        for (int i = 0; i < rate/8; i++) {
            state[i] ^= dataQueue[i];
        }
        KeccakPermutation();
        bitsInQueue = 0;
    }

    private void KeccakPad() {
        if ((bitsInQueue % 8) != 0) {
            // The bits are numbered from 0=LSB to 7=MSB
            byte padByte = (byte)(1 << (bitsInQueue % 8));
            dataQueue[bitsInQueue/8] |= padByte;
            bitsInQueue += 8-(bitsInQueue % 8);
        } else {
            dataQueue[bitsInQueue/8] = 0x01;
            bitsInQueue += 8;
        }
        if (bitsInQueue == rate) {
            AbsorbQueue();
        }
        dataQueue[bitsInQueue/8] = diversifier;
        bitsInQueue += 8;
        if (bitsInQueue == rate) {
            AbsorbQueue();
        }
        dataQueue[bitsInQueue/8] = (byte)(rate/8);
        bitsInQueue += 8;
        if (bitsInQueue == rate) {
            AbsorbQueue();
        }
        dataQueue[bitsInQueue/8] = 0x01;
        bitsInQueue += 8;
        if (bitsInQueue > 0) {
            AbsorbQueue();
        }
        System.arraycopy(state, 0, dataQueue, 0, rate/8);
        bitsAvailableForSqueezing = rate;
    }


    
    // Helper Functions
    
    private long ROL64(long a, int offset) {
        return (a << offset) | (a >>> -offset);
    }

    private void fromBytesToWords() {
        for (int i = 0, j = 0; i < (KeccakPermutationSize/64); i++, j += 8) {
            stateAsWords[i] = ((long)state[j    ] & 0xFFL)
                            | ((long)state[j + 1] & 0xFFL) <<  8
                            | ((long)state[j + 2] & 0xFFL) << 16
                            | ((long)state[j + 3] & 0xFFL) << 24
                            | ((long)state[j + 4] & 0xFFL) << 32
                            | ((long)state[j + 5] & 0xFFL) << 40
                            | ((long)state[j + 6] & 0xFFL) << 48
                            | ((long)state[j + 7] & 0xFFL) << 56;
        }
    }

    private void fromWordsToBytes() {
        for (int i = 0, j = 0; i < (KeccakPermutationSize/64); i++, j += 8) {
            state[j    ] = (byte)(stateAsWords[i]      );
            state[j + 1] = (byte)(stateAsWords[i] >>  8);
            state[j + 2] = (byte)(stateAsWords[i] >> 16);
            state[j + 3] = (byte)(stateAsWords[i] >> 24);
            state[j + 4] = (byte)(stateAsWords[i] >> 32);
            state[j + 5] = (byte)(stateAsWords[i] >> 40);
            state[j + 6] = (byte)(stateAsWords[i] >> 48);
            state[j + 7] = (byte)(stateAsWords[i] >> 56);
        }
    }

    private static int index(int x, int y) {
        return (((x)%5)+5*((y)%5));
    }

}