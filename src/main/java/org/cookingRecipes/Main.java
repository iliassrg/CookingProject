package org.cookingRecipes;

import java.io.File;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String[] fileNames = {"pancakes.cook", "french_fries.cook", "syrup.cook", "fakes.cook"};
        boolean fileExists = true;
        List<String> ingredientsList = new ArrayList<>();
        for (String fileName : fileNames) {
            fileExists = true;
            File file = new File(fileName);

            // Έλεγχουμε αν το αρχείο υπάρχει πριν προχωρήσουμε
            if (!file.exists()) {
                System.err.println("Wrong file name: " + fileName);
                fileExists = false;
                continue; // Συνεχίζουμε στο επόμενο αρχείο
            }

            System.out.println("\nΣυνταγή για " + fileName.substring(0,fileName.indexOf(".")));

            // Χρήση πολυμορφισμού
            Recipe[] recipes = {new Ingredients(), new Utensils(), new Time()};
            for (Recipe recipe : recipes) {
                recipe.loadFromFile(file);
                recipe.display();

                // Ειδική διαχείριση για τα υλικά (Ingredients)
                if (recipe instanceof Ingredients) {
                    ingredientsList.addAll(((Ingredients) recipe).getIngredients());
                }
            }
            displaySteps(file);
        }

        //Αν υπάρχουν τα αρχεία, τότε εκτυπώνονται όλα τα υλικά από κάθε αρχείο συνταγής.
        if(fileExists) {
            System.out.println("\nΛίστα αγορών:");
            for (String item : ingredientsList) {
                System.out.println(" - " + item);
            }
        }
    }

    private static void displaySteps(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            int stepCounter = 1; // Ο μετρητής βημάτων ξεκινά από το 1
            boolean newStep = true; // Παρακολουθεί αν βρισκόμαστε σε νέο βήμα
            String line;

            System.out.println("\nΒήματα:");
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) { // Αν η γραμμή είναι κενή, προχωράμε στο επόμενο βήμα
                    stepCounter++;
                    newStep = true; // Σηματοδοτεί την αρχή ενός νέου βήματος
                    System.out.println();
                } else {
                    if (newStep) { // Εκτύπωση αριθμού βήματος για το νέο βήμα
                        System.out.println("\t" + stepCounter + ". " + line);
                        newStep = false;
                    } else {
                        System.out.println("\t   " + line); // Εκτύπωση επιπλέον γραμμών αν υπάρχουν για 1 βήμα
                    }
                }
            }
        } catch (IOException e) {
            System.err.printf("Error: %s\n", e.getMessage());
        }
    }
}
