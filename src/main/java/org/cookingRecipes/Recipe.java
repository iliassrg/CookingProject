package org.cookingRecipes;

import java.io.*;

public abstract class Recipe {

    // Φόρτωση δεδομένων από αρχείο
    public void loadFromFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                processLine(line);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Wrong file name! " + file);
        } catch (IOException e) {
            System.err.printf("Error: %s\n", e.getMessage());
        }
    }

    protected abstract void processLine(String line);

    public abstract void display();
}
