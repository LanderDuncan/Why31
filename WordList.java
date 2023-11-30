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
        for(Word w : this){
            Integer value = w.getHash(iValue) % modValue;
            if(claimedValues.contains(value)){
                count++;
            }else {
                claimedValues.add(value);
            }
        }
        return count;
    }

    public long getAverageTime(int iValue) {
        return -1;
    }

    public long[] getTimeArray(int iValue) {
        return null;
    }

    public void exportToCSV() {

    }
}
