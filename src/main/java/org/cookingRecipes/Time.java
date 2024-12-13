package org.cookingRecipes;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Time {
    private List<Integer> hours;
    private List<Integer> minutes;
    private List<Integer> seconds;

    // Constructor
    public Time() {
        this.hours = new ArrayList<>();
        this.minutes = new ArrayList<>();
        this.seconds = new ArrayList<>();
    }

    // Getters
    public List<Integer> getHours() {
        return new ArrayList<>(hours); // Return a copy to preserve encapsulation
    }

    public List<Integer> getMinutes() {
        return new ArrayList<>(minutes);
    }

    public List<Integer> getSeconds() {
        return new ArrayList<>(seconds);
    }

    // Add a time entry
    public void addTime(int hour, int minute, int second) {
        if (hour >= 0 && minute >= 0 && second >= 0) {
            hours.add(hour);
            minutes.add(minute);
            seconds.add(second);
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
        if (line.contains("~") && line.contains("{")) {
            int[] timeParts = extractTime(line);
            if (timeParts != null) {
                addTime(timeParts[0], timeParts[1], timeParts[2]);
            }
        }
    }

    // Extract the time value from the input line
    private int[] extractTime(String line) {
        try {
            int beginIndex = line.indexOf("{") + 1;
            int endIndex = line.indexOf("}", beginIndex);
            if (endIndex == -1) return null; // Guard against missing '%'

            String timeSegment = line.substring(beginIndex, endIndex);
            int hour = 0, minute = 0, second = 0;

            if (timeSegment.contains("hours")) {
                hour = Integer.parseInt(timeSegment.replace("%hours", "").trim());
            } else if (timeSegment.contains("minutes")) {
                minute = Integer.parseInt(timeSegment.replace("%minutes", "").trim());
            } else if (timeSegment.contains("seconds")) {
                second = Integer.parseInt(timeSegment.replace("%seconds", "").trim());
            }

            return new int[]{hour, minute, second};
        } catch (NumberFormatException e) {
            System.err.printf("Invalid time format: %s\n", e.getMessage());
            return null;
        }
    }

    // Display total time
    public void displayTotalTime() {
        System.out.println("\nΣυνολική ώρα:");
        int sumOfHours = 0;
        int sumOfMinutes = 0;
        int sumOfSeconds = 0;
        for(Integer h : hours){
            sumOfHours += h;
        }
        for(Integer m : minutes){
            sumOfMinutes += m;
        }
        for(Integer s : seconds){
            sumOfSeconds += s;
        }
        if(sumOfHours != 0){
            if(sumOfHours == 1){
                System.out.print(sumOfHours + " hour");
            }
            else{
                System.out.print(sumOfHours + " hours");
            }
        }
        if(sumOfMinutes != 0){
            if(sumOfHours != 0){
                System.out.print(" ");
            }
            if(sumOfMinutes == 1){
                System.out.print(sumOfMinutes + " minute");
            }
            else{
                System.out.print(sumOfMinutes + " minutes");
            }

        }
        if(sumOfSeconds != 0){
            if(sumOfHours != 0 || sumOfMinutes != 0){
                System.out.print(" ");
            }
            if(sumOfSeconds == 1){
                System.out.print(sumOfSeconds + " second");
            }
            else{
                System.out.print(sumOfSeconds + " seconds");
            }

        }
        System.out.println();
    }
}
