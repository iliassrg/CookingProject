package org.cookingRecipes;
import javax.swing.*;

public class Time extends Recipe {
    private int hours = 0;
    private int minutes = 0;
    private int seconds = 0;
    private JTextArea displayArea;  // Αναφορά στην JTextArea

    public void setDisplayArea(JTextArea displayArea) {
        this.displayArea = displayArea;
    }

    @Override
    protected void processLine(String line) {
        if (line.contains("~") && line.contains("{")) {
            int beginIndex = line.indexOf("{") + 1;
            int endIndex = line.indexOf("}", beginIndex);
            String timeSegment = line.substring(beginIndex, endIndex);

            if (timeSegment.contains("hours")) {
                hours += Integer.parseInt(timeSegment.replace("%hours", "").trim());
            } else if (timeSegment.contains("minutes")) {
                minutes += Integer.parseInt(timeSegment.replace("%minutes", "").trim());
            } else if (timeSegment.contains("seconds")) {
                seconds += Integer.parseInt(timeSegment.replace("%seconds", "").trim());
            }
        }
    }

    @Override
    public void display() {
        if (displayArea != null) {
            displayArea.append("\nΣυνολική ώρα:\n");

            boolean isFirst = true;
            displayArea.append("\t");

            if (hours > 0) {
                displayArea.append(hours + (hours == 1 ? " hour" : " hours"));
                isFirst = false;
            }
            if (minutes > 0) {
                if (!isFirst) displayArea.append(", ");
                displayArea.append(minutes + (minutes == 1 ? " minute" : " minutes"));
                isFirst = false;
            }
            if (seconds > 0) {
                if (!isFirst) displayArea.append(", ");
                displayArea.append(seconds + (seconds == 1 ? " second" : " seconds"));
            }

            if (hours == 0 && minutes == 0 && seconds == 0) {
                displayArea.append("0 seconds");
            }

            displayArea.append("\n");
        }
    }
}