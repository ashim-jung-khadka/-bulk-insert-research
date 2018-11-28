package com.ashim.file.reader.text;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author ashimjk on 11/26/2018
 */
public class BufferedReaderExample {

    protected static ArrayList<String> generateArrayListFromFile(String filename) throws IOException {

        ArrayList<String> result = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            while (br.ready()) {
                result.add(br.readLine());
            }
            return result;
        }

    }

}
