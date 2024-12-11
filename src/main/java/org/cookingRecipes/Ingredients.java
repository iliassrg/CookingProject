package org.cookingRecipes;

import java.io.*;
import java.util.ArrayList;

public class Ingredients {
    ArrayList<String> quantity = new ArrayList<>();
    ArrayList<String> ingredients = new ArrayList<>();
    public Ingredients(File f){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(f));
            String line;
            String inLine;
            int beginIndex;
            int endIndex;
            while((line = reader.readLine()) != null){
                    if (line.contains("@")) {
                            beginIndex = line.indexOf("@") + 1;
                            endIndex = line.indexOf("{", beginIndex);
                            inLine = line.substring(beginIndex, endIndex);
                            if (inLine.contains("@") || inLine.contains("#") || inLine.contains("~")) {
                                String[] words = inLine.split(" ");
                                inLine = words[0];
                                if (inLine.contains(".") || inLine.contains(",")) {
                                    inLine = inLine.substring(0, inLine.length() - 1);
                                }
                            }
                            ingredients.add(inLine);
                    }
            }
            System.out.println("\nΥλικά:");
            for(int i = 0; i < ingredients.size(); i++){
                System.out.println(ingredients.get(i));
            }
            reader.close();
        }
        catch(IOException e){
            System.err.printf("Error: %s\n", e.getMessage());
        }
    }
}
