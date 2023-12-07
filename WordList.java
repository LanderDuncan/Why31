import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;

/**
 * Extension of the ArrayList<Word> class that adds additional methods to help
 * create and explore the data surrounding the hash values.
 * 
 * @author Lander Duncan
 * @version
 */
public class WordList extends ArrayList<Word> {

    /**
     * Default constructor for empty list.
     */
    public WordList() {
        super();
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
        if (iValue < 0 || iValue > this.size()) {
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
        if (iValue < 0 || iValue > this.size()) {
            throw new IllegalArgumentException("Index must be within bounds.");
        }
        long sum = 0;
        for (int i = 0; i < this.size(); i++) {
            // TODO: Replace with a check that the next addition will not overflow the long.
            if (true) {
                sum += this.get(i).getTime(iValue);
            } else {
                return averageTimeOverflow(sum, i, iValue);
            }
        }
        return sum / this.size();
    }

    /**
     * Helper method for the averageTime method in the case of a long overflow.
     * 
     * @param cur           the current sum
     * @param startingIndex the next index to be added.
     * @param iValue        the hash value being averaged.
     * @return long representing the average time to calculate a hash value.
     * @throws IllegalArgumentException if the arguments are out of bounds.
     */
    private long averageTimeOverflow(long cur, int startingIndex, int iValue) {
        // TODO: Implement this
        // if invalid args throw invalid arg.
        // BigInteger sum = new BigInteger(cur);
        // for(int i = startingIndex; i < this.size(); i++){
        // sum += this.get(i).getTime(iValue);
        // }
        // return sum / this.size();
        return -1;
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
    public void exportToCSV() {

    }
}
