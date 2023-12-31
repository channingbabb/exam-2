package ChanningBabb_FileProcessing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class LitText implements LitLyzer {
    String contents;


    @Override
    public String getContents() {
        return this.contents;
    }

    @Override
    public Map<String, Integer> count(Counter counter, ArrayList<String> categories) {
        HashMap<String, Integer> countMap = new HashMap<>();
        for (String s : categories) { // for each category in categories
            countMap.put(s, counter.count(contents)); // add the category and the count of the category to the map
        }
        return countMap;
    }
}
