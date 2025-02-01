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
    private List<String> ingredientsList;

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
        recipeList.setFont(new Font("Arial", Font.PLAIN, 14));
        recipeList.setBackground(new Color(255, 255, 255));
        recipeList.setSelectionBackground(new Color(200, 220, 255));
        recipeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScrollPane = new JScrollPane(recipeList);
        listScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(listScrollPane, BorderLayout.WEST);

        // Περιοχή εμφάνισης κειμένου
        displayArea = new JTextArea();
        displayArea.setFont(new Font("Arial", Font.PLAIN, 14));
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
        ingredientsList = new ArrayList<>();

        // Προσθήκη listeners στα κουμπιά
        viewButton.addActionListener(e -> viewRecipe());
        shoppingListButton.addActionListener(e -> showShoppingList());
        executeButton.addActionListener(e -> executeRecipe());

        // Δημιουργία μενού
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        fileMenu.setFont(new Font("Arial", Font.PLAIN, 14));
        JMenuItem openMenuItem = new JMenuItem("Open Recipe");
        openMenuItem.setFont(new Font("Arial", Font.PLAIN, 14));
        openMenuItem.addActionListener(e -> openRecipe());
        fileMenu.add(openMenuItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }

    // Δημιουργία κουμπιών με στυλ
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
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
        }
    }

    // Προβολή συνταγής
    private void viewRecipe() {
        int selectedIndex = recipeList.getSelectedIndex();
        if (selectedIndex != -1) {
            File file = recipeFiles.get(selectedIndex);
            displayArea.setText("");  // Καθαρίζουμε την περιοχή κειμένου

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
                recipe.display();
            }
        }
    }

    // Εμφάνιση λίστας αγορών
    private void showShoppingList() {
        ingredientsList.clear();
        for (File file : recipeFiles) {
            Recipe[] recipes = {new Ingredients()};
            for (Recipe recipe : recipes) {
                recipe.loadFromFile(file);
                if (recipe instanceof Ingredients) {
                    ingredientsList.addAll(((Ingredients) recipe).getIngredients());
                }
            }
        }
        displayArea.setText("Shopping List:\n");
        for (String item : ingredientsList) {
            displayArea.append(" - " + item + "\n");
        }
    }

    // Εκτέλεση συνταγής (βήματα και αντίστροφη μέτρηση)
    private void executeRecipe() {
        int selectedIndex = recipeList.getSelectedIndex();
        if (selectedIndex != -1) {
            File file = recipeFiles.get(selectedIndex);
            displaySteps(file);
        }
    }

    // Εμφάνιση βημάτων και διαχείριση αντίστροφης μέτρησης
    private void displaySteps(File file) {
        new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                int stepCounter = 1;
                String line;
                boolean newStep = true; // Για τον εντοπισμό νέων βημάτων

                while ((line = reader.readLine()) != null) {
                    line = line.trim(); // Αφαιρούμε τυχόν κενά
                    if (line.isEmpty()) {
                        newStep = true; // Ενεργοποιούμε τη σημαία για νέο βήμα
                        continue; // Αγνοούμε την κενή γραμμή
                    }

                    // Αν βρέθηκε κενή γραμμή πριν, αύξησε το μετρητή και ξεκίνησε νέο βήμα
                    if (newStep) {
                        displayArea.append("\nStep " + stepCounter + ":\n");
                        stepCounter++;
                        newStep = false; // Απενεργοποιούμε τη σημαία μέχρι την επόμενη κενή γραμμή
                    }

                    // Εμφάνιση περιεχομένου του βήματος
                    displayArea.append("   " + line + "\n");
                    displayArea.setCaretPosition(displayArea.getDocument().getLength());  // Αυτόματο scroll

                    // Έλεγχος για χρόνο στο βήμα
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
                            Thread.sleep(1000);
                        }
                    }

                    // Ζητάμε από τον χρήστη να επιβεβαιώσει πριν προχωρήσει
                    int confirm = JOptionPane.showConfirmDialog(this, "Have you completed this step?", "Confirm Step", JOptionPane.YES_NO_OPTION);
                    if (confirm != JOptionPane.YES_OPTION) {
                        displayArea.append("Execution stopped by user.\n");
                        displayArea.setCaretPosition(displayArea.getDocument().getLength());  // Αυτόματο scroll
                        return;
                    }
                }
            } catch (IOException | InterruptedException e) {
                displayArea.append("Error: " + e.getMessage() + "\n");
                displayArea.setCaretPosition(displayArea.getDocument().getLength());  // Αυτόματο scroll
            }
        }).start();
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