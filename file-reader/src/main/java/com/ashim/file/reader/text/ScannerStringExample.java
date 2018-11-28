package com.ashim.file.reader.text;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author ashimjk on 11/26/2018
 */
public class ScannerStringExample {

    protected static ArrayList<String> generateArrayListFromFile(String filename) throws IOException {

        ArrayList<String> result = new ArrayList<>();

        try (Scanner s = new Scanner(new FileReader(filename))) {

            while (s.hasNext()) {
                result.add(s.nextLine());
            }
            return result;
        }

    }

}
