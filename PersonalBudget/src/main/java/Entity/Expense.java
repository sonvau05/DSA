package Entity;

import javafx.beans.property.*;

public class Expense {
    private StringProperty month;
    private StringProperty category;
    private DoubleProperty amount;

    public Expense(String month, String category, double amount, String note) {
        this.month = new SimpleStringProperty(month);
        this.category = new SimpleStringProperty(category);
        this.amount = new SimpleDoubleProperty(amount);
    }

    public String getMonth() {
        return month.get();
    }

    public StringProperty monthProperty() {
        return month;
    }

    public String getCategory() {
        return category.get();
    }

    public StringProperty categoryProperty() {
        return category;
    }

    public double getAmount() {
        return amount.get();
    }

    public DoubleProperty amountProperty() {
        return amount;
    }

}
