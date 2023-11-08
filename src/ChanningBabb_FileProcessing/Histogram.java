package ChanningBabb_FileProcessing;

import java.util.Map;

public class Histogram {
    private Map<String, Integer> data;
    private int scale;
    private String measuringUnit;

    public Histogram(Map<String, Integer> data, int scale, String measuringUnit) {
        this.data = data;
        this.scale = scale;
        this.measuringUnit = measuringUnit;
    }

    public String getMeasuringUnit() {
        return measuringUnit;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public String displayHistogramGUI() {
        // output key and value for each entry in the map
        int maxFrequency = 0;
        for (int frequency : data.values()) {
            if (frequency > maxFrequency) {
                maxFrequency = frequency;
            }
        }

        int numBins = maxFrequency / scale + 1;
        int[] bins = new int[numBins];

        for (int frequency : data.values()) {
            int binIndex = frequency / scale;
            bins[binIndex]++;
        }

        String histogram = "";
        for (int i = numBins-1; i > 0; i--) {
            int lowerBound = i * scale;
            int upperBound = (i + 1) * scale - 1;
            if (i == numBins - 1) {
                upperBound = maxFrequency;
            }
            // if bins is 0, don't print anything
            if (bins[i] == 0) {
                continue;
            }
            histogram += String.format("%3d %3s: ", lowerBound, measuringUnit);
            for (int j = 0; j < bins[i]; j++) {
                histogram += "*";
            }
            histogram += "<br>";
        }
        return histogram;
    }

    public void displayHistogram() {
        // output key and value for each entry in the map
        int maxFrequency = 0;
        for (int frequency : data.values()) {
            if (frequency > maxFrequency) {
                maxFrequency = frequency;
            }
        }

        int numBins = maxFrequency / scale + 1;
        int[] bins = new int[numBins];

        for (int frequency : data.values()) {
            int binIndex = frequency / scale;
            bins[binIndex]++;
        }

        for (int i = numBins-1; i > 0; i--) {
            int lowerBound = i * scale;
            int upperBound = (i + 1) * scale - 1;
            if (i == numBins - 1) {
                upperBound = maxFrequency;
            }
            // if bins is 0, don't print anything
            if (bins[i] == 0) {
                continue;
            }
            System.out.printf("%3d %3s: ", lowerBound, measuringUnit);
            for (int j = 0; j < bins[i]; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
}
