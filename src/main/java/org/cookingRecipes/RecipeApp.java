package org.cookingRecipes;

import gr.hua.dit.oop2.countdown.Countdown;
import gr.hua.dit.oop2.countdown.CountdownFactory;
import gr.hua.dit.oop2.countdown.Notifier;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecipeApp extends JFrame {
    private JList<String> recipeList;
    private DefaultListModel<String> listModel;
    private JTextArea displayArea;
    private JButton viewButton, shoppingListButton, executeButton;
    private List<File> recipeFiles;
    private List<String> shoppingList;

    // Κοινή γραμματοσειρά για όλα τα στοιχεία
    private final Font appFont = new Font("Arial", Font.PLAIN, 16);

    public RecipeApp() {
        super("Recipe Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);  // Μεγαλύτερο παράθυρο για καλύτερη εμφάνιση
        setLayout(new BorderLayout(10, 10));  // Προσθήκη περιθωρίων

        // Χρώμα φόντου
        getContentPane().setBackground(new Color(245, 245, 245));

        // Λίστα συνταγών
        listModel = new DefaultListModel<>();
        recipeList = new JList<>(listModel);
        recipeList.setFont(appFont);  // Εφαρμογή της κοινής γραμματοσειράς
        recipeList.setBackground(new Color(255, 255, 255));
        recipeList.setSelectionBackground(new Color(200, 220, 255));
        recipeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScrollPane = new JScrollPane(recipeList);
        listScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(listScrollPane, BorderLayout.WEST);

        // Περιοχή εμφάνισης κειμένου
        displayArea = new JTextArea();
        displayArea.setFont(appFont);  // Εφαρμογή της κοινής γραμματοσειράς
        displayArea.setEditable(false);
        displayArea.setBackground(new Color(255, 255, 255));
        displayArea.setLineWrap(true);
        displayArea.setWrapStyleWord(true);
        JScrollPane textScrollPane = new JScrollPane(displayArea);
        textScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(textScrollPane, BorderLayout.CENTER);

        // Πάνελ με κουμπιά
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(245, 245, 245));

        viewButton = createStyledButton("View Recipe", new Color(100, 150, 255));
        shoppingListButton = createStyledButton("Shopping List", new Color(100, 200, 150));
        executeButton = createStyledButton("Execute Recipe", new Color(255, 100, 100));

        buttonPanel.add(viewButton);
        buttonPanel.add(shoppingListButton);
        buttonPanel.add(executeButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Αρχικοποίηση λιστών
        recipeFiles = new ArrayList<>();
        shoppingList = new ArrayList<>();

        // Προσθήκη listeners στα κουμπιά
        viewButton.addActionListener(e -> viewRecipe());
        shoppingListButton.addActionListener(e -> showShoppingList());
        executeButton.addActionListener(e -> executeRecipe());

        // Δημιουργία μενού
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        fileMenu.setFont(appFont);  // Εφαρμογή της κοινής γραμματοσειράς
        JMenuItem openMenuItem = new JMenuItem("Open Recipe");
        openMenuItem.setFont(appFont);  // Εφαρμογή της κοινής γραμματοσειράς
        openMenuItem.addActionListener(e -> openRecipe());
        fileMenu.add(openMenuItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }

    // Δημιουργία κουμπιών με στυλ
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(appFont);  // Εφαρμογή της κοινής γραμματοσειράς
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }

    // Άνοιγμα αρχείων συνταγών
    private void openRecipe() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);
        int option = fileChooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File[] files = fileChooser.getSelectedFiles();
            for (File file : files) {
                recipeFiles.add(file);
                listModel.addElement(file.getName());
            }

            // Εμφάνιση ειδοποίησης με οδηγίες
            JOptionPane.showMessageDialog(this,
                    "Files loaded successfully!\n" +
                            "1. Click on the files you loaded on the top-left.\n" +
                            "2. Then click 'View Recipe', 'Shopping List', or 'Execute Recipe'.",
                    "Files Loaded",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Προβολή της συνταγής
    private void viewRecipe() {
        int selectedIndex = recipeList.getSelectedIndex();
        if (selectedIndex != -1) {
            File file = recipeFiles.get(selectedIndex);
            displayArea.setText("");  // Καθαρίζουμε την περιοχή κειμένου πριν την εμφάνιση

            // Φόρτωση και εμφάνιση των πληροφοριών της συνταγής
            Recipe[] recipes = {new Ingredients(), new Utensils(), new Time()};
            for (Recipe recipe : recipes) {
                if (recipe instanceof Ingredients) {
                    ((Ingredients) recipe).setDisplayArea(displayArea);
                } else if (recipe instanceof Utensils) {
                    ((Utensils) recipe).setDisplayArea(displayArea);
                } else if (recipe instanceof Time) {
                    ((Time) recipe).setDisplayArea(displayArea);
                }
                recipe.loadFromFile(file);
                recipe.display(); // Εμφάνιση των στοιχείων της συνταγής
            }
        }
    }

    // Εμφάνιση λίστας αγορών με τα υλικά όλων των συνταγών
    private void showShoppingList() {
        shoppingList.clear(); // Καθαρισμός της λίστας πριν την εμφάνιση
        for (File file : recipeFiles) {
            Recipe[] recipes = {new Ingredients()};
            for (Recipe recipe : recipes) {
                recipe.loadFromFile(file);
                if (recipe instanceof Ingredients) {
                    shoppingList.addAll(((Ingredients) recipe).getIngredients()); // Προσθήκη των υλικών
                }
            }
        }
        displayArea.setText("Shopping List:\n");
        for (String item : shoppingList) {
            displayArea.append(" - " + item + "\n");
        }
    }

    // Εκτέλεση της συνταγής βήμα προς βήμα
    private void executeRecipe() {
        int selectedIndex = recipeList.getSelectedIndex();
        if (selectedIndex != -1) {
            File file = recipeFiles.get(selectedIndex);
            displaySteps(file); // Εμφάνιση των βημάτων εκτέλεσης της συνταγής
        }
    }

    // Εμφάνιση των βημάτων εκτέλεσης και διαχείριση αντίστροφης μέτρησης
    private void displaySteps(File file) {
        new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                displayArea.setText("");  // Καθαρίζει την περιοχή κειμένου πριν εμφανιστούν τα βήματα

                int stepCounter = 1;
                String line;
                StringBuilder stepContent = new StringBuilder();  // Για την αποθήκευση του περιεχομένου του βήματος

                while ((line = reader.readLine()) != null) {
                    line = line.trim(); // Αφαιρούμε τυχόν κενά

                    if (line.isEmpty()) {
                        // Εμφάνιση του βήματος και ζήτηση επιβεβαίωσης
                        displayStep(stepCounter, stepContent.toString());

                        // Ζητάμε από τον χρήστη να επιβεβαιώσει πριν προχωρήσει
                        int confirm = JOptionPane.showConfirmDialog(this, "Have you completed this step?", "Confirm Step", JOptionPane.YES_NO_OPTION);
                        if (confirm != JOptionPane.YES_OPTION) {
                            displayArea.append("Execution stopped by user.\n");
                            displayArea.setCaretPosition(displayArea.getDocument().getLength());  // Αυτόματο scroll
                            return;
                        }

                        // Εκκαθάριση του περιεχομένου για το επόμενο βήμα
                        stepContent.setLength(0);
                        stepCounter++;
                    } else {
                        // Προσθήκη της γραμμής στο περιεχόμενο του βήματος
                        stepContent.append("   ").append(line).append("\n");
                    }
                }

                // Εμφάνιση του τελευταίου βήματος αν δεν τελειώνει με κενή γραμμή
                if (stepContent.length() > 0) {
                    displayStep(stepCounter, stepContent.toString());

                    // Ζητάμε από τον χρήστη να επιβεβαιώσει πριν τελειώσει
                    int confirm = JOptionPane.showConfirmDialog(this, "Have you completed this step?", "Confirm Step", JOptionPane.YES_NO_OPTION);
                    if (confirm != JOptionPane.YES_OPTION) {
                        displayArea.append("Execution stopped by user.\n");
                        displayArea.setCaretPosition(displayArea.getDocument().getLength());  // Αυτόματο scroll
                    }
                }
            } catch (IOException e) {
                displayArea.append("Error: " + e.getMessage() + "\n");
                displayArea.setCaretPosition(displayArea.getDocument().getLength());  // Αυτόματο scroll
            }
        }).start();
    }

    // Εμφάνιση βήματος και διαχείριση της αντίστροφης μέτρησης
    private void displayStep(int stepCounter, String stepContent) {
        displayArea.append("\nStep " + stepCounter + ":\n");
        displayArea.append(stepContent);
        displayArea.setCaretPosition(displayArea.getDocument().getLength());  // Αυτόματο scroll

        // Έλεγχος για χρόνο στο βήμα
        if (stepContent.contains("~")) {
            String[] lines = stepContent.split("\n");
            for (String line : lines) {
                if (line.contains("~")) {
                    int beginIndex = line.indexOf("{") + 1;
                    int endIndex = line.indexOf("}", beginIndex);
                    String timeSegment = line.substring(beginIndex, endIndex);

                    long seconds = 0;
                    if (timeSegment.contains("hours")) {
                        seconds += Integer.parseInt(timeSegment.replace("%hours", "").trim()) * 3600;
                    }
                    if (timeSegment.contains("minutes")) {
                        seconds += Integer.parseInt(timeSegment.replace("%minutes", "").trim()) * 60;
                    }
                    if (timeSegment.contains("seconds")) {
                        seconds += Integer.parseInt(timeSegment.replace("%seconds", "").trim());
                    }

                    // Δημιουργία αντίστροφης μέτρησης
                    Countdown countdown = CountdownFactory.countdown(seconds);
                    countdown.addNotifier(new Notifier() {
                        @Override
                        public void finished(Countdown c) {
                            displayArea.append("Time's up! Please confirm to proceed to the next step.\n");
                            displayArea.setCaretPosition(displayArea.getDocument().getLength());  // Αυτόματο scroll
                        }
                    });

                    // Εμφάνιση αντίστροφης μέτρησης
                    countdown.start();
                    while (countdown.secondsRemaining() > 0) {
                        displayArea.append("Time remaining: " + countdown.secondsRemaining() + " seconds\n");
                        displayArea.setCaretPosition(displayArea.getDocument().getLength());  // Αυτόματο scroll
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            displayArea.append("Error: " + e.getMessage() + "\n");
                            displayArea.setCaretPosition(displayArea.getDocument().getLength());  // Αυτόματο scroll
                        }
                    }
                }
            }
        }
    }

    // Εκκίνηση της εφαρμογής
    public static void launchApp() {
        SwingUtilities.invokeLater(() -> {
            RecipeApp app = new RecipeApp();
            app.setVisible(true);

            // Ειδοποίηση κατά την έναρξη
            JOptionPane.showMessageDialog(app,
                    "Please click on 'File' in the top-left corner to load recipe files.",
                    "Welcome to Recipe Application",
                    JOptionPane.INFORMATION_MESSAGE);
        });
    }
}