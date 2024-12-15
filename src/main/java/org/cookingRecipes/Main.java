package org.cookingRecipes;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        // Array of file names
        String[] fileNames = {"pancakes.cook", "french_fries.cook", "syrup.cook"};

        // Process each file
        for (String fileName : fileNames) {
            processFile(new File(fileName));
        }
    }

    private static void processFile(File file) {
        Ingredients ingredients = new Ingredients();
        ingredients.loadFromFile(file);
        ingredients.displayIngredients();

        Utensils utensils = new Utensils();
        utensils.loadFromFile(file);
        utensils.displayUtensils();

        Time time = new Time();
        time.loadFromFile(file);
        time.displayTotalTime();
    }
}
