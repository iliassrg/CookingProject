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
    public void addIngredient(List<String> ingredient) {
        if (ingredient != null && !ingredient.isEmpty()) {
            ingredients.addAll(ingredient);
        }
    }

    // Load ingredients from a file
    public void loadFromFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                processLine(line);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Wrong file name!"+file);
        } catch (IOException e) {
            System.err.printf("Error: %s\n", e.getMessage());
        }
    }

    // Process a single line to extract ingredients
    private void processLine(String line) {
        List<Integer> position = new ArrayList<>();
        for(int i = 0 ; i < line.length(); i++){
            if(line.charAt(i) == '@'){
                position.add(i);
            }
        }
        List<String> subIngredients = extractIngredient(line, position);
        addIngredient(subIngredients);
    }

    // Extract the ingredient from the input line
    private List<String> extractIngredient(String line, List<Integer> position) {
        List<String> subIngredients = new ArrayList<>();
        for (Integer i : position) {
            int beginIndex = i + 1;
            int endIndex = line.indexOf("}", beginIndex);
            if(endIndex == -1){
                endIndex = findEndIndex(line, beginIndex);
            }

            String ingredient;
            if (endIndex == -1) {
                ingredient = line.substring(beginIndex);
            } else {
                ingredient = line.substring(beginIndex, endIndex);
            }

            subIngredients.add(cleanIngredient(ingredient));
        }
        return subIngredients;
    }

    // Helper method to find the end index of an ingredient
    private int findEndIndex(String line, int beginIndex) {
        int[] possibleEndIndices = {
                line.indexOf("}", beginIndex),
                line.indexOf(" ", beginIndex),
                line.indexOf(".", beginIndex),
                line.indexOf(",", beginIndex)
        };

        for (int index : possibleEndIndices) {
            if (index != -1) {
                return index;
            }
        }
        return -1;
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
        ingredient = ingredient.replace("{"," ");
        ingredient = ingredient.replace("%", " ");
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
