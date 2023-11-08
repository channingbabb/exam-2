package ChanningBabb_FileProcessing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
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

        // Remove Gutenberg info
        String trimmedText = wholeText.substring(796, wholeText.length() - 18870).strip();

        // Remove title and table of contents
        trimmedText = trimmedText.substring(4560, trimmedText.length()).strip();

        // Remove and store ETYMOLOGY and EXTRACTS sections
        String etymology = trimmedText.substring(0, 1390).strip();
        String extracts = trimmedText.substring(1391, 22090).strip();
        trimmedText = trimmedText.substring(22091, trimmedText.length()).strip();

        // Split the remaining text into chapters
        List<String> chapterStrings = Arrays.asList(trimmedText.split("(?=CHAPTER [\\dIVXLCM]+\\.)"));
        ArrayList<Chapter> chapters = new ArrayList<>();
        for (String chapterString : chapterStrings) {
            String chapterTitle = chapterString.substring(0, chapterString.indexOf("\n")).strip();
            Chapter chapter = new Chapter(chapterTitle, chapterString);
            chapters.add(chapter);
        }

        // Split the remaining text into sentences
        List<String> sentenceStrings = Arrays.asList(trimmedText.split("(?<=[.?!])\\s+"));
        ArrayList<Sentence> sentences = new ArrayList<>();
        for (String sentenceString : sentenceStrings) {
            Sentence sentence = new Sentence(sentenceString);
            sentences.add(sentence);
        }

        // Split the remaining text into words
        List<String> wordStrings = Arrays.asList(trimmedText.split("\\s+"));
        ArrayList<Word> words = new ArrayList<>();
        for (String wordString : wordStrings) {
            Word word = new Word(wordString);
            words.add(word);
        }

        // Create a map of all Chapters
        Map<String, ChapterStats> chapterStatsMap = new HashMap<>();
        for (Chapter chapter : chapters) {
            String chapterTitle = chapter.getName();
            List<Sentence> chapterSentences = chapter.getSentences();
            int numSentences = chapterSentences.size();
            int totalWords = 0;
            int totalChars = 0;
            for (Sentence sentence : chapterSentences) {
                totalWords += sentence.getWords().size();
                for (Word word : sentence.getWords()) {
                    totalChars += word.getLetters().size();
                }
            }
            double avgSentenceLength = (double) totalWords / numSentences;
            double avgWordLength = (double) totalChars / totalWords;
            ChapterStats chapterStats = new ChapterStats(numSentences, avgSentenceLength, avgWordLength);
            chapterStatsMap.put(chapterTitle, chapterStats);
        }

        // Create a map of all Sentences
        Map<String, Integer> sentenceLengthCountMap = new HashMap<>();
        for (Sentence sentence : sentences) {
            int numWords = 0;
            numWords = sentence.getWords().size();
            sentenceLengthCountMap.put(Integer.toString(numWords), numWords);
        }

        // Create a map of all Words
        Map<String, Integer> wordLengthCountMap = new HashMap<>();
        for (Word word : words) {
            int numChars = 0;
            numChars = word.getLetters().size();
            wordLengthCountMap.put(Integer.toString(numChars), numChars);
        }

        // Print out the results
        System.out.println("Chapter Stats:");
        for (String chapterTitle : chapterStatsMap.keySet()) {
            String[] chapterNumber = chapterTitle.split("(?=CHAPTER [\\dIVXLCM]+\\. )");
            String[] chapterNumberParts = chapterNumber[0].split(" ", 3);
            String chapterNumberString = chapterNumberParts[0] + " " + chapterNumberParts[1];
            ChapterStats chapterStats = chapterStatsMap.get(chapterTitle);
                String numSentencesStr = String.format("%-5s", chapterStats.getNumSentences());
                String avgSentenceLengthStr = String.format("%-10.2f", chapterStats.getAvgSentenceLength());
                String avgWordLengthStr = String.format("%-10.2f", chapterStats.getAvgWordLength());
                String chapterTitleStr = String.format("%-50s", chapterTitle);
                System.out.println(" " + chapterNumberString + numSentencesStr + avgSentenceLengthStr + avgWordLengthStr + chapterTitleStr);
}

        Histogram[] histograms = new Histogram[2];
        histograms[0] = new Histogram(sentenceLengthCountMap, 15, "words");
        histograms[1] = new Histogram(wordLengthCountMap, 15, "letters");
        System.out.println("Sentence Length Histogram:");
        histograms[0].displayHistogram();
        System.out.println("Word Length Histogram:");
        histograms[1].displayHistogram();

        HistogramGUI histogramGUI = new HistogramGUI(histograms);
    }
}

class ChapterStats {
    private int numSentences;
    private double avgSentenceLength;
    private double avgWordLength;

    public ChapterStats(int numSentences, double avgSentenceLength, double avgWordLength) {
        this.numSentences = numSentences;
        this.avgSentenceLength = avgSentenceLength;
        this.avgWordLength = avgWordLength;
    }

    public int getNumSentences() {
        return numSentences;
    }

    public double getAvgSentenceLength() {
        return avgSentenceLength;
    }

    public double getAvgWordLength() {
        return avgWordLength;
    }
}

