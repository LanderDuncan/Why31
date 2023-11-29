import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class WordList extends ArrayList<Word> {

    public WordList() {
        super();
    }

    public WordList(File f, int valuesToCalculate) {
        Scanner wordScanner = null;
        try {
            wordScanner = new Scanner(f);
        } catch (Exception e) {

        }
        while (wordScanner.hasNextLine()) {
            this.add(new Word(wordScanner.nextLine(), valuesToCalculate));
        }
    }

    public int getCollisions(int iValue, int modValue) {
        return -1;
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
