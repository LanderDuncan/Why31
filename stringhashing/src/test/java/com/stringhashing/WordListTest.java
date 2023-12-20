package com.stringhashing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

/**
 * Unit test for the WordList class.
 */
public class WordListTest {
    private WordList wl;

    @BeforeEach
    public void setUp() {
        wl = new WordList();
    }

    @Test
    public void testConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new WordList(null, 10));
    }

    @Test
    public void testAdd() {
        assertThrows(IllegalArgumentException.class, () -> wl.add(null));
        wl.add(new Word("hello", 5));

    }

    @Test
    public void testCollisionCount() {
        assertThrows(IllegalArgumentException.class, () -> wl.collisionCount(0, 1));
        wl.add(new Word("Hello", 5));
        assertTrue(wl.collisionCount(0, 1) == 0);
        wl.add(new Word("Howdy!", 5));
        assertTrue(wl.collisionCount(0, 1) == 1);
        assertTrue(wl.collisionCount(0, 5) == 0);

    }

    @Test
    public void testAverageTime() {
        assertThrows(IllegalArgumentException.class, () -> wl.averageTime(0));
        wl.add(new Word("Hello", 1));
        wl.add(new Word("Howdy", 1));
        assertThrows(IllegalArgumentException.class, () -> wl.averageTime(1));
        assertThrows(IllegalArgumentException.class, () -> wl.averageTime(-1));
        long num = wl.averageTime(0);
        assertTrue(num >= 0);
    }

    @Test
    public void testGetTimeArray() {
        assertThrows(IllegalArgumentException.class, () -> wl.getTimeArray(0));
        wl.add(new Word("Hello", 1));
        wl.add(new Word("Howdy", 1));
        assertThrows(IllegalArgumentException.class, () -> wl.getTimeArray(1));
        assertThrows(IllegalArgumentException.class, () -> wl.getTimeArray(-1));
        long[] arr = wl.getTimeArray(0);
        assertTrue(arr.length == 2);
    }

    @Test
    public void testGetHashArray() {
        assertThrows(IllegalArgumentException.class, () -> wl.getHashArray(0));
        wl.add(new Word("Hello", 1));
        wl.add(new Word("Howdy", 1));
        assertThrows(IllegalArgumentException.class, () -> wl.getHashArray(1));
        assertThrows(IllegalArgumentException.class, () -> wl.getHashArray(-1));
        int[] arr = wl.getHashArray(0);
        assertTrue(arr.length == 2);
    }

    @Test
    public void testExportTrueHashCollisionsToCSV() {
        assertThrows(IllegalArgumentException.class, () -> wl.exportTrueHashCollisionsToCSV(0));
        assertThrows(IllegalArgumentException.class, () -> wl.exportTrueHashCollisionsToCSV(2));

    }
}
