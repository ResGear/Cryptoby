package ch.zhaw.cryptoby.sym.imp;

import ch.zhaw.cryptoby.sym.itf.CryptSym;

/**
 *
 * @author Toby
 */
public class CryptAES implements CryptSym {

    private final int nBlocks = 4; // words in a block, always 4 for now
    private int keyLength; // key length in words
    private int nRounds; // number of rounds, = keyLength + 6
    private int keyCount; // position in tempKey for RoundKey (= 0 each encrypt)
    private CryptTablesAES tab; // all the tables needed for AESy

    private byte[] initKeyExpand(byte[] key) {
        keyLength = (key.length / 4); // words in a key, = 4, or 6, or 8
        nRounds = keyLength + 6; // corresponding number of rounds
        tab = new CryptTablesAES(); // class to give values of various functions
        return expandKey(key);
    }

    @Override
    public byte[] encrypt(byte[] plainInput, byte[] key) {
        byte[] exKey = initKeyExpand(key);
        int inputLength = plainInput.length;
        int restInput = plainInput.length % 16;
        byte[] cryptOutput = new byte[(inputLength - restInput) + 16];
        int outputLength = cryptOutput.length;
        byte[] cipher = new byte[16];

        // Copy plaintext Array into crypt Array
        System.arraycopy(plainInput, 0, cryptOutput, 0, inputLength);
        // Add in the last Arrayindex the origin length of plaintext Array
        cryptOutput[outputLength - 1] = (byte) inputLength;

        for (int i = 0; i < outputLength; i += 16) {
            // Create 16 Block Cipher Arrays
            for (int j = i; j < i + 16; j++) {
                cipher[j - i] = cryptOutput[j];
            }
            // Encrypt Cipher
            cipher = this.encryptCipher(cipher, exKey);
            // Copy Cipher back in decryptOutput Array
            for (int j = i; j < i + 16; j++) {
                cryptOutput[j] = cipher[j - i];
            }
        }
        return cryptOutput;
    }

    @Override
    public byte[] decrypt(byte[] cryptInput, byte[] key) {
        byte[] exKey = initKeyExpand(key);
        byte[] decryptOutput = cryptInput;
        int outputLength = decryptOutput.length;
        byte[] cipher = new byte[16];
        byte[] plainOutput;

        for (int i = 0; i < outputLength; i += 16) {
            // Create 16 Block Cipher Arrays
            for (int j = i; j < i + 16; j++) {
                cipher[j - i] = decryptOutput[j];
            }
            // Encrypt Cipher
            cipher = this.decryptCipher(cipher, exKey);
            // Copy Cipher back in decryptOutput Array
            for (int j = i; j < i + 16; j++) {
                decryptOutput[j] = cipher[j - i];
            }
        }
        // Read last Index of encryptet Output  
        // and use the Integer Content for lenght of plainOutput
        int lengthOriginArray = decryptOutput[decryptOutput.length - 1];
        if(lengthOriginArray < 1){
            plainOutput = cryptInput;
        } else {
            plainOutput = new byte[lengthOriginArray];
            System.arraycopy(decryptOutput, 0, plainOutput, 0, lengthOriginArray);
        }

        return plainOutput;
    }

    private byte[] decryptCipher(byte[] cipher, byte[] exKey) {
        keyCount = 4 * nBlocks * (nRounds + 1);

        byte[][] state = arrayToMatrix(cipher);

        // Preround
        state = invAddRoundKey(state, exKey);

        // Crypt Rounds
        for (int i = 1; i < nRounds; i++) {
            state = invShiftRows(state);
            state = invSubBytes(state);
            state = invAddRoundKey(state, exKey);
            state = invMixColumns(state);
        }

        // Endround
        state = invShiftRows(state);
        state = invSubBytes(state);
        state = invAddRoundKey(state, exKey);
        return matrixToArray(state);
    }

