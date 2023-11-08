package ChanningBabb_FileProcessing;

import java.util.ArrayList;
import java.util.List;

public class Chapter extends LitText {
    private ArrayList<Sentence> sentences;
    private String name;
    
    public Chapter(String name, String text) {
        this.name = name;
        this.sentences = new ArrayList<>();
        String[] sentenceArray = text.replaceAll("[\n\r]", " ").split("\\.");
        for (String sentence : sentenceArray) {
            Sentence s = new Sentence(sentence);
            this.sentences.add(s);
        }
    }
    
    public List<Sentence> getSentences() {
        return this.sentences;
    }
    
    public String getName() {
        return this.name;
    }

    
    public int getNumSentences() {
        return this.sentences.size();
    }
    
    public double getAvgSentenceLength() {
        int totalWords = 0;
        for (Sentence s : this.sentences) {
            totalWords += s.getWords().size();
        }
        return (double) totalWords / this.sentences.size();
    }
    
    public double getAvgWordLength() {
        int totalChars = 0;
        int totalWords = 0;
        for (Sentence s : this.sentences) {
            for (Word w : s.getWords()) {
                totalChars += w.getLetters().size();
            }
            totalWords += s.getWords().size();
        }
        return (double) totalChars / totalWords;
    }
}
