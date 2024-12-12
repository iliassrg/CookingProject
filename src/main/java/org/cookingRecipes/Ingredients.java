package org.cookingRecipes;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Ingredients {
    private List<String> ingredients;

    // Constructor
    public Ingredients() {
        this.ingredients = new ArrayList<>();
    }

    // Getter
    public List<String> getIngredients() {
        return new ArrayList<>(ingredients); // Return a copy to preserve encapsulation
    }

    // Add ingredient
    public void addIngredient(String ingredient) {
        if (ingredient != null && !ingredient.isEmpty()) {
            ingredients.add(ingredient);
        }
    }

    // Load ingredients from a file
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

    // Process a single line to extract ingredients
    private void processLine(String line) {
        if (line.contains("@")) {
            String ingredient = extractIngredient(line);
            if (ingredient != null) {
                addIngredient(ingredient);
            }
        }
    }

    // Extract the ingredient from the input line
    private String extractIngredient(String line) {
        int beginIndex = line.indexOf("@") + 1;
        int endIndex = line.indexOf("{", beginIndex);
        if (endIndex == -1) return null; // Guard against missing '{'

        String ingredient = line.substring(beginIndex, endIndex);
        ingredient = cleanIngredient(ingredient);
        return ingredient;
    }

    // Clean the extracted ingredient
    private String cleanIngredient(String ingredient) {
        if (ingredient.contains("@") || ingredient.contains("#") || ingredient.contains("~")) {
            String[] words = ingredient.split(" ");
            ingredient = words[0];
        }
        if (ingredient.endsWith(".") || ingredient.endsWith(",")) {
            ingredient = ingredient.substring(0, ingredient.length() - 1);
        }
        return ingredient;
    }

    // Display ingredients
    public void displayIngredients() {
        System.out.println("\nΥλικά:");
        for (String ingredient : ingredients) {
            System.out.printf("\t%s\n", ingredient);
        }
    }
}
