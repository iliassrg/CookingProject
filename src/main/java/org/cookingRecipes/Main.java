package org.cookingRecipes;

import java.io.File;


public class Main {
    public static void main(String[] args) {
        //First file
        File file = new File("pancakes.cook");
        Ingredients ingredient = new Ingredients();
        ingredient.loadFromFile(file);
        ingredient.displayIngredients();

        Utensils utensils = new Utensils();
        utensils.loadFromFile(file);
        utensils.displayUtensils();

        Time time = new Time();
        time.loadFromFile(file);
        time.displayTotalTime();

        //Second file
        File file2 = new File("french_fries.cook");

        Ingredients ingredient2 = new Ingredients();
        ingredient2.loadFromFile(file2);
        ingredient2.displayIngredients();

        Utensils utensils2 = new Utensils();
        utensils2.loadFromFile(file2);
        utensils2.displayUtensils();

        Time time2 = new Time();
        time2.loadFromFile(file2);
        time2.displayTotalTime();
    }
}