    private byte[] encryptCipher(byte[] cipher, byte[] exKey) {
        keyCount = 0;
        byte[][] state = arrayToMatrix(cipher);

        // Preround
        state = addRoundKey(state, exKey);

        // Crypt Rounds
        for (int i = 1; i < nRounds; i++) {
            state = subBytes(state);
            state = shiftRows(state);
            state = mixColumns(state);
            state = addRoundKey(state, exKey);
        }

        // Endround
        state = subBytes(state);
        state = shiftRows(state);
        state = addRoundKey(state, exKey);

        return matrixToArray(state);
    }

    // Expand Key in 4xnk Matrix
    private byte[] expandKey(byte[] key) {
        byte[] tempKey = new byte[4 * nBlocks * (nRounds + 1)]; // room for expanded key
        byte[] temp = new byte[4];
        // first just copy key to tempKey
        int j = 0;
        while (j < 4 * keyLength) {
            tempKey[j] = key[j++];
        }
        // here j == 4*keyLength;
        int i;
        while (j < 4 * nBlocks * (nRounds + 1)) {
            i = j / 4; // j is always multiple of 4 here
            // handle everything word-at-a time, 4 bytes at a time
            for (int iTemp = 0; iTemp < 4; iTemp++) {
                temp[iTemp] = tempKey[j - 4 + iTemp];
            }
            if (i % keyLength == 0) {
                byte tTemp, tRcon;
                byte oldtemp0 = temp[0];
                for (int iTemp = 0; iTemp < 4; iTemp++) {
                    if (iTemp == 3) {
                        tTemp = oldtemp0;
                    } else {
                        tTemp = temp[iTemp + 1];
                    }
                    if (iTemp == 0) {
                        tRcon = tab.Rcon(i / keyLength);
                    } else {
                        tRcon = 0;
                    }
                    temp[iTemp] = (byte) (tab.SBox(tTemp) ^ tRcon);
                }
            } else if (keyLength > 6 && (i % keyLength) == 4) {
                for (int iTemp = 0; iTemp < 4; iTemp++) {
                    temp[iTemp] = tab.SBox(temp[iTemp]);
                }
            }
            for (int iTemp = 0; iTemp < 4; iTemp++) {
                tempKey[j + iTemp] = (byte) (tempKey[j - 4 * keyLength + iTemp] ^ temp[iTemp]);
                j = j + 4;
            }
        }
        return tempKey;
    }

    // Encryption Functions
    // ShiftRows: simple circular shift of rows 1, 2, 3 by 1, 2, 3
    private byte[][] shiftRows(byte[][] state) {
        byte[] t = new byte[4];
        for (int r = 1; r < 4; r++) {
            for (int c = 0; c < nBlocks; c++) {
                t[c] = state[r][(c + r) % nBlocks];
            }
            System.arraycopy(t, 0, state[r], 0, nBlocks);
//            for (int c = 0; c < nBlocks; c++) {
//                state[r][c] = t[c];
//            }
        }
        return state;
    }

    // MixColumns: complex and sophisticated mixing of columns
    private byte[][] mixColumns(byte[][] state) {
        int[] sp = new int[4];
        byte b02 = (byte) 0x02, b03 = (byte) 0x03;
        for (int c = 0; c < 4; c++) {
            sp[0] = tab.FFMul(b02, state[0][c]) ^ tab.FFMul(b03, state[1][c])
                    ^ state[2][c] ^ state[3][c];
            sp[1] = state[0][c] ^ tab.FFMul(b02, state[1][c])
                    ^ tab.FFMul(b03, state[2][c]) ^ state[3][c];
            sp[2] = state[0][c] ^ state[1][c] ^ tab.FFMul(b02, state[2][c])
                    ^ tab.FFMul(b03, state[3][c]);
            sp[3] = tab.FFMul(b03, state[0][c]) ^ state[1][c]
                    ^ state[2][c] ^ tab.FFMul(b02, state[3][c]);
            for (int i = 0; i < 4; i++) {
                state[i][c] = (byte) (sp[i]);
            }
        }
        return state;
    }

