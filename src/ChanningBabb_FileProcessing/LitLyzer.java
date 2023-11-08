package ChanningBabb_FileProcessing;

import java.util.ArrayList;
import java.util.Map;

public interface LitLyzer {
    public String getContents();
    Map<String, Integer> count (Counter counter, ArrayList<String> categories);
}
