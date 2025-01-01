package Service;

import Entity.Expense;

import java.util.ArrayList;
import java.util.List;

public class ExpenseService {
    private List<Expense> expenses = new ArrayList<>();

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void clearHistory() {
        expenses.clear();
    }
}
