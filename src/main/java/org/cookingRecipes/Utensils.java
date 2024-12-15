package org.cookingRecipes;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Utensils {
    private List<String> utensils;
    // Constructor
    public Utensils() {
        this.utensils = new ArrayList<>();
    }

    // Getter
    public List<String> getUtensils() {
        return new ArrayList<>(utensils); // Return a copy to preserve encapsulation
    }

    // Add utensil
    public void addUtensil(List<String> utensil) {
        if (utensil != null && !utensil.isEmpty()) {
            utensils.addAll(utensil);
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

    // Process a single line to extract utensils
    private void processLine(String line) {
        List<Integer> position = new ArrayList<>();
        for(int i = 0 ; i < line.length(); i++){
            if(line.charAt(i) == '#'){
                position.add(i);
            }
        }
        List<String> subUtensils = extractUtensil(line, position);
        addUtensil(subUtensils);
    }

    // Extract the tag line from the input
    private List<String> extractUtensil(String line, List<Integer> position) {
        List<String> subUtensils = new ArrayList<>();
        for(Integer i : position){
            int beginIndex = i + 1;
            int endIndex = line.indexOf("}", beginIndex);
            if (endIndex == -1) {
                endIndex = line.indexOf(" ", beginIndex);
            }
            String utensil = line.substring(beginIndex, endIndex);
            utensil = cleanUtensil(utensil);
            subUtensils.add(utensil);
        }
        return subUtensils;
    }

    // Clean the extracted tag line
    private String cleanUtensil(String utensil) {
        if (utensil.contains("@") || utensil.contains("#") || utensil.contains("~")) {
            String[] words = utensil.split(" ");
            utensil = words[0];
        }
        if (utensil.endsWith(".") || utensil.endsWith(",")) {
            utensil = utensil.substring(0, utensil.length() - 1);
        }
        utensil = utensil.replace("{"," ");
        return utensil;
    }

    // Display kitchenware items
    public void displayUtensils() {
        System.out.println("\nΣκεύη:");
        for (String item : utensils) {
            System.out.printf("\t%s\n", item);
        }
    }
}
