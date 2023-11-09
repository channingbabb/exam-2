package ChanningBabb_FileProcessing;
import java.util.ArrayList;
import java.util.List;

public class Sentence extends LitText {
    private ArrayList<Word> words;

    public Sentence(String text) {
        words = new ArrayList<>(); // initialize words
        String[] wordArray = text.split(" "); // split text into words
        for (String word : wordArray) {
            words.add(new Word(word)); // add each word to words
        }
    }

    public List<Word> getWords() {
        return words;
    }
}
