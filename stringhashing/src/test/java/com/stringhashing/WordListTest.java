package com.stringhashing;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

/**
 * Unit test for the WordList class.
 */
public class WordListTest 
{

    @Test
    public void exportToCSV()
    {
        File f = new File("C:\\Users\\Lande\\OneDrive\\Desktop\\VSC Projects\\Why31\\stringhashing\\src\\main\\java\\com\\stringhashing\\google-10000-english.txt");
        WordList li = new WordList(f,10000);
        li.exportAverageTimeToCSV();
        li.exportTrueHashCollisionsToCSV(.75);
    }
}
