package org.cookingRecipes;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Ingredients extends Recipe {
    private List<String> ingredients;
    private JTextArea displayArea;  // Αναφορά στην JTextArea

    public Ingredients() {
        this.ingredients = new ArrayList<>();
    }

    public void setDisplayArea(JTextArea displayArea) {
        this.displayArea = displayArea;
    }

    public List<String> getIngredients() {
        return new ArrayList<>(ingredients);
    }

    public void addIngredient(List<String> ingredient) {
        if (ingredient != null && !ingredient.isEmpty()) {
            ingredients.addAll(ingredient);
        }
    }

    @Override
    protected void processLine(String line) {
        List<Integer> position = new ArrayList<>();
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '@') {
                position.add(i);
            }
        }
        List<String> subIngredients = extractIngredient(line, position);
        addIngredient(subIngredients);
    }

    private List<String> extractIngredient(String line, List<Integer> position) {
        List<String> subIngredients = new ArrayList<>();
        for (Integer i : position) {
            int beginIndex = i + 1;
            int endIndex = line.indexOf("}", beginIndex);
            if (endIndex == -1) {
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

    private int findEndIndex(String line, int beginIndex) {
        int[] possibleEndIndices = {
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

    private String cleanIngredient(String ingredient) {
        if (ingredient.contains("@") || ingredient.contains("#") || ingredient.contains("~")) {
            String[] words = ingredient.split(" ");
            ingredient = words[0];
        }
        if (ingredient.endsWith(".") || ingredient.endsWith(",")) {
            ingredient = ingredient.substring(0, ingredient.length() - 1);
        }
        ingredient = ingredient.replace("{", " ");
        ingredient = ingredient.replace("%", " ");
        return ingredient;
    }

    @Override
    public void display() {
        if (displayArea != null) {
            displayArea.append("\nΥλικά:\n");
            for (String ingredient : ingredients) {
                displayArea.append("\t" + ingredient + "\n");
            }
        }
    }
}