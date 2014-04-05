package ch.zhaw.cryptoby.sym.imp;

import ch.zhaw.cryptoby.sym.itf.CryptSym;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Toby
 */
public class CryptAES implements CryptSym {

    private final int Nb = 4; // words in a block, always 4 for now
    private int Nk; // key length in words
    private int Nr; // number of rounds, = Nk + 6
    private int keyCount; // position in tempKey for RoundKey (= 0 each encrypt)
    private CryptTablesAES tab; // all the tables needed for AESy
    
    private byte[] initKeyExpand(byte[] key){
        Nk = (key.length/16); // words in a key, = 4, or 6, or 8
        Nr = Nk + 6; // corresponding number of rounds
        tab = new CryptTablesAES(); // class to give values of various functions
        return expandKey(key); // length of w depends on Nr
    }
    
    @Override
    public byte[] decrypt(byte[] cryptInput, byte[] key) {
         byte[] exKey = initKeyExpand(key);
        byte[] plainOutput = cryptInput;
        int inputLength = cryptInput.length;
        int restInput = cryptInput.length%16;
        int endOfLoop = inputLength-restInput;
        byte[] cipher = new byte[16];
        
        for(int i=0;i<endOfLoop;i+=16){
            System.arraycopy(plainOutput, i, cipher, i, i+16 - i);
            // Decrypt Cipher
            cipher = this.decryptCipher(cipher, exKey);
            System.arraycopy(cipher, i, plainOutput, i, i+16 - i);
        }
        
//            for(int i=0;i<endOfLoop;i+=16){
//            // Create 16 Block Cipher Arrays
//            for(int j=i;j<i+16;j++){
//              cipher[j] = plainOutput[j];
//            }
//            // Decrypt Cipher
//            this.decryptCipher(cipher, exKey);
//            // Copy Cipher back in plainOutput Array
//            for(int j=i;j<i+16;j++){
//              plainOutput[j] = cipher[j];  
//            }
//        }
        
        // Decrypt the last Cipher Block
        for(int i=endOfLoop;i<restInput;i++){
            cipher[i] = plainOutput[i];
        }
        cipher = this.decryptCipher(cipher, exKey);
        for(int i=endOfLoop;i<restInput;i++){
            plainOutput[i] = cipher[i];  
        }
        
        return plainOutput;
    }

    @Override
    public byte[] encrypt(byte[] plainInput, byte[] key) {
        byte[] exKey = initKeyExpand(key);
        byte[] cryptOutput = plainInput;
        int inputLength = plainInput.length;
        int restInput = plainInput.length%16;
        int endOfLoop = inputLength-restInput;
        byte[] cipher = new byte[16];
        
        for(int i=0;i<endOfLoop;i+=16){
            System.arraycopy(cryptOutput, i, cipher, i, i+16 - i);
            // Decrypt Cipher
            cipher = this.encryptCipher(cipher, exKey);
            // Copy cipher back to Output Array
            System.arraycopy(cipher, i, cryptOutput, i, i+16 - i);
        }
        // Decrypt the last Cipher Block
        for(int i=endOfLoop;i<restInput;i++){
            cipher[i] = cryptOutput[i];
        }
        cipher = this.encryptCipher(cipher, exKey);
        for(int i=endOfLoop;i<restInput;i++){
            cryptOutput[i] = cipher[i];  
        }
        
        return cryptOutput;
        
       
    }

