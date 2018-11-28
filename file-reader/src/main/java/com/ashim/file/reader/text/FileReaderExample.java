package com.ashim.file.reader.text;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author ashimjk on 11/26/2018
 */
public class FileReaderExample {

    protected static ArrayList<String> generateArrayListFromFile(String filename) throws IOException {

        ArrayList<String> result = new ArrayList<>();

        try (FileReader f = new FileReader(filename)) {
            StringBuffer sb = new StringBuffer();
            while (f.ready()) {
                char c = (char) f.read();
                if (c == '\n') {
                    result.add(sb.toString());
                    sb = new StringBuffer();
                } else {
                    sb.append(c);
                }
            }
            if (sb.length() > 0) {
                result.add(sb.toString());
            }
        }

        return result;
    }
}
