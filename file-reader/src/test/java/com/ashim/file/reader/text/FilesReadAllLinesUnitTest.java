package com.ashim.file.reader.text;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * @author ashimjk on 11/26/2018
 */
public class FilesReadAllLinesUnitTest {

    protected static final String TEXT_FILENAME = "src/test/resources/sampleTextFile.txt";

    @Test
    public void whenParsingExistingTextFile_thenGetArrayList() throws IOException {
        List<String> lines = FilesReadLinesExample.generateArrayListFromFile(TEXT_FILENAME);
        assertTrue("File does not has 2 lines", lines.size() == 2);
    }
}
