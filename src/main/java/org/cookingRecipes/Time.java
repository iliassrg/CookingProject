package org.cookingRecipes;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Time {
    private List<Integer> time;

    // Constructor
    public Time() {
        this.time = new ArrayList<>();
    }

    // Getter
    public List<Integer> getTime() {
        return new ArrayList<>(time); // Return a copy to preserve encapsulation
    }

    // Add a time entry
    public void addTime(int duration) {
        if (duration > 0) {
            time.add(duration);
        }
    }

    // Load time durations from a file
    public void loadFromFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                processLine(line);
            }
        } catch (IOException e) {
            System.err.printf("Error: %s\n", e.getMessage());
        }
    }

    // Process a single line to extract time
    private void processLine(String line) {
        if (line.contains("~")) {
            Integer duration = extractTime(line);
            if (duration != null) {
                addTime(duration);
            }
        }
    }

    // Extract the time value from the input line
    private Integer extractTime(String line) {
        try {
            int beginIndex = line.indexOf("{") + 1;
            int endIndex = line.indexOf("%", beginIndex);
            if (endIndex == -1) return null; // Guard against missing '%'
            return Integer.parseInt(line.substring(beginIndex, endIndex));
        } catch (NumberFormatException e) {
            System.err.printf("Invalid time format: %s\n", e.getMessage());
            return null;
        }
    }

    // Calculate the total time
    public int getTotalTime() {
        return time.stream().mapToInt(Integer::intValue).sum();
    }

    // Display total time
    public void displayTotalTime() {
        System.out.printf("\nΣυνολική ώρα:\n\t%d minutes\n", getTotalTime());
    }
}
