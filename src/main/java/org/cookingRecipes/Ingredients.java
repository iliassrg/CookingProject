package org.cookingRecipes;


public class Ingredients {
    private String name; // Όνομα του υλικού
    private double quantity; // Ποσότητα
    private String unit; // Μονάδα μέτρησης (π.χ. gr, ml, κ.σ.)

    // Κατασκευαστής
    public Ingredients(String name, double quantity, String unit) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    // Getters και Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    // Μέθοδος για μετατροπή ποσότητας (π.χ. από gr σε kg)
    public void convertQuantity(double factor, String newUnit) {
        this.quantity *= factor;
        this.unit = newUnit;
    }

    // toString για ευανάγνωστη αναπαράσταση
    @Override
    public String toString() {
        return name + " " + quantity + (unit.isEmpty() ? "" : " " + unit);
    }

    // Μέθοδος equals για σύγκριση υλικών βάσει ονόματος
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Ingredients that = (Ingredients) obj;
        return name.equalsIgnoreCase(that.name); // Σύγκριση ανεξάρτητη κεφαλαίων
    }

    // hashCode (χρήσιμο για δομές όπως HashMap ή HashSet)
    @Override
    public int hashCode() {
        return name.toLowerCase().hashCode();
    }
}
