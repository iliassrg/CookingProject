package org.cookingRecipes;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Kitchenware {
    private List<String> kitchenware;

    // Constructor
    public Kitchenware() {
        this.kitchenware = new ArrayList<>();
    }

    // Getter
    public List<String> getKitchenware() {
        return new ArrayList<>(kitchenware); // Return a copy to preserve encapsulation
    }

    // Add kitchenware
    public void addKitchenware(String item) {
        if (item != null && !item.isEmpty()) {
            kitchenware.add(item);
        }
    }

    // Load kitchenware from a file
    public void loadFromFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                processLine(line);
            }
        } catch (IOException e) {
            System.err.printf("Error: %s\n", e.getMessage());
        }
    }

    // Process a single line to extract kitchenware
    private void processLine(String line) {
        if (line.contains("#")) {
            String tagLine = extractTagLine(line);
            if (tagLine != null) {
                addKitchenware(tagLine);
            }
        }
    }

    // Extract the tag line from the input
    private String extractTagLine(String line) {
        int beginIndex = line.indexOf("#") + 1;
        int endIndex = line.indexOf("{", beginIndex);
        if (endIndex == -1) return null; // Guard against missing '{'

        String tagLine = line.substring(beginIndex, endIndex);
        tagLine = cleanTagLine(tagLine);
        return tagLine;
    }

    // Clean the extracted tag line
    private String cleanTagLine(String tagLine) {
        if (tagLine.contains("@") || tagLine.contains("#") || tagLine.contains("~")) {
            String[] words = tagLine.split(" ");
            tagLine = words[0];
        }
        if (tagLine.endsWith(".") || tagLine.endsWith(",")) {
            tagLine = tagLine.substring(0, tagLine.length() - 1);
        }
        return tagLine;
    }

    // Display kitchenware items
    public void displayKitchenware() {
        System.out.println("\nΣκεύη:");
        for (String item : kitchenware) {
            System.out.printf("\t%s\n", item);
        }
    }
}
