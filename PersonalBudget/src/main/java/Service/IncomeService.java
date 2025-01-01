package Service;

import Entity.Income;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class IncomeService {
    private ObservableList<Income> incomes = FXCollections.observableArrayList();

    public void addIncome(Income income) {
        incomes.add(income);
    }

    public void editIncome(Income oldIncome, Income newIncome) {
        int index = incomes.indexOf(oldIncome);
        if (index >= 0) {
            incomes.set(index, newIncome);
        }
    }

    public void deleteIncome(Income income) {
        incomes.remove(income);
    }

    public ObservableList<Income> getIncomes() {
        return incomes;
    }

    public ObservableList<Income> searchIncomeHistory(String source) {
        ObservableList<Income> result = FXCollections.observableArrayList();
        for (Income income : incomes) {
            if (income.getSource().equalsIgnoreCase(source)) {
                result.add(income);
            }
        }
        return result;
    }
}
