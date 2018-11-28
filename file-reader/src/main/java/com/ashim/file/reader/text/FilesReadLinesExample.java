package com.ashim.file.reader.text;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ashimjk on 11/26/2018
 */
public class FilesReadLinesExample {

    protected static ArrayList<String> generateArrayListFromFile(String filename) throws IOException {

        List<String> result = Files.readAllLines(Paths.get(filename));

        return (ArrayList<String>) result;
    }

}
