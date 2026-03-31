java
package com.grievance.nlp;

import java.util.*;

public class GrievanceClassifier {

    // Each department has a list of keywords
    private static final Map<String, List<String>> KEYWORDS = new LinkedHashMap<>();

    static {
        KEYWORDS.put("Water Supply", Arrays.asList(
            "water", "pipe", "leakage", "tap", "drainage",
            "flood", "sewage", "borewell", "tank", "supply"
        ));
        KEYWORDS.put("Electricity", Arrays.asList(
            "electricity", "power", "light", "transformer",
            "wire", "current", "voltage", "blackout", "outage", "electric"
        ));
        KEYWORDS.put("Roads", Arrays.asList(
            "road", "pothole", "footpath", "bridge", "traffic",
            "street", "highway", "construction", "pavement", "signal"
        ));
        KEYWORDS.put("Sanitation", Arrays.asList(
            "garbage", "waste", "dustbin", "cleaning", "dirty",
            "smell", "toilet", "sweeper", "litter", "trash", "drain"
        ));
        KEYWORDS.put("Health", Arrays.asList(
            "hospital", "doctor", "medicine", "health", "disease",
            "clinic", "ambulance", "vaccination", "nurse", "medical"
        ));
    }

    // This method reads the complaint text and returns department name
    public static String classify(String title, String description) {

        // Combine title and description, make lowercase
        String text = (title + " " + description).toLowerCase();

        // Score each department
        Map<String, Integer> scores = new HashMap<>();

        for (Map.Entry<String, List<String>> entry : KEYWORDS.entrySet()) {
            int score = 0;
            for (String keyword : entry.getValue()) {
                if (text.contains(keyword)) {
                    score++; // +1 for each matching keyword
                }
            }
            scores.put(entry.getKey(), score);
        }

        // Return department with highest score
        // If no keywords match → return "General"
        return scores.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .filter(e -> e.getValue() > 0)
            .map(Map.Entry::getKey)
            .orElse("General");
    }
}
