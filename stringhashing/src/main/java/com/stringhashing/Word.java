package com.stringhashing;

/**
 * Stores a word and several hash values along with the calculation time for
 * each.
 * 
 * @author Lander Duncan
 * @version 1/2/2024
 */
public class Word {
    private final String word;
    private int[] hashValues;
    private long[] timeToCalculate;

    /**
     * Constructor for the Word class.
     * 
     * @param word             the string to be hashed.
     * @param basesToCalculate the number of hash values to calculate. Begins at
     *                         zero.
     * @throws IllegalArgumentException if the word is null or empty or when the
     *                                  valuesToCalculate field is negative.
     */
    public Word(String word, int basesToCalculate) throws IllegalArgumentException {
        if (word == null || word.equals("")) {
            throw new IllegalArgumentException("String argument must be non-empty and non-null.");
        }
        if (basesToCalculate < 0) {
            throw new IllegalArgumentException("values to calculate must be non-negative.");
        }
        this.word = word;
        values(basesToCalculate);
    }

    /**
     * Helper method for the constructor that populates the hash values and
     * calculation time arrays.
     * 
     * @param max the number of values to calculate.
     */
    private void values(int max) {
        hashValues = new int[max];
        timeToCalculate = new long[max];
        byte[] by = word.getBytes();
        for (int i = 0; i < max; i++) {
            long startTime = System.nanoTime();
            int h = 0;
            for (byte v : by) {
                h = i * h + (v & 0xff);
            }
            hashValues[i] = h;
            long endTime = System.nanoTime();
            long duration = (endTime - startTime);
            timeToCalculate[i] = duration;
        }
    }

    /**
     * Getter for the word attribute.
     * 
     * @return String representing the word passed to the constructor.
     */
    public String getWord() {
        return word;
    }

    /**
     * Getter method for the number of stored hashes.
     * 
     * @return int representing the number of stored hashes.
     */
    public int size() {
        return hashValues.length;
    }

    /**
     * Getter method for the hash values.
     * 
     * @param base the value of the hash to return. Begins at zero.
     * @return int hash value.
     * @throws IllegalArgumentException if the index is outside of the range.
     */
    public int getHash(int base) throws IllegalArgumentException {
        if (base < 0 || base >= hashValues.length) {
            throw new IllegalArgumentException("Base must be within the range of hashed values.");
        }
        return hashValues[base];
    }

    /**
     * Getter method for the time values.
     * 
     * @param base int representing the value of the hash to get the time for.
     *             Begins at zero.
     * @return long time to calculate
     * @throws IllegalArgumentException if the index is outside of the range.
     */
    public long getTime(int base) throws IllegalArgumentException {
        if (base < 0 || base >= timeToCalculate.length) {
            throw new IllegalArgumentException("Base must be within the range of hashed values.");
        }
        return timeToCalculate[base];
    }

    /**
     * Getter method for the hash values array.
     * 
     * @return int array representing the hash values at each index.
     */
    public int[] getHashValues() {
        return hashValues;
    }

    /**
     * Getter method for the time values array.
     * 
     * @return long array representing the time it took to calculate each hash
     *         value.
     */
    public long[] getTimeToCalculate() {
        return timeToCalculate;
    }
}