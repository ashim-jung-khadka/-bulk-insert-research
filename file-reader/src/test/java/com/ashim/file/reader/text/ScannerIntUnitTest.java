package com.ashim.file.reader.text;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * @author ashimjk on 11/26/2018
 */
public class ScannerIntUnitTest {

    protected static final String NUMBER_FILENAME = "src/test/resources/sampleNumberFile.txt";

    @Test
    public void whenParsingExistingTextFile_thenGetIntArrayList() throws IOException {
        List<Integer> numbers = ScannerIntExample.generateArrayListFromFile(NUMBER_FILENAME);
        assertTrue("File does not has 2 lines", numbers.size() == 2);
    }
}
