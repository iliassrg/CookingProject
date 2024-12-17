package org.cookingRecipes;

import java.io.*;

public abstract class Recipe {
    // Load data from file
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

    // Abstract method to process a line (implemented in subclasses)
    protected abstract void processLine(String line);

    // Abstract method to display content (implemented in subclasses)
    public abstract void display();
}
