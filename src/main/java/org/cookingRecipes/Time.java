package org.cookingRecipes;

import java.io.*;
import java.util.ArrayList;

public class Time {
    ArrayList<Integer> time = new ArrayList<>();
    public Time(File f){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(f));
            String line;
            while((line = reader.readLine()) != null){
                if(line.contains("~")){
                    time.add(Integer.parseInt(line.substring((line.indexOf("{")+1), line.indexOf("%"))));
                }
            }
            int sum = 0;
            for (Integer i : time) {
                sum += i;
            }
            System.out.printf("\nΣυνολική ώρα:\n\t%d minutes", sum);
            reader.close();
        }
        catch(IOException e){
            System.err.printf("Error: %s\n", e.getMessage());
        }
    }
}
