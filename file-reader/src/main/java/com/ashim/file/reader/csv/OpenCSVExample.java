package com.ashim.file.reader.csv;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;

/**
 * @author ashimjk on 11/28/2018
 */
public class OpenCSVExample {

    public static void main(String[] args) {

        String csvFile = "src/test/resources/csv/country3.csv";

        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(csvFile));
            String[] line;
            while ((line = reader.readNext()) != null) {
                System.out.println("Country [id= " + line[0] + ", code= " + line[1] + " , name=" + line[2] + "]");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
