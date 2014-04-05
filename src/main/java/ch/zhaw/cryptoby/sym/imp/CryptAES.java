package ch.zhaw.cryptoby.sym.imp;

import ch.zhaw.cryptoby.sym.itf.CryptSym;

/**
 *
 * @author Toby
 */
public class CryptAES implements CryptSym {

    private final int Nb = 4; // words in a block, always 4 for now
    private int Nk; // key length in words
    private int Nr; // number of rounds, = Nk + 6
    private int wCount; // position in w for RoundKey (= 0 each encrypt)
    private CryptTablesAES tab; // all the tables needed for AES
    private byte[] w; // the expanded key

    public CryptAES(byte[] key, int NkIn) {
        Nk = NkIn; // words in a key, = 4, or 6, or 8
        Nr = Nk + 6; // corresponding number of rounds
        w = new byte[4 * Nb * (Nr + 1)]; // room for expanded key
        expandKey(key, w); // length of w depends on Nr
    }

    // Expand Key in 4xnk Matrix
    private void expandKey(byte[] key, byte[] w) {
        byte[] temp = new byte[4];
        // first just copy key to w
        int j = 0;
        while (j < 4 * Nk) {
            w[j] = key[j++];
        }
        // here j == 4*Nk;
        int i;
        while (j < 4 * Nb * (Nr + 1)) {
            i = j / 4; // j is always multiple of 4 here
            // handle everything word-at-a time, 4 bytes at a time
            for (int iTemp = 0; iTemp < 4; iTemp++) {
                temp[iTemp] = w[j - 4 + iTemp];
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
                w[j + iTemp] = (byte) (w[j - 4 * Nk + iTemp] ^ temp[iTemp]);
                j = j + 4;
            }
        }

    }

   // ShiftRows: simple circular shift of rows 1, 2, 3 by 1, 2, 3
    private void shiftRows(byte[][] state) {
        byte[] t = new byte[4];
        for (int r = 1; r < 4; r++) {
            for (int c = 0; c < Nb; c++) {
                t[c] = state[r][(c + r) % Nb];
            }
            for (int c = 0; c < Nb; c++) {
                state[r][c] = t[c];
            }
        }
    }

// MixColumns: complex and sophisticated mixing of columns
    private void mixColumns(byte[][] s) {
        int[] sp = new int[4];
        byte b02 = (byte) 0x02, b03 = (byte) 0x03;
        for (int c = 0; c < 4; c++) {
            sp[0] = tab.FFMul(b02, s[0][c]) ^ tab.FFMul(b03, s[1][c]) ^ 
                    s[2][c] ^ s[3][c];
            sp[1] = s[0][c] ^ tab.FFMul(b02, s[1][c]) ^ 
                    tab.FFMul(b03, s[2][c]) ^ s[3][c];
            sp[2] = s[0][c] ^ s[1][c] ^ tab.FFMul(b02, s[2][c]) ^ 
                    tab.FFMul(b03, s[3][c]);
            sp[3] = tab.FFMul(b03, s[0][c]) ^ s[1][c] ^ 
                    s[2][c] ^ tab.FFMul(b02, s[3][c]);
            for (int i = 0; i < 4; i++) {
                s[i][c] = (byte) (sp[i]);
            }
        }
    }
        
    // SubBytes: apply Sbox substitution to each byte of state
    private void subBytes(byte[][] state) {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < Nb; col++)
        state[row][col] = tab.SBox(state[row][col]);
        }
    }
// AddRoundKey: xor a portion of expanded key with state

    private void addRoundKey(byte[][] state) {
        for (int c = 0; c < Nb; c++) {
            for (int r = 0; r < 4; r++) {
                state[r][c] = (byte)(state[r][c] ^ w[wCount++]);
            }
        }
    }


    
    
    private byte[] decryptCipher(byte[] cipher, byte[] key) {
        byte[][] state = arrayToMatrix(cipher);
        

        // Preround
        subBytes(state);
        shiftRows(state);
        addRoundKey(state);

        // Crypt Rounds
        for (int i = 0; i < 14; i++) {
            subBytes(state);
            shiftRows(state);
            mixColumns(state);
            addRoundKey(state);
        }

        // Endround
        addRoundKey(state);

        return matrixToArray(state);
    }

    
    private byte[] encryptCipher(byte[] cipher, byte[] key) {

        byte[][] state = arrayToMatrix(cipher);

        // Preround
        addRoundKey(state);

        // Crypt Rounds
        for (int i = 0; i < 14; i++) {
            subBytes(state);
            shiftRows(state);
            mixColumns(state);
            addRoundKey(state);
        }

        // Endround
        subBytes(state);
        shiftRows(state);
        addRoundKey(state);

        return matrixToArray(state);
    }

    // Help Functions
    // Converts the given byte array to a 4 by 4 matrix by column
    private static byte[][] arrayToMatrix(byte[] array) {
        byte[][] matrix = new byte[4][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                matrix[j][i] = array[i * 8 + j];
            }
        }
        return matrix;
    }

    // Converts the given matrix to the corresponding array (by columns)
    private static byte[] matrixToArray(byte[][] matrix) {
        byte[] array = new byte[16];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                array[i * 8 + j] = matrix[j][i];
            }
        }
        return array;
    }

    @Override
    public byte[] decrypt(byte[] cipher, byte[] key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public byte[] encrypt(byte[] cipher, byte[] key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
