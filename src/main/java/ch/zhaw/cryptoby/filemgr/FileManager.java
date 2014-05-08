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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author Toby
 */
public class FileManager {

    public static byte[] getBytesFromFile(String filePath) {
        File file = new File(filePath);

        byte[] output = new byte[(int) file.length()];
        try {
            InputStream input = null;
            try {
                int totalBytesRead = 0;
                input = new BufferedInputStream(new FileInputStream(file));
                while (totalBytesRead < output.length) {
                    int bytesRemaining = output.length - totalBytesRead;
                    int bytesRead = input.read(output, totalBytesRead, bytesRemaining);
                    if (bytesRead > 0) {
                        totalBytesRead = totalBytesRead + bytesRead;
                    }
                }
            } finally {
                input.close();
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not found.");
        } catch (IOException ioe) {
            System.out.println("IOException : " + ioe);
        }
        return output;
    }

    public static void putBytesToFile(String filePath, byte[] exportByte) {
        File outputFile = new File(filePath);
        if (!outputFile.exists()) {
            try {
                outputFile.createNewFile();
            } catch (IOException ioe) {
                System.out.println("IOException : " + ioe);
            }
        }
        try {
            try (OutputStream outStream = new BufferedOutputStream(new FileOutputStream(filePath))) {
                outStream.write(exportByte);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("FileNotFoundException : " + ex);
        } catch (IOException ioe) {
            System.out.println("IOException : " + ioe);
        }
    }

    public static byte[] getKeyFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString().getBytes();
        } catch (FileNotFoundException ex) {
            System.out.println("File Not Found.");
        } catch (IOException ex) {
            System.out.println("Error Reading The File.");
        }
        return null;
    }

    public static void putKeyToFile(String filePath, String key) {
        FileWriter fileWriter;
        try {
            File newTextFile = new File(filePath);
            fileWriter = new FileWriter(newTextFile);
            fileWriter.write(key);
            fileWriter.close();
        } catch (FileNotFoundException ex) {
            System.out.println("FileNotFoundException : " + ex);
        } catch (IOException ioe) {
            System.out.println("IOException : " + ioe);
        }
    }

}
