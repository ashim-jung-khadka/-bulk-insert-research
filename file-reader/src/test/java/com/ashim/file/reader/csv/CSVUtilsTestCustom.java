package com.ashim.file.reader.csv;

import org.hamcrest.core.IsNull;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author ashimjk on 11/28/2018
 */
public class CSVUtilsTestCustom {

    @Test
    public void test_custom_separator() {

        String line = "10|AU|Australia";
        List<String> result = CSVUtils.parseLine(line, '|');

        assertThat(result, IsNull.notNullValue());
        assertThat(result.size(), is(3));
        assertThat(result.get(0), is("10"));
        assertThat(result.get(1), is("AU"));
        assertThat(result.get(2), is("Australia"));

    }

    @Test
    public void test_custom_separator_and_quote() {

        String line = "'10'|'AU'|'Australia'";
        List<String> result = CSVUtils.parseLine(line, '|', '\'');

        assertThat(result, IsNull.notNullValue());
        assertThat(result.size(), is(3));
        assertThat(result.get(0), is("10"));
        assertThat(result.get(1), is("AU"));
        assertThat(result.get(2), is("Australia"));

    }

    @Test
    public void test_custom_separator_and_quote_but_custom_quote_in_column() {

        String line = "'10'|'AU'|'Aus|tralia'";
        List<String> result = CSVUtils.parseLine(line, '|', '\'');

        assertThat(result, IsNull.notNullValue());
        assertThat(result.size(), is(3));
        assertThat(result.get(0), is("10"));
        assertThat(result.get(1), is("AU"));
        assertThat(result.get(2), is("Aus|tralia"));

    }

    @Test
    public void test_custom_separator_and_quote_but_double_quotes_in_column() {

        String line = "'10'|'AU'|'Aus\"\"tralia'";
        List<String> result = CSVUtils.parseLine(line, '|', '\'');

        assertThat(result, IsNull.notNullValue());
        assertThat(result.size(), is(3));
        assertThat(result.get(0), is("10"));
        assertThat(result.get(1), is("AU"));
        assertThat(result.get(2), is("Aus\"tralia"));

    }

}