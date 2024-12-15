package org.cookingRecipes;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        // Array of file names
        String[] fileNames = {"pancakes.cook", "french_fries.cook", "syrup.cook", "fakes.cook"};
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

        displaySteps(file);
    }

    private static void displaySteps(File file){
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            System.out.println("\nΒήματα:");
            while ((line = reader.readLine()) != null) {
                System.out.println("\t"+line);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Wrong file name!");
        } catch (IOException e) {
            System.err.printf("Error: %s\n", e.getMessage());
        }
    }
}
