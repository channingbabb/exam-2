package ChanningBabb_FileProcessing;

import java.util.ArrayList;
import java.util.List;

public class Word extends LitText {
    private ArrayList<Character> letters;

    public Word(String text) {
        letters = new ArrayList<>();
        for (char c : text.toCharArray()) {
            letters.add(c);
        }
    }

    public List<Character> getLetters() {
        return letters;
    }
}
