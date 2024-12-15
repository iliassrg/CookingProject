package org.cookingRecipes;

import java.io.*;

public class Main {
    // Flag to track if the file-not-found message has been printed
    private static boolean fileNotFoundMessagePrinted = false;

    public static void main(String[] args) {
        // Array of file names
        String[] fileNames = {"pancakes.cook", "french_fries.cook", "syrup.cook", "fakes.cook"};
        // Process each file
        for (String fileName : fileNames) {
            if(!fileNotFoundMessagePrinted) {
                processFile(new File(fileName));
            }
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
            // Print the error message only if it has not been printed yet
            if (!fileNotFoundMessagePrinted) {
                System.err.println("Wrong file name!"+file);
                fileNotFoundMessagePrinted = true; // Set the flag to true
            }
        } catch (IOException e) {
            System.err.printf("Error: %s\n", e.getMessage());
        }
    }
}
