package org.cookingRecipes;

public class Time extends Recipe {
    private int hours = 0;
    private int minutes = 0;
    private int seconds = 0;

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
        System.out.println("\nΣυνολική ώρα:");

        boolean isFirst = true; //Μεταβλητή για την εισαγωγή κομμάτων

        System.out.print("\t");

        if (hours > 0) {
            System.out.print(hours + (hours == 1 ? " hour" : " hours"));
            isFirst = false;
        }
        if (minutes > 0) {
            if (!isFirst) System.out.print(", ");
            System.out.print(minutes + (minutes == 1 ? " minute" : " minutes"));
            isFirst = false;
        }
        if (seconds > 0) {
            if (!isFirst) System.out.print(", ");
            System.out.print(seconds + (seconds == 1 ? " second" : " seconds"));
        }

        if (hours == 0 && minutes == 0 && seconds == 0) {
            System.out.print("0 seconds");
        }

        System.out.println();
    }

}
