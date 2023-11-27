public class WordTest {
    
    public static void main(String[] args) {
        Word w = new Word("Hello", 32);
        String str = new String("Hello");
        System.out.println(w.getWord().hashCode());
        System.out.println(str.hashCode());
    }
}
