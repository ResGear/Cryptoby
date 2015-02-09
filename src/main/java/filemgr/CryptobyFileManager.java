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
package filemgr;

import helper.CryptobyHelper;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class provides file loading and saving from and to disk.
 * In special there are methods for key file loading saving.
 *
 * @author Tobias Rees
 */
public class CryptobyFileManager {

    /**
     * This method load plain or encrypted file to byte array from disk
     *
     * @param filePath The Path to file which bytes will be loaded
     * @return Byte array of file from filePath
     * @throws IOException If file not found or other IO problems there will be
     * throw an IOException
     */
    public static byte[] getBytesFromFile(String filePath) throws IOException {
        File file = new File(filePath);
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        int numByte = bis.available();
        byte[] output = new byte[numByte];
        bis.read(output, 0, numByte);
        return output;
    }

    /**
     * This method save plain or encrypted byte array to file on disk
     *
     * @param filePath The Path to file which will be saved to disk
     * @param exportByte The byte array will be saved to disk
     * @throws IOException If file couldn't save or other IO problems there will
     * be throw an IOException
     */
    public static void putBytesToFile(String filePath, byte[] exportByte) throws IOException {
        File outputFile = new File(filePath);
        if (!outputFile.exists()) {
            outputFile.createNewFile();
        }
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            outputStream.write(exportByte);
            outputStream.close();
        }
    }

    /**
     * This method load a key file from disk to byte array. They key in the file
     * must be in Hex String Block format which will be merged and converted
     * from hex String to byte array as output.
     *
     * @param filePath Path to key file which bytes will be loaded
     * @return Byte array of hex string in filePath
     * @throws IOException If file not found or other IO problems there will be
     * throw an IOException
     */
    public static byte[] getKeyFromFile(String filePath) throws IOException {
        StringBuilder sb;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
        }
        return CryptobyHelper.hexStringToBytes(sb.toString());
    }

    /**
     * This method save a key file from byte array to disk.
     *
     * @param filePath Path to file where key file will be saved to disk
     * @param key Key String in Hex format which will be converted to Block and
     * saved to disk in filePath
     * @throws IOException If file couldn't save or other IO problems there will
     * be throw an IOException
     */
    public static void putKeyToFile(String filePath, String key) throws IOException {

        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(filePath))) {
            fileWriter.write(CryptobyHelper.charToBlockString(key.toCharArray()));
            fileWriter.close();
        }
    }

}
