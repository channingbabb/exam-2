package ChanningBabb_FileProcessing;
import java.util.ArrayList;
import java.util.List;

public class Sentence extends LitText {
    private ArrayList<Word> words;

    public Sentence(String text) {
        words = new ArrayList<Word>();
        String[] wordArray = text.split(" ");
        for (String word : wordArray) {
            words.add(new Word(word));
        }
    }

    public List<Word> getWords() {
        return words;
    }
}
