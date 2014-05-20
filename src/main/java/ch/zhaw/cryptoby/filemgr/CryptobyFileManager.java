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
package ch.zhaw.cryptoby.filemgr;

import ch.zhaw.cryptoby.helper.CryptobyHelper;
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
 *
 * @author Toby
 */
public class CryptobyFileManager {

    public static byte[] getBytesFromFile(String filePath) throws IOException {
        File file = new File(filePath);
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        int numByte = bis.available();
        byte[] output = new byte[numByte];
        bis.read(output, 0, numByte);
        return output;
    }

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

    public static void putKeyToFile(String filePath, String key) throws IOException {

        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(filePath))) {
            fileWriter.write(CryptobyHelper.charToBlockString(key.toCharArray()));
            fileWriter.close();
        }
    }

}
