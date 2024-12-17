package org.cookingRecipes;

import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        String[] fileNames = {"pancakes.cook", "french_fries.cook", "syrup.cook", "fakes.cook"};

        List<String> ingredientsList = new ArrayList<>();

        // Process each file and populate ingredientsList
        for (String fileName : fileNames) {
            processFile(new File(fileName), ingredientsList);
        }

        // Add all items from ingredientsList to shoppingList
        List<String> shoppingList = new ArrayList<>(ingredientsList);

        // Print the shopping list
        System.out.println("\nShopping List:");
        for (String item : shoppingList) {
            System.out.println(" - " + item);
        }
    }


    private static void processFile(File file, List<String> ingredientsList) {
        Ingredients ingredients = new Ingredients();
        ingredients.loadFromFile(file);
        ingredients.displayIngredients();

        // Add all ingredients from the file into the main ingredientsList
        ingredientsList.addAll(ingredients.getIngredients());

        Utensils utensils = new Utensils();
        utensils.loadFromFile(file);
        utensils.displayUtensils();

        Time time = new Time();
        time.loadFromFile(file);
        time.displayTotalTime();

        displaySteps(file);
    }

    private static void displaySteps(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            int counter = 1;
            boolean changedLine = true;
            String line;
            System.out.println("\nΒήματα:");
            while ((line = reader.readLine()) != null) {
                if(line.isEmpty()){
                    counter++;
                    changedLine = true;
                    System.out.println();
                }
                else{
                    if(changedLine){
                        System.out.println("\t" + counter + ". " + line);
                        changedLine = false;
                    }
                    else{
                        System.out.println("\t   "+line);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Wrong file name!"+file);
        } catch (IOException e) {
            System.err.printf("Error: %s\n", e.getMessage());
        }
    }
}
