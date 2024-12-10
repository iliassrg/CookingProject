package org.cookingRecipes;

import java.io.*;
import java.util.ArrayList;
public class Kitchenware{
ArrayList<String> kitchenware = new ArrayList<>();
public Kitchenware(File f) {
    try {
        BufferedReader reader = new BufferedReader(new FileReader(f));
        String line;
        String tagLine;
        int beginIndex;
        while((line = reader.readLine()) != null) {
            if(line.contains("#")){
                beginIndex = line.indexOf("#") + 1;
                tagLine = line.substring(beginIndex,line.indexOf("{", beginIndex));
                if(tagLine.contains("@") || tagLine.contains("#") || tagLine.contains("~")){
                    String[] words = tagLine.split(" ");
                    tagLine = words[0];
                    if(tagLine.contains(".") || tagLine.contains(",")){
                        tagLine = tagLine.substring(0, tagLine.length() - 1);
                    }
                }
                kitchenware.add(tagLine);
            }
        }
        // Εμφάνιση των σκευών
        System.out.println("\nΣκεύη:");
        for(String k : kitchenware) {
            System.out.println(k);
        }
        reader.close();
    } catch(IOException e) {
        System.err.printf("Error: %s\n", e.getMessage());
    }
}
}