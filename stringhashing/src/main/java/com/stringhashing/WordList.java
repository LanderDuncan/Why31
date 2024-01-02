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
 * @version 1/2/2024
 */
public class WordList extends ArrayList<Word> {
    private int maxHashes;

    /**
     * Default constructor for an empty list.
     */
    public WordList() {
        super();
        maxHashes = 0;
    }

    /**
     * Constructor that accepts a file with line-separated words to add to the list.
     * 
     * @param f                the file containing line-separated strings.
     * @param basesToCalculate the number of bases to calculate for each
     *                         word.
     * @throws IllegalArgumentException if the file is not correct or if a negative
     *                                  int is passed.
     */
    public WordList(File f, int basesToCalculate) throws IllegalArgumentException {
        if (basesToCalculate < 0) {
            throw new IllegalArgumentException("The value to calculate cannot be negative.");
        }
        maxHashes = basesToCalculate;
        Scanner wordScanner = null;
        try {
            wordScanner = new Scanner(f);
            while (wordScanner.hasNextLine()) {
                this.add(new Word(wordScanner.nextLine(), basesToCalculate));
            }
            wordScanner.close();
        } catch (Exception e) {
            throw new IllegalArgumentException("File has thrown an error.");
        }

    }

    /**
     * Appends the specified element to the end of this list and keeps track of the
     * largest word for use in csv exporting.
     * 
     * @param w word to be appended to this list
     * 
     * @return true (as specified by Collection.add)
     */
    @Override
    public boolean add(Word w) {
        if (w == null) {
            throw new IllegalArgumentException("Word must not be null");
        }
        if (w.size() > maxHashes) {
            maxHashes = w.size();
        }
        return super.add(w);
    }

    /**
     * Returns the number of collisions along a specified base after the modulus
     * operation is complete.
     * 
     * @param base     the hash value to check.
     * @param capacity the capacity of the simulated Hash Table. hashValue %
     *                 capacity =
     *                 collisionIndex.
     * @return the number of collisions.
     * @throws IllegalArgumentException if the index is out of bounds or if the mod
     *                                  value is less than one.
     */
    public int collisionCount(int base, int capacity) throws IllegalArgumentException {
        if (base < 0 || base >= this.maxHashes) {
            throw new IllegalArgumentException("Index must be within bounds.");
        }
        if (capacity < 1) {
            throw new IllegalArgumentException("Mod value must be at least one.");
        }
        Set<Integer> claimedValues = new HashSet<Integer>();
        int count = 0;
        for (Word w : this) {
            Integer value = w.getHash(base) % capacity;
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
     * words in the list.
     * 
     * @param base the hash value to lookup.
     * @return long representing the average time.
     * @throws IllegalArgumentException if the index is out of bounds.
     */
    public long averageTime(int base) throws IllegalArgumentException {
        if (base < 0 || base >= this.maxHashes) {
            throw new IllegalArgumentException("Index must be within bounds.");
        }

        BigInteger sum = BigInteger.ZERO;
        for (int i = 0; i < this.size(); i++) {
            sum = sum.add(BigInteger.valueOf(this.get(i).getTime(base)));
        }

        BigInteger average = sum.divide(BigInteger.valueOf(this.size()));
        return average.longValue();
    }

    /**
     * Creates an array containing all the times along that specific hash.
     * 
     * @param base the hash value to lookup.
     * @return long array containing all the times.
     * @throws IllegalArgumentException if the argument is out of bounds.
     */
    public long[] getTimeArray(int base) throws IllegalArgumentException {
        if (base < 0 || base >= this.maxHashes) {
            throw new IllegalArgumentException("Index must be within bounds.");
        }
        long[] toReturn = new long[this.size()];
        for (int i = 0; i < this.size(); i++) {
            toReturn[i] = this.get(i).getTime(base);
        }
        return toReturn;
    }

    /**
     * Creates an array containing all the values of a specified hash.
     * 
     * @param base the hash value to lookup.
     * @return int array containing all the values.
     * @throws IllegalArgumentException if the argument is out of bounds.
     */
    public int[] getHashArray(int base) throws IllegalArgumentException {
        if (base < 0 || base >= this.maxHashes) {
            throw new IllegalArgumentException("Index must be within bounds.");
        }
        int[] toReturn = new int[this.size()];
        for (int i = 0; i < this.size(); i++) {
            toReturn[i] = this.get(i).getHash(base);
        }
        return toReturn;
    }

    /**
     * Exports hash values to a CSV file named "hashvalues.csv".
     * Each row contains a word and its corresponding hash values.
     *
     * @throws IOException if an I/O error occurs while writing to the file.
     */
    public void exportHashesToCSV() throws IOException {
        String csvFileName = "hashvalues.csv";

        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFileName))) {
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
            throw new IOException("Error occurred while writing to the CSV file.", e);
        }
    }

    /**
     * Exports time values to a CSV file named "averagetime.csv".
     * Each row contains a base and its corresponding time values.
     *
     * @throws IOException if an I/O error occurs while writing to the file.
     */
    public void exportAverageTimeToCSV() throws IOException {
        String csvFileName = "averagetime.csv";

        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFileName))) {
            String[] headers = { "Base", "Time" };
            writer.writeNext(headers);
            for (int i = 0; i < maxHashes; i++) {
                String[] row = new String[2];
                row[0] = String.valueOf(i);
                row[1] = String.valueOf(averageTime(i));
                writer.writeNext(row);
            }
        } catch (IOException e) {
            throw new IOException("Error occurred while writing to the CSV file.", e);

        }
    }

    /**
     * Exports collision count values to a CSV file named "collisions.csv".
     * Each row contains a base and its corresponding collision count for a given
     * loadFactor.
     *
     * @param loadFactor the loadFactor of the hashMap to calculate collisions for.
     *                   Must be greater than 0 and at most 1.
     * @throws IOException              if an I/O error occurs while writing to the
     *                                  file.
     * @throws IllegalArgumentException if the loadFactor is out of bounds.
     */
    public void exportTrueHashCollisionsToCSV(double loadFactor) throws IOException, IllegalArgumentException {
        if (loadFactor <= 0 || loadFactor > 1) {
            throw new IllegalArgumentException("LoadFactor must be greater than 0 and at most 1.");
        }
        String csvFileName = "collisions.csv";

        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFileName))) {
            double modValue = ((double) this.size()) / loadFactor;
            String[] headers = { "Base", "Collisions" };
            writer.writeNext(headers);
            for (int i = 0; i < maxHashes; i++) {
                String[] row = new String[2];
                row[0] = String.valueOf(i);
                row[1] = String.valueOf(collisionCount(i, (int) modValue));
                writer.writeNext(row);
            }
        } catch (IOException e) {
            throw new IOException("Error occurred while writing to the CSV file.", e);
        }
    }
}
