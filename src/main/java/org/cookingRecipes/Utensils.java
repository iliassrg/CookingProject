package org.cookingRecipes;

import java.util.ArrayList;
import java.util.List;

public class Utensils extends Recipe {
    private List<String> utensils;

    public Utensils() {
        this.utensils = new ArrayList<>();
    }

    public List<String> getUtensils() {
        return new ArrayList<>(utensils);
    }

    public void addUtensil(List<String> utensil) {
        if (utensil != null && !utensil.isEmpty()) {
            //Προσθέτουμε στη λίστα τις λίστες με τα σκεύη από τη κάθε γραμμή που υπάρχουν
            utensils.addAll(utensil);
        }
    }

    @Override
    protected void processLine(String line) {
        List<Integer> position = new ArrayList<>();
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '#') {
                position.add(i);
            }
        }
        // Δημιουργία λίστας subUtensils για την εισαγωγή σκευών από κάθε γραμμή.
        List<String> subUtensils = extractUtensil(line, position);
        addUtensil(subUtensils);
    }

    // Δημιουργία σκευών από τη γραμμή στην οποία βρισκόμαστε.
    private List<String> extractUtensil(String line, List<Integer> position) {
        List<String> subUtensils = new ArrayList<>();
        for(Integer i : position){
            //Όπου i το σημείο στο οποίο παρουσιάζεται ο χαρακτήρας '#'.
            int beginIndex = i + 1;
            int endIndex = line.indexOf("}", beginIndex);
            if (endIndex == -1) {
                //Στην περίπτωση αυτή, το σκεύος είναι μόνο μία λέξη.
                endIndex = findEndIndex(line, beginIndex);
            }

            String utensil;
            if (endIndex == -1) {
                //Στην περίπτωση αυτή, το σκεύος είναι μόνο μία λέξη
                //και βρίσκεται στο τέλος της γραμμής.
                utensil = line.substring(beginIndex);
            } else {
                utensil = line.substring(beginIndex, endIndex);
            }

            subUtensils.add(cleanUtensil(utensil));
        }
        return subUtensils;
    }

    // Βοηθητική μέθοδος για να βρούμε το δείκτη που δείχνει στο τέλος ενός σκεύους
    // Στην περίπτωση αυτή το σκεύος είναι 1 λέξη, αλλά μπορεί να τελειώνει με '.', ',' ή ' '.
    private int findEndIndex(String line, int beginIndex) {
        int[] possibleEndIndices = {
                line.indexOf("}", beginIndex),
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

    // Clean the extracted tag line
    private String cleanUtensil(String utensil) {
        if (utensil.contains("@") || utensil.contains("#") || utensil.contains("~")) {
            //Στην περίπτωση που το σκεύος είναι μία λέξη
            String[] words = utensil.split(" ");
            utensil = words[0];
        }
        if (utensil.endsWith(".") || utensil.endsWith(",")) {
            utensil = utensil.substring(0, utensil.length() - 1);
        }
        utensil = utensil.replace("{"," ");
        return utensil;
    }

    @Override
    public void display() {
        System.out.println("\nΣκεύη:");
        for (String utensil : utensils) {
            System.out.println("\t" + utensil);
        }
    }
}
