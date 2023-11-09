package ChanningBabb_FileProcessing;

// ChapterStats class to store the stats for each chapter
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