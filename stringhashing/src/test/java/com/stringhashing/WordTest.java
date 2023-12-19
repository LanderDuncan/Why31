package com.stringhashing;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileWriter;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import com.opencsv.CSVWriter;

/**
 * Unit test for the WordList class.
 */
public class WordTest {
    private Word first;

    @BeforeEach
    public void setUp() {
        first = new Word("Hello", 5);
    }

    @Test
    public void testConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new Word(null, 0));
        assertThrows(IllegalArgumentException.class, () -> new Word("", 0));
        assertThrows(IllegalArgumentException.class, () -> new Word("Hello", -1));
        new Word("Hello", 0);
    }

    @Test
    public void getWord() {
        assertTrue(first.getWord().equals("Hello"));
    }

    @Test
    public void size() {
        assertTrue(first.size() == 5);
    }

    @Test
    public void getHash() {
        assertThrows(IllegalArgumentException.class, () -> first.getHash(-1));
        assertThrows(IllegalArgumentException.class, () -> first.getHash(6));
        assertTrue(first.getHash(0) == 111);

    }

    @Test
    public void getTime() {
        assertThrows(IllegalArgumentException.class, () -> first.getTime(-1));
        assertThrows(IllegalArgumentException.class, () -> first.getTime(6));
        assertTrue(first.getHash(0) > 0); // Time could vary depending on system
    }

    @Test
    public void getHashValues() {
        int[] hashes = first.getHashValues();
        int[] expected = { 111, 500, 2719, 9966, 27167 };
        assertArrayEquals(hashes, expected);

    }

    @Test
    public void getTimeToCalculate() {
        long[] time = first.getTimeToCalculate();
        assertTrue(time.length == 5);
        for (long l : time) {
            assertTrue(l > 0);
        }
    }
}
