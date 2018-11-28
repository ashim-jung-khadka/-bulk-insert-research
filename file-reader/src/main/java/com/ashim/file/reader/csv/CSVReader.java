package com.ashim.file.reader.csv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author ashimjk on 11/28/2018
 */
public class CSVReader {

    public static void main(String[] args) {

        String csvFile = "src/test/resources/csv/country.csv";
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] country = line.split(cvsSplitBy);

                System.out.println("Country [code= " + country[0] + " , name=" + country[1] + "]");

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}