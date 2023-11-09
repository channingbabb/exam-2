package ChanningBabb_FileProcessing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ChanningBabb_FileProcessing.GUI.HistogramGUI;

public class Main {
    public static void main(String[] args) {
        // Read the file into a string
        String wholeText = "";
        try {
            wholeText = Files.readString(Paths.get("src/ChanningBabb_FileProcessing/MobyDick.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //
        // The following code is from Dr. Baarsch
        //
        // Remove Gutenberg info
        String trimmedText = wholeText.substring(796, wholeText.length() - 18870).strip();

        // Remove title and table of contents
        trimmedText = trimmedText.substring(4560).strip();

        // Remove and store ETYMOLOGY and EXTRACTS sections
        String etymology = trimmedText.substring(0, 1390).strip();
        String extracts = trimmedText.substring(1391, 22090).strip();
        trimmedText = trimmedText.substring(22091).strip();

        //
        // End of Dr. Baarsch's code
        //

        // Split the remaining text into chapters
        String[] chapterStrings = trimmedText.split("(?=CHAPTER [\\dIVXLCM]+\\.)");
        ArrayList<Chapter> chapters = new ArrayList<>();
        for (String chapterString : chapterStrings) {
            String chapterTitle = chapterString.substring(0, chapterString.indexOf("\n")).strip();
            Chapter chapter = new Chapter(chapterTitle, chapterString);
            chapters.add(chapter);
        }

        // Split the remaining text into sentences
        String[] sentenceStrings = trimmedText.split("(?<=[.?!])\\s+");
        ArrayList<Sentence> sentences = new ArrayList<>();
        for (String sentenceString : sentenceStrings) {
            Sentence sentence = new Sentence(sentenceString);
            sentences.add(sentence);
        }

        // Split the remaining text into words
        String[] wordStrings = trimmedText.split("\\s+");
        ArrayList<Word> words = new ArrayList<>();
        for (String wordString : wordStrings) {
            Word word = new Word(wordString);
            words.add(word);
        }

        // Create a map of all Chapters
        Map<String, ChapterStats> chapterStatsMap = new HashMap<>();
        for (Chapter chapter : chapters) {
            String chapterTitle = chapter.getName();
            List<Sentence> chapterSentences = chapter.getSentences(); // get the sentences in the chapter
            int numSentences = chapterSentences.size();
            double avgSentenceLength = chapter.getAvgSentenceLength(); // use the Chapter class to get the average sentence length
            double avgWordLength = chapter.getAvgWordLength(); // use the Chapter class to get the average word length
            ChapterStats chapterStats = new ChapterStats(numSentences, avgSentenceLength, avgWordLength); // create a ChapterStats object
            chapterStatsMap.put(chapterTitle, chapterStats);
        }

        // Create a map of all Sentences
        Map<String, Integer> sentenceLengthCountMap = new HashMap<>();
        for (Sentence sentence : sentences) {
            int numWords = sentence.getWords().size();
            sentenceLengthCountMap.put(Integer.toString(numWords), numWords); // add the sentence to the map
        }

        // Create a map of all Words
        Map<String, Integer> wordLengthCountMap = new HashMap<>();
        for (Word word : words) {
            int numChars = word.getLetters().size();
            wordLengthCountMap.put(Integer.toString(numChars), numChars); // add the word to the map
        }

        // Print out the results
        System.out.println("Chapter Stats:");
        for (String chapterTitle : chapterStatsMap.keySet()) {
            // regex to split the chapter title into the chapter number and the chapter title
            String[] chapterNumber = chapterTitle.split("(?=CHAPTER [\\dIVXLCM]+\\. )");
            String[] chapterNumberParts = chapterNumber[0].split(" ", 3);
            String chapterNumberString = chapterNumberParts[0] + " " + chapterNumberParts[1];
            ChapterStats chapterStats = chapterStatsMap.get(chapterTitle);
            // provide formatting for the output
            String numSentencesStr = String.format("%-5s", chapterStats.getNumSentences());
            String avgSentenceLengthStr = String.format("%-10.2f", chapterStats.getAvgSentenceLength());
            String avgWordLengthStr = String.format("%-10.2f", chapterStats.getAvgWordLength());
            String chapterTitleStr = String.format("%-50s", chapterTitle);
            // print out the results
            System.out.println(" " + chapterNumberString + "    " + numSentencesStr + avgSentenceLengthStr + avgWordLengthStr
                    + chapterTitleStr);
        }

        Histogram[] histograms = new Histogram[2];
        histograms[0] = new Histogram(sentenceLengthCountMap, 15, "words");
        histograms[1] = new Histogram(wordLengthCountMap, 15, "letters");
        System.out.println("Sentence Length Histogram:");
        histograms[0].displayHistogram();
        System.out.println("Word Length Histogram:");
        histograms[1].displayHistogram();

        new HistogramGUI(histograms);
    }
}
