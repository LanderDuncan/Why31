import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;

/**
 * 
 */
public class WordList extends ArrayList<Word> {

    /**
     * 
     */
    public WordList() {
        super();
    }

    /**
     * 
     * @param f
     * @param valuesToCalculate
     */
    public WordList(File f, int valuesToCalculate) {
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
     * 
     * @param iValue
     * @param modValue
     * @return
     */
    public int CollisionCount(int iValue, int modValue) {
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

    public long getAverageTime(int iValue) {
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

    private long averageTimeOverflow(long cur, int startingIndex, int iValue) {
        // BigInteger sum = new BigInteger(cur);
        // for(int i = startingIndex; i < this.size(); i++){
        // sum += this.get(i).getTime(iValue);
        // }
        // return sum / this.size();
        return -1;
    }

    public long[] getTimeArray(int iValue) {
        long[] toReturn = new long[this.size()];
        for (int i = 0; i < this.size(); i++) {
            toReturn[i] = this.get(i).getTime(iValue);
        }
        return toReturn;
    }

    public int[] getHashArray(int iValue) {
        int[] toReturn = new int[this.size()];
        for (int i = 0; i < this.size(); i++) {
            toReturn[i] = this.get(i).getHash(iValue);
        }
        return toReturn;
    }

    public void exportToCSV() {

    }
}
