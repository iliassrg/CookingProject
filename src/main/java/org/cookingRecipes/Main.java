package org.cookingRecipes;

import java.io.File;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Έλεγχος αν υπάρχουν ορίσματα για το αρχείο συνταγών
        if (args.length == 0) {
            System.err.println("Πρέπει να δώσετε τουλάχιστον ένα αρχείο συνταγής.");
            return; // Σταματάει την εκτέλεση αν δεν δίνονται αρχεία
        }

        List<String> ingredientsList = new ArrayList<>();

        // Επεξεργασία όλων των ορισμάτων (δηλαδή, όλων των αρχείων συνταγών)
        for (String fileName : args) {
            File file = new File(fileName);

            // Έλεγχος αν το αρχείο υπάρχει πριν προχωρήσουμε
            if (!file.exists()) {
                System.err.println("Λάθος όνομα αρχείου: " + fileName);
                continue; // Παραλείπει το τρέχον αρχείο και προχωράει στο επόμενο
            }

            System.out.println("\nΣυνταγή για " + fileName.substring(0, fileName.indexOf(".")));

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

        // Εκτύπωση της λίστας αγορών
        System.out.println("\nΛίστα αγορών:");
        for (String item : ingredientsList) {
            System.out.println(" - " + item);
        }
    }

    private static void displaySteps(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            int stepCounter = 1; // Ο μετρητής βημάτων ξεκινά από το 1
            boolean newStep = true; // Παρακολουθεί αν βρισκόμαστε σε νέο βήμα
            String line;

            System.out.println("\nΒήματα:"); // Εκτύπωση κεφαλίδας "Βήματα"
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) { // Αν η γραμμή είναι κενή, προχωράμε στο επόμενο βήμα
                    stepCounter++;
                    newStep = true; // Σηματοδοτεί την αρχή ενός νέου βήματος
                    System.out.println(); // Εκτυπώνει μια κενή γραμμή για καλύτερη μορφοποίηση
                } else {
                    if (newStep) { // Εκτύπωση αριθμού βήματος για το νέο βήμα
                        System.out.println("\t" + stepCounter + ". " + line);
                        newStep = false;
                    } else {
                        System.out.println("\t   " + line); // Εκτύπωση επιπλέον γραμμών με εσοχή
                    }
                }
            }
        } catch (IOException e) {
            System.err.printf("Σφάλμα: %s\n", e.getMessage());
        }
    }
}
