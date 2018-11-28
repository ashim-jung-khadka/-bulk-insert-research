package com.ashim.file.reader.csv;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * @author ashimjk on 11/28/2018
 */
public class PerformaceTest {

    private static String CSV_FILE = "src/test/resources/csv/performance/fxdeal_1M.csv";
    private static Runtime RUNTIME = Runtime.getRuntime();
    private static int MB = 1024 * 1024;
    private static long usedMemory = 0;

    @Test
    public void test_csv_performance() throws IOException {
        this.test_using_scanner();
        this.test_using_buffer_reader();
        this.test_using_open_csv1();
        this.test_using_open_csv2();
        this.test_using_open_csv_bean_builder();
    }

    private void test_using_scanner() throws FileNotFoundException {
        this.setMemoryStatus();

        int count = 0;
        Scanner scanner = new Scanner(new File(CSV_FILE));
        long startTime = System.currentTimeMillis();
        while (scanner.hasNext()) {
            count++;
            if (count > 1) {
                CSVUtils.parseLine(scanner.nextLine());
                System.out.print("");
            }
        }
        scanner.close();
        System.out.println("test_using_scanner-> time taken : " + (System.currentTimeMillis() - startTime));

        this.printMemoryStatus();
        System.out.println();
    }

    private void test_using_buffer_reader() {
        this.setMemoryStatus();

        String line = "";
        int count = 0;
        long startTime = System.currentTimeMillis();
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {

            while ((line = br.readLine()) != null) {
                count++;
                if (count > 1) {
                    CSVUtils.parseLine(line);
                    System.out.print("");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("test_using_buffer_reader-> time taken : " + (System.currentTimeMillis() - startTime));

        this.printMemoryStatus();
        System.out.println();
    }

    private void test_using_open_csv1() throws IOException {
        this.setMemoryStatus();

        long startTime = System.currentTimeMillis();
        CSVReader reader = new CSVReader(new FileReader(CSV_FILE));
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            // nothing to implement
            System.out.print("");
        }
        System.out.println("test_using_open_csv1-> time taken : " + (System.currentTimeMillis() - startTime));

        this.printMemoryStatus();
        System.out.println();
    }

    private void test_using_open_csv2() throws IOException {
        this.setMemoryStatus();

        long startTime = System.currentTimeMillis();
        CSVReader reader = new CSVReader(new FileReader(CSV_FILE));
        for (String[] nextLine : reader) {
            // nothing to implement
            System.out.print("");
        }
        System.out.println("test_using_open_csv2-> time taken : " + (System.currentTimeMillis() - startTime));

        this.printMemoryStatus();
        System.out.println();
    }

    private void test_using_open_csv_bean_builder() throws FileNotFoundException {
        this.setMemoryStatus();

        long startTime = System.currentTimeMillis();
        List<FxDeal> fxDeals = new CsvToBeanBuilder<FxDeal>(new FileReader(CSV_FILE)).withType(FxDeal.class).build()
                .parse();
        System.out.println("test_using_open_csv2-> time taken : " + (System.currentTimeMillis() - startTime));

        this.printMemoryStatus();
        System.out.println();
    }

    private void setMemoryStatus() {
        this.usedMemory = (RUNTIME.totalMemory() - RUNTIME.freeMemory()) / MB;
    }

    private void printMemoryStatus() {
        usedMemory = ((RUNTIME.totalMemory() - RUNTIME.freeMemory()) / MB) - usedMemory;
        System.out.println("Used Memory: " + usedMemory + "MB");
    }

}
