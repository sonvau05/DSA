package Entity;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Income {
    private SimpleStringProperty source;
    private SimpleDoubleProperty amount;
    private SimpleStringProperty note;

    public Income(String source, double amount, String note) {
        this.source = new SimpleStringProperty(source);
        this.amount = new SimpleDoubleProperty(amount);
        this.note = new SimpleStringProperty(note);
    }

    public String getSource() {
        return source.get();
    }

    public void setSource(String source) {
        this.source.set(source);
    }

    public double getAmount() {
        return amount.get();
    }

    public void setAmount(double amount) {
        this.amount.set(amount);
    }

    public String getNote() {
        return note.get();
    }

    public void setNote(String note) {
        this.note.set(note);
    }

    public SimpleStringProperty sourceProperty() {
        return source;
    }

    public SimpleDoubleProperty amountProperty() {
        return amount;
    }

    public SimpleStringProperty noteProperty() {
        return note;
    }
}
