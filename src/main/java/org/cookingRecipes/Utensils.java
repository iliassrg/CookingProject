package org.cookingRecipes;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Utensils extends Recipe {
    private List<String> utensils;
    private JTextArea displayArea;  // Αναφορά στην JTextArea

    public Utensils() {
        this.utensils = new ArrayList<>();
    }

    public void setDisplayArea(JTextArea displayArea) {
        this.displayArea = displayArea;
    }

    public List<String> getUtensils() {
        return new ArrayList<>(utensils);
    }

    public void addUtensil(List<String> utensil) {
        if (utensil != null && !utensil.isEmpty()) {
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
        List<String> subUtensils = extractUtensil(line, position);
        addUtensil(subUtensils);
    }

    private List<String> extractUtensil(String line, List<Integer> position) {
        List<String> subUtensils = new ArrayList<>();
        for (Integer i : position) {
            int beginIndex = i + 1;
            int endIndex = line.indexOf("}", beginIndex);
            if (endIndex == -1) {
                endIndex = findEndIndex(line, beginIndex);
            }

            String utensil;
            if (endIndex == -1) {
                utensil = line.substring(beginIndex);
            } else {
                utensil = line.substring(beginIndex, endIndex);
            }

            subUtensils.add(cleanUtensil(utensil));
        }
        return subUtensils;
    }

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

    private String cleanUtensil(String utensil) {
        if (utensil.contains("@") || utensil.contains("#") || utensil.contains("~")) {
            String[] words = utensil.split(" ");
            utensil = words[0];
        }
        if (utensil.endsWith(".") || utensil.endsWith(",")) {
            utensil = utensil.substring(0, utensil.length() - 1);
        }
        utensil = utensil.replace("{", " ");
        return utensil;
    }

    @Override
    public void display() {
        if (displayArea != null) {
            displayArea.append("\nΣκεύη:\n");
            for (String utensil : utensils) {
                displayArea.append("\t" + utensil + "\n");
            }
        }
    }
}