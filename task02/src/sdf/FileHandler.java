package sdf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class FileHandler {
    
    private Map<String, Map<String, Double>> nextWordFrequency;
    private Map<String, Map<String, Double>> nextWordProbability;
    private File file;
    private int count;
    
    public FileHandler(File file) {
        this.file = file;
        this.nextWordFrequency = new HashMap<>();
        this.count = 0;
    }

    public Map<String, Map<String, Double>> getnextWordFrequency() {
        return nextWordFrequency;
    }

    public Map<String, Map<String, Double>> getnextWordProbability() {
        return nextWordProbability;
    }

    public int getcount() {
        return count;
    }
    
    public void TextProcessor() throws IOException {

        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String line = "";

        // Create a map to hold the next word distribution frequency
        Map<String, Map<String, Double>> nextWordFrequency = new HashMap<>();

        while((line = br.readLine()) != null) {

            // Tokenize the text into words
            String[] words = line.replaceAll("[.,:!-(){}\"\n\t]", "")
                                    .toLowerCase()
                                    .trim()
                                    .split(" ");
                                  
            // Loop through the words and update the next word distribution frequency
            for (int i = 0; i < words.length - 1; i++) {
                String currentWord = words[i];
                String nextWord = words[i+1];
                if (nextWordFrequency.containsKey(currentWord)) {
                    Map<String, Double> frequency = nextWordFrequency.get(currentWord);
                    if (frequency.containsKey(nextWord)) {
                        double count = frequency.get(nextWord);
                        frequency.put(nextWord, count + 1);
                    } else {
                        frequency.put(nextWord, 1.0);
                    }
                } else {
                    Map<String, Double> frequency = new HashMap<>();
                    frequency.put(nextWord, 1.0);
                    nextWordFrequency.put(currentWord, frequency);
                }
            }
        }

        // Compute the probability of occurrence for each next word
        nextWordProbability = new HashMap<>();
        for (Map.Entry<String, Map<String, Double>> entry : nextWordFrequency.entrySet()) {
            String currentWord = entry.getKey();
            Map<String, Double> frequency = entry.getValue();
            double totalCount = 0.0;
            for (double count : frequency.values()) {
                totalCount += count;
            }
        
            // Compute the probability of occurrence for each next word
            Map<String, Double> subMap = new HashMap<>();
            for (Map.Entry<String, Double> subEntry : frequency.entrySet()) {
                String nextWord = subEntry.getKey();
                double count = subEntry.getValue();
                double probability;
                if (totalCount != 0) {
                    probability = (double) count / totalCount;
                } else {
                    probability = 0.0;
                }
                subMap.put(nextWord, probability);
            }
            nextWordProbability.put(currentWord, subMap);         
        }
        
        // Print out the next word distribution probability for each word
        
        for (Entry<String, Map<String, Double>> entry : nextWordProbability.entrySet()) {
            String currentWord = entry.getKey();
            Map<String, Double> probability = entry.getValue();
            System.out.println(currentWord);
            for (Entry<String, Double> subEntry : probability.entrySet()) {
                String nextWord = subEntry.getKey();
                double prob = subEntry.getValue();
                System.out.println("    " + nextWord + " " + prob);
            }
        }

    }

}
