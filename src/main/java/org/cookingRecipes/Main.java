package org.cookingRecipes;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        File recipeFile = new File("pancakes.cook");
        new Kitchenware(recipeFile);
        new Time(recipeFile);
    }
}