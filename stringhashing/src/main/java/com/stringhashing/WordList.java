package com.stringhashing;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;
import com.opencsv.CSVWriter;

/**
 * Extension of the ArrayList<Word> class that adds additional methods to help
 * create and explore the data surrounding the hash values.
 * 
 * @author Lander Duncan
 * @version
 */
public class WordList extends ArrayList<Word> {
    private int maxHashes;

    /**
     * Default constructor for empty list.
     */
    public WordList() {
        super();
        maxHashes = 0;
    }

    /**
     * Constructor that accepts a file with line-seperated words to add to the list.
     * 
     * @param f                 the file containing line-seperated strings.
     * @param valuesToCalculate the amount of hash values to calculate for each
     *                          word.
     * @throws IllegalArgumentException if the file is not correct or if a negative
     *                                  int is passed.
     */
    public WordList(File f, int valuesToCalculate) {
        if (valuesToCalculate < 0) {
            throw new IllegalArgumentException("The value to calculate cannot be negative.");
        }
        maxHashes = valuesToCalculate;
        Scanner wordScanner = null;
        try {
            wordScanner = new Scanner(f);
            while (wordScanner.hasNextLine()) {
                this.add(new Word(wordScanner.nextLine(), valuesToCalculate));
            }
            wordScanner.close();
        } catch (Exception e) {
            throw new IllegalArgumentException("File has thrown an error.");
        }

    }

    @Override
    public boolean add(Word w) {
        if (w.size() > maxHashes) {
            maxHashes = w.size();
        }
        return super.add(w);
    }

    /**
     * Returns the amount of collisions along a specified iValue after the modulus
     * operation is complete.
     * 
     * @param iValue   the hash value to check.
     * @param modValue this will affect the count. hashValue % modvalue =
     *                 collisionIndex.
     * @return the amount of collisions.
     * @throws IllegalArgumentException if the index is out of bounds or if the mod
     *                                  value is less than one.
     */
    public int collisionCount(int iValue, int modValue) {
        if (iValue < 0 || iValue > this.maxHashes) {
            throw new IllegalArgumentException("Index must be within bounds.");
        }
        if (modValue < 1) {
            throw new IllegalArgumentException("Mod value must be at least one.");
        }
        Set<Integer> claimedValues = new HashSet<Integer>();
        int count = 0;
        for (Word w : this) {
            Integer value = w.getHash(iValue) % modValue;
            if (claimedValues.contains(value)) {
                count++;
            } else {
                claimedValues.add(value);
            }
        }
        return count;
    }

    /**
     * Calculates the average time it took to calculate a given hash among all
     * words.
     * 
     * @param iValue the hash value to lookup.
     * @return long representing the average time.
     * @throws IllegalArgumentException if the index is out of bounds.
     */
    public long averageTime(int iValue) {
        if (iValue < 0 || iValue > this.maxHashes) {
            throw new IllegalArgumentException("Index must be within bounds.");
        }

        BigInteger sum = BigInteger.ZERO;
        for (int i = 0; i < this.size(); i++) {
            sum = sum.add(BigInteger.valueOf(this.get(i).getTime(iValue)));
        }

        BigInteger average = sum.divide(BigInteger.valueOf(this.size()));
        return average.longValue();
    }

    /**
     * Creates an array containing all the times along that specific hash.
     * 
     * @param iValue the hash value to lookup.
     * @return long array containing all the times.
     * @throws IllegalArgumentException if the arguments are out of bounds.
     */
    public long[] getTimeArray(int iValue) {
        if (iValue < 0 || iValue > this.size()) {
            throw new IllegalArgumentException("Index must be within bounds.");
        }
        long[] toReturn = new long[this.size()];
        for (int i = 0; i < this.size(); i++) {
            toReturn[i] = this.get(i).getTime(iValue);
        }
        return toReturn;
    }

    /**
     * Creates an array containing all the values of a specified hash.
     * 
     * @param iValue the hash value to lookup.
     * @return int array containing all the values.
     * @throws IllegalArgumentException if the arguments are out of bounds.
     */
    public int[] getHashArray(int iValue) {
        if (iValue < 0 || iValue > this.size()) {
            throw new IllegalArgumentException("Index must be within bounds.");
        }
        int[] toReturn = new int[this.size()];
        for (int i = 0; i < this.size(); i++) {
            toReturn[i] = this.get(i).getHash(iValue);
        }
        return toReturn;
    }

    /**
     * Exports the wordList to a csv file.
     */
    public void exportHashesToCSV() {
        String csvFileName = "hashvalues.csv";

        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFileName))) {
            // Define data to be written to the CSV file
            String[] header = new String[maxHashes + 1];
            header[0] = "Word";
            for (int i = 0; i < maxHashes; i++) {
                header[i + 1] = String.valueOf(i);
            }

            writer.writeNext(header);

            for (int i = 0; i < this.size(); i++) {
                String[] data = new String[this.get(i).size() + 1];
                data[0] = this.get(i).getWord();
                int[] hashValues = this.get(i).getHashValues();
                for (int k = 1; k < hashValues.length + 1; k++) {
                    data[k] = String.valueOf(hashValues[k - 1]);
                }
                writer.writeNext(data);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportAverageTimeToCSV() {
        String csvFileName = "averagetime.csv";

        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFileName))) {
            String[] headers = { "Index", "Time" };
            writer.writeNext(headers);
            for (int i = 0; i < maxHashes; i++) {
                String[] row = new String[2];
                row[0] = String.valueOf(i);
                row[1] = String.valueOf(averageTime(i));
                writer.writeNext(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportTrueHashCollisionsToCSV(double loadFactor) {
        String csvFileName = "collisions.csv";

        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFileName))) {
            double modValue = ((double) this.size()) / loadFactor;
            String[] headers = { "Index", "Collisions" };
            writer.writeNext(headers);
            for (int i = 0; i < maxHashes; i++) {
                String[] row = new String[2];
                row[0] = String.valueOf(i);
                row[1] = String.valueOf(collisionCount(i, (int) modValue));
                writer.writeNext(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
