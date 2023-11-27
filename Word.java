public class Word {
    private final String word;
    private int[] hashValues;
    private long[] timeToCalculate;

    /**
     * 
     * @param word
     * @param valuesToCalculate
     */
    public Word(String word, int valuesToCalculate) {
        if (word == null || word.equals("")) {
            throw new IllegalArgumentException("String argument must be non-empty and non-null.");
        }
        if (valuesToCalculate < 0) {
            throw new IllegalArgumentException("values to calculate must be non-negative.");
        }
        this.word = word;
        values(valuesToCalculate);
    }

    /**
     * 
     * @param max
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
     * 
     * @return
     */
    public String getWord() {
        return word;
    }

    /**
     * 
     * @param iValue
     * @return
     */
    public int getHash(int iValue) {
        if (iValue < 0 || iValue >= hashValues.length) {
            throw new IllegalArgumentException("iValue must be within the range of hashed values.");
        }
        return hashValues[iValue];
    }

    /**
     * 
     * @param iValue
     * @return
     */
    public long getTime(int iValue) {
        if (iValue < 0 || iValue >= timeToCalculate.length) {
            throw new IllegalArgumentException("iValue must be within the range of hashed values.");
        }
        return timeToCalculate[iValue];
    }
}