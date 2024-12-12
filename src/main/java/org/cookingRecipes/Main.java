package org.cookingRecipes;

import java.io.File;


public class Main {
    public static void main(String[] args) {
        //First file
        File file = new File("pancakes.cook");
        Ingredients ingredient = new Ingredients();
        ingredient.loadFromFile(file);
        ingredient.displayIngredients();

        Kitchenware kitchenware = new Kitchenware();
        kitchenware.loadFromFile(file);
        kitchenware.displayKitchenware();

        Time time = new Time();
        time.loadFromFile(file);
        time.displayTotalTime();

        //Second file
        File file2 = new File("french_fries.cook");

        Ingredients ingredient2 = new Ingredients();
        ingredient2.loadFromFile(file2);
        ingredient2.displayIngredients();

        Kitchenware kitchenware2 = new Kitchenware();
        kitchenware2.loadFromFile(file2);
        kitchenware2.displayKitchenware();

        Time time2 = new Time();
        time2.loadFromFile(file2);
        time2.displayTotalTime();
    }
}