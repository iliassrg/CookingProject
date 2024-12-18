package org.cookingRecipes;

import java.util.ArrayList;
import java.util.List;

public class Ingredients extends Recipe {
    private List<String> ingredients;

    public Ingredients() {
        this.ingredients = new ArrayList<>();
    }

    public List<String> getIngredients() {
        return new ArrayList<>(ingredients);
    }

    public void addIngredient(List<String> ingredient) {
        if (ingredient != null && !ingredient.isEmpty()) {
            //Προσθέτουμε στη λίστα τις λίστες με τα υλικά από τη κάθε γραμμή που υπάρχουν
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
        // Δημιουργία λίστας subIngredients για την εισαγωγή υλικών από κάθε γραμμή.
        List<String> subIngredients = extractIngredient(line, position);
        addIngredient(subIngredients);
    }

    // Δημιουργία υλικών από τη γραμμή στην οποία βρισκόμαστε.
    private List<String> extractIngredient(String line, List<Integer> position) {
        List<String> subIngredients = new ArrayList<>();
        for (Integer i : position) {
            //Όπου i το σημείο στο οποίο παρουσιάζεται ο χαρακτήρας '@'.
            int beginIndex = i + 1;
            int endIndex = line.indexOf("}", beginIndex);
            if(endIndex == -1){
                //Στην περίπτωση αυτή, το υλικό είναι μόνο μία λέξη χωρίς ποσότητα.
                endIndex = findEndIndex(line, beginIndex);
            }

            String ingredient;
            if (endIndex == -1) {
                //Στην περίπτωση αυτή, το υλικό είναι μόνο μία λέξη χωρίς ποσότητα
                //και βρίσκεται στο τέλος της γραμμής.
                ingredient = line.substring(beginIndex);
            } else {
                ingredient = line.substring(beginIndex, endIndex);
            }

            subIngredients.add(cleanIngredient(ingredient));
        }
        return subIngredients;
    }

    // Βοηθητική μέθοδος για να βρούμε το δείκτη που δείχνει στο τέλος ενός υλικού
    // Στην περίπτωση αυτή το υλικό είναι 1 λέξη, αλλά μπορεί να τελειώνει με '.', ',' ή ' '.
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
            //Στην περίπτωση που το υλικό είναι μία λέξη και δεν έχει ποσότητα
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

    @Override
    public void display() {
        System.out.println("\nΥλικά:");
        for (String ingredient : ingredients) {
            System.out.println("\t" + ingredient);
        }
    }
}
