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
            String unit;
            String q;
            while((line = reader.readLine()) != null){
                if(line.contains("@")){
                    inLine = line.substring(line.indexOf("@") + 1,line.indexOf("{"));
                    q = line.substring(line.indexOf("{") + 1,line.indexOf("}"));
                    if(q.contains("%")){
                        q = q.replace(Character.toString('%'), " ");
                    }
                    quantity.add(q);
                    ingredients.add(inLine);
                }
            }
            System.out.println("\nΥλικά:");
            for(int i = 0; i < ingredients.size(); i++){
                System.out.println(ingredients.get(i) + " " + quantity.get(i));
            }
            reader.close();
        }
        catch(IOException e){
            System.err.printf("Error: %s\n", e.getMessage());
        }
    }
}
