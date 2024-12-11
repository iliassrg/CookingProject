package org.cookingRecipes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        File file = new File("pancakes.cook");
        try {
            FileReader reader = new FileReader(file);
            int data = reader.read();
            while(data != -1) {
                System.out.print((char)data);
                data = reader.read();
            }
            reader.close();
        }catch (FileNotFoundException e){
            System.err.println("Wrong file name!");
        } catch (IOException e) {
            System.err.printf("Error: %s\n", e.getMessage());
        }
        new Kitchenware(file);
        new Time(file);
        new Ingredients(file);
    }
}