    private byte[] decryptCipher(byte[] cipher, byte[] exKey) {
        keyCount = 4*Nb*(Nr+1);
        byte[][] state = arrayToMatrix(cipher);
        
        // Preround
        state = invAddRoundKey(state, exKey);

        // Crypt Rounds
        for (int i = 1; i < Nr; i++) {
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
        for (int i = 1; i < Nr; i++) {
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
        byte[] tempKey = new byte[4 * Nb * (Nr + 1)]; // room for expanded key
        byte[] temp = new byte[4];
        // first just copy key to tempKey
        int j = 0;
        while (j < 4 * Nk) {
            tempKey[j] = key[j++];
        }
        // here j == 4*Nk;
        int i;
        while (j < 4 * Nb * (Nr + 1)) {
            i = j / 4; // j is always multiple of 4 here
            // handle everything word-at-a time, 4 bytes at a time
            for (int iTemp = 0; iTemp < 4; iTemp++) {
                temp[iTemp] = tempKey[j - 4 + iTemp];
            }
            if (i % Nk == 0) {
                byte tTemp, tRcon;
                byte oldtemp0 = temp[0];
                for (int iTemp = 0; iTemp < 4; iTemp++) {
                    if (iTemp == 3) {
                        tTemp = oldtemp0;
                    } else {
                        tTemp = temp[iTemp + 1];
                    }
                    if (iTemp == 0) {
                        tRcon = tab.Rcon(i / Nk);
                    } else {
                        tRcon = 0;
                    }
                    temp[iTemp] = (byte) (tab.SBox(tTemp) ^ tRcon);
                }
            } else if (Nk > 6 && (i % Nk) == 4) {
                for (int iTemp = 0; iTemp < 4; iTemp++) {
                    temp[iTemp] = tab.SBox(temp[iTemp]);
                }
            }
            for (int iTemp = 0; iTemp < 4; iTemp++) {
                tempKey[j + iTemp] = (byte) (tempKey[j - 4 * Nk + iTemp] ^ temp[iTemp]);
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
            for (int c = 0; c < Nb; c++) {
                t[c] = state[r][(c + r) % Nb];
            }
            System.arraycopy(t, 0, state[r], 0, Nb);      
//            for (int c = 0; c < Nb; c++) {
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
            sp[0] = tab.FFMul(b02, state[0][c]) ^ tab.FFMul(b03, state[1][c]) ^ 
                    state[2][c] ^ state[3][c];
            sp[1] = state[0][c] ^ tab.FFMul(b02, state[1][c]) ^ 
                    tab.FFMul(b03, state[2][c]) ^ state[3][c];
            sp[2] = state[0][c] ^ state[1][c] ^ tab.FFMul(b02, state[2][c]) ^ 
                    tab.FFMul(b03, state[3][c]);
            sp[3] = tab.FFMul(b03, state[0][c]) ^ state[1][c] ^ 
                    state[2][c] ^ tab.FFMul(b02, state[3][c]);
            for (int i = 0; i < 4; i++) {
                state[i][c] = (byte) (sp[i]);
            }
        }
        return state;
    }
        
    // SubBytes: apply Sbox substitution to each byte of state
    private byte[][] subBytes(byte[][] state) {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < Nb; col++)
        state[row][col] = tab.SBox(state[row][col]);
        }
        return state;
    }
    
    // AddRoundKey: xor a portion of expanded key with state
    private byte[][] addRoundKey(byte[][] state, byte[] exKey) {
        for (int c = 0; c < Nb; c++) {
            for (int r = 0; r < 4; r++) {
                state[r][c] = (byte)(state[r][c] ^ exKey[keyCount++]);
            }
        }
        return state;
    }

    
    // Decryption Functions
    // InvSubBytes: apply inverse Sbox substitution to each byte of state
    private byte[][] invSubBytes(byte[][] state) {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < Nb; col++) {
                state[row][col] = tab.invSBox(state[row][col]);
            }
        }
        return state;
    }

    // InvShiftRows: right circular shift of rows 1, 2, 3 by 1, 2, 3
    private byte[][] invShiftRows(byte[][] state) {
        byte[] t = new byte[4];
        for (int r = 1; r < 4; r++) {
            for (int c = 0; c < Nb; c++) {
                t[(c + r) % Nb] = state[r][c];
            }
            System.arraycopy(t, 0, state[r], 0, Nb);
        }
        return state;
    }
    
    // InvMixColumns: complex and sophisticated mixing of columns
    private byte[][]invMixColumns(byte[][] state) {
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
        for (int c = Nb - 1; c >= 0; c--) {
            for (int r = 3; r >= 0; r--) {
                state[r][c] = (byte) (state[r][c] ^ key[--keyCount]);
            }
        }
        return state;
    }
    
    
    // Help Functions
    
// Converts the given byte array to a 4 by 4 matrix by column

    public byte[][] arrayToMatrix(byte[] array) {
        byte[][] matrix = new byte[4][Nk];
        int inLoc = 0;
        for (int c = 0; c < Nb; c++) {
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
        for (int c = 0; c < Nb; c++) {
            for (int r = 0; r < 4; r++) {
                array[outLoc++] = matrix[r][c];
            }
        }
        return array;
    }
    
}