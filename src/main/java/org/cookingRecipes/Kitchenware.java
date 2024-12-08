package org.cookingRecipes;

import java.io.*;
import java.util.ArrayList;

public class Kitchenware {
    ArrayList<String> kitchenware = new ArrayList<>();

    public Kitchenware(File f) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(f));
            String line;

            while ((line = reader.readLine()) != null) {
                // Επεξεργασία γραμμής για σκεύη
                String[] words = line.split(" "); // Διαχωρισμός γραμμής σε λέξεις

                // Ελέγχουμε αν κάποια λέξη ξεκινά με '#'
                for (String word : words) {
                    if (word.startsWith("#")) { // Αν η λέξη ξεκινάει με '#'
                        // Αφαίρεση του '#' και προσθήκη της λέξης στη λίστα
                        String item = word.substring(1); // Αφαιρούμε το '#'

                        // Αν το σκεύος είναι μία φράση (π.χ., "μεγάλο αντικολλητικό τηγάνι")
                        // πρέπει να προσθέσουμε και τις υπόλοιπες λέξεις
                        if (item.contains(" ")) {
                            kitchenware.add(item); // Αν είναι φράση, προσθέτουμε ολόκληρο το σκεύος
                        } else {
                            kitchenware.add(item); // Αν είναι μόνο μία λέξη, το προσθέτουμε απευθείας
                        }
                    }
                }
            }

            // Εμφάνιση των σκευών
            System.out.println("\nΣκεύη:");
            for (String k : kitchenware) {
                System.out.println(k);
            }

            reader.close();
        } catch (IOException e) {
            System.err.printf("Error: %s\n", e.getMessage());
        }
    }
}