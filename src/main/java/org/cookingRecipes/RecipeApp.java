package org.cookingRecipes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        setSize(800, 600);  // Μεγαλύτερο παράθυρο για καλύτερη εμφάνιση
        setLayout(new BorderLayout());

        // Λίστα συνταγών
        listModel = new DefaultListModel<>();
        recipeList = new JList<>(listModel);
        add(new JScrollPane(recipeList), BorderLayout.WEST);

        // Περιοχή εμφάνισης κειμένου
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        // Πάνελ με κουμπιά
        JPanel buttonPanel = new JPanel();
        viewButton = new JButton("View Recipe");
        shoppingListButton = new JButton("Shopping List");
        executeButton = new JButton("Execute Recipe");
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
        JMenuItem openMenuItem = new JMenuItem("Open Recipe");
        openMenuItem.addActionListener(e -> openRecipe());
        fileMenu.add(openMenuItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
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
            displayArea.setText("");
            Recipe[] recipes = {new Ingredients(), new Utensils(), new Time()};
            for (Recipe recipe : recipes) {
                recipe.loadFromFile(file);
                recipe.display();
                displayArea.append(recipe.toString() + "\n");
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
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            int stepCounter = 1;
            boolean newStep = true;
            String line;
            displayArea.setText("Steps:\n");
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    stepCounter++;
                    newStep = true;
                    displayArea.append("\n");
                } else {
                    if (newStep) {
                        displayArea.append("\t" + stepCounter + ". " + line + "\n");
                        newStep = false;
                    } else {
                        displayArea.append("\t   " + line + "\n");
                    }
                }
            }
        } catch (IOException e) {
            displayArea.setText("Error: " + e.getMessage());
        }
    }

    // Εκκίνηση της εφαρμογής
    public static void launchApp() {
        SwingUtilities.invokeLater(() -> new RecipeApp().setVisible(true));
    }
}