package ChanningBabb_FileProcessing;

import java.util.ArrayList;
import java.util.List;

public class Word extends LitText {
    private ArrayList<Character> letters;

    public Word(String text) {
        letters = new ArrayList<>(); // initialize letters
        for (char c : text.toCharArray()) { // for each character in text
            letters.add(c); // add the character to letters
        }
    }

    public List<Character> getLetters() {
        return letters;
    }
}