    // SubBytes: apply Sbox substitution to each byte of state
    private byte[][] subBytes(byte[][] state) {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < nBlocks; col++) {
                state[row][col] = tab.SBox(state[row][col]);
            }
        }
        return state;
    }

    // AddRoundKey: xor a portion of expanded key with state
    private byte[][] addRoundKey(byte[][] state, byte[] exKey) {
        for (int c = 0; c < nBlocks; c++) {
            for (int r = 0; r < 4; r++) {
                state[r][c] = (byte) (state[r][c] ^ exKey[keyCount++]);
            }
        }
        return state;
    }

    // Decryption Functions
    // InvSubBytes: apply inverse Sbox substitution to each byte of state
    private byte[][] invSubBytes(byte[][] state) {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < nBlocks; col++) {
                state[row][col] = tab.invSBox(state[row][col]);
            }
        }
        return state;
    }

    // InvShiftRows: right circular shift of rows 1, 2, 3 by 1, 2, 3
    private byte[][] invShiftRows(byte[][] state) {
        byte[] t = new byte[4];
        for (int r = 1; r < 4; r++) {
            for (int c = 0; c < nBlocks; c++) {
                t[(c + r) % nBlocks] = state[r][c];
            }
            System.arraycopy(t, 0, state[r], 0, nBlocks);
        }
        return state;
    }

    // InvMixColumns: complex and sophisticated mixing of columns
    private byte[][] invMixColumns(byte[][] state) {
        int[] sp = new int[4];
        byte b0b = (byte) 0x0b;
        byte b0d = (byte) 0x0d;
        byte b09 = (byte) 0x09;
        byte b0e = (byte) 0x0e;
        for (int c = 0; c < 4; c++) {
            sp[0] = tab.FFMul(b0e, state[0][c]) ^ tab.FFMul(b0b, state[1][c])
                    ^ tab.FFMul(b0d, state[2][c]) ^ tab.FFMul(b09, state[3][c]);
            sp[1] = tab.FFMul(b09, state[0][c]) ^ tab.FFMul(b0e, state[1][c])
                    ^ tab.FFMul(b0b, state[2][c]) ^ tab.FFMul(b0d, state[3][c]);
            sp[2] = tab.FFMul(b0d, state[0][c]) ^ tab.FFMul(b09, state[1][c])
                    ^ tab.FFMul(b0e, state[2][c]) ^ tab.FFMul(b0b, state[3][c]);
            sp[3] = tab.FFMul(b0b, state[0][c]) ^ tab.FFMul(b0d, state[1][c])
                    ^ tab.FFMul(b09, state[2][c]) ^ tab.FFMul(b0e, state[3][c]);
            for (int i = 0; i < 4; i++) {
                state[i][c] = (byte) (sp[i]);
            }
        }
        return state;
    }

    // InvAddRoundKey: same as AddRoundKey, but backwards
    private byte[][] invAddRoundKey(byte[][] state, byte[] key) {
        for (int c = nBlocks - 1; c >= 0; c--) {
            for (int r = 3; r >= 0; r--) {
                state[r][c] = (byte) (state[r][c] ^ key[--keyCount]);
            }
        }
        return state;
    }

    // Help Functions
// Converts the given byte array to a 4 by 4 matrix by column
    public byte[][] arrayToMatrix(byte[] array) {
        byte[][] matrix = new byte[4][keyLength];
        int inLoc = 0;
        for (int c = 0; c < nBlocks; c++) {
            for (int r = 0; r < 4; r++) {
                matrix[r][c] = array[inLoc++];
            }
        }
        return matrix;
    }

// Converts the given matrix to the corresponding array (by columns)
    public byte[] matrixToArray(byte[][] matrix) {
        byte[] array = new byte[16];
        int outLoc = 0;
        for (int c = 0; c < nBlocks; c++) {
            for (int r = 0; r < 4; r++) {
                array[outLoc++] = matrix[r][c];
            }
        }
        return array;
    }

}
