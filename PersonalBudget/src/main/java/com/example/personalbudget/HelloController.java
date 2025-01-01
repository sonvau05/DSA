package com.example.personalbudget;

import Entity.Expense;
import Entity.Income;
import Service.ExpenseService;
import Service.IncomeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.*;

public class HelloController {

    @FXML
    private ComboBox<String> monthComboBox;

    @FXML
    private ComboBox<String> categoryComboBox;

    @FXML
    private TextField amountTextField;

    @FXML
    private Label totalExpenseText;

    @FXML
    private StackedBarChart<String, Number> expenseBarChart;

    @FXML
    private CategoryAxis monthAxis;

    @FXML
    private NumberAxis expenseYAxis;

    @FXML
    private PieChart expensePieChart;

    @FXML
    private Label analysisLabel;

    @FXML
    private VBox categoryBreakdown;

    @FXML
    private TableView<Expense> expenseHistoryTable;

    @FXML
    private TableColumn<Expense, String> monthColumn;

    @FXML
    private TableColumn<Expense, String> categoryColumn;

    @FXML
    private TableColumn<Expense, Double> amountColumn;

    @FXML
    private TableColumn<Expense, String> noteColumn;

    @FXML
    private TableView<Expense> monthlyExpenseTable;

    @FXML
    private TableColumn<Expense, String> monthlyMonthColumn;

    @FXML
    private TableColumn<Expense, String> monthlyCategoryColumn;

    @FXML
    private TableColumn<Expense, Double> monthlyTotalExpenseColumn;

    @FXML
    private TableColumn<Expense, String> monthlyAnalysisColumn;

    @FXML
    private TableView<Expense> yearlyExpenseTable;

    @FXML
    private TableColumn<Expense, String> yearlyMonthColumn;

    @FXML
    private TableColumn<Expense, String> yearlyHighestExpenseColumn;

    @FXML
    private TableColumn<Expense, String> yearlyLowestExpenseColumn;

    @FXML
    private TableColumn<Expense, Double> yearlyTotalExpenseColumn;

    @FXML
    private ComboBox<String> incomeSourceComboBox;

    @FXML
    private TextField incomeAmountTextField;

    @FXML
    private Label totalIncomeText;

    @FXML
    private TableView<Income> incomeHistoryTable;

    @FXML
    private TableColumn<Income, String> incomeSourceColumn;

    @FXML
    private TableColumn<Income, Double> incomeAmountColumn;

    @FXML
    private TableColumn<Income, String> incomeNoteColumn;

    private ExpenseService expenseService = new ExpenseService();
    private IncomeService incomeService = new IncomeService();
    private ObservableList<Expense> expenseList = FXCollections.observableArrayList();
    private ObservableList<Income> incomeList = FXCollections.observableArrayList();
    private Map<String, Map<String, Double>> expenseData = new HashMap<>();
    private Map<String, Double> monthlyExpenseAnalysis = new HashMap<>();
    private Map<String, Map.Entry<String, Double>> highestExpenseByMonth = new HashMap<>();
    private Map<String, Map.Entry<String, Double>> lowestExpenseByMonth = new HashMap<>();

    @FXML
    public void initialize() {
        monthComboBox.setItems(FXCollections.observableArrayList(
                "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6",
                "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"
        ));

        categoryComboBox.setItems(FXCollections.observableArrayList(
                "Ăn uống", "Mua sắm", "Giải trí", "Học tập", "Đi lại", "Điện nước", "Khác"
        ));

        incomeSourceComboBox.setItems(FXCollections.observableArrayList("Lương", "Kinh doanh", "Khác"));

        monthColumn.setCellValueFactory(cellData -> cellData.getValue().monthProperty());
        categoryColumn.setCellValueFactory(cellData -> cellData.getValue().categoryProperty());
        amountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());

        incomeSourceColumn.setCellValueFactory(cellData -> cellData.getValue().sourceProperty());
        incomeAmountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
        incomeNoteColumn.setCellValueFactory(cellData -> cellData.getValue().noteProperty());

        expenseHistoryTable.setItems(expenseList);
        incomeHistoryTable.setItems(incomeList);

        monthlyMonthColumn.setCellValueFactory(cellData -> cellData.getValue().monthProperty());
        monthlyCategoryColumn.setCellValueFactory(cellData -> cellData.getValue().categoryProperty());
        monthlyTotalExpenseColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
        monthlyAnalysisColumn.setCellValueFactory(cellData -> {
            Expense expense = cellData.getValue();
            double amount = expense.getAmount();
            String analysis;

            if (amount > 10000000) {
                analysis = "Cần phải tiết kiệm hơn";
            } else if (amount >= 5000000) {
                analysis = "Quản lí chi tiêu ổn";
            } else {
                analysis = "Chi tiêu rất tốt";
            }
            return new javafx.beans.property.SimpleStringProperty(analysis);
        });

        yearlyMonthColumn.setCellValueFactory(cellData -> cellData.getValue().monthProperty());
        yearlyHighestExpenseColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCategory()));

        setupCharts();
        updateExpenseTables();
        updateIncomeTables();
    }

    public void updateExpenseTables() {
        updateMonthlyExpenseTable();
        updateYearlyExpenseTable();
    }

    public void updateIncomeTables() {
        updateTotalIncome();
    }

    private void setupCharts() {
        monthAxis.setLabel("Tháng");
        expenseYAxis.setLabel("Tổng Chi Tiêu (VND)");

        expensePieChart.setTitle("Chi Tiêu Theo Danh Mục");
        expenseBarChart.setTitle("Chi Tiêu Tháng");
    }

    @FXML
    public void addExpense(ActionEvent event) {
        String month = monthComboBox.getValue();
        String category = categoryComboBox.getValue();
        double amount;

        if (month == null || category == null) {
            showAlert("Lỗi", "Vui lòng chọn tháng và danh mục!", Alert.AlertType.ERROR);
            return;
        }

        try {
            amount = Double.parseDouble(amountTextField.getText());
        } catch (NumberFormatException e) {
            showAlert("Lỗi", "Số tiền không hợp lệ!", Alert.AlertType.ERROR);
            return;
        }

        Expense expense = new Expense(month, category, amount, "Không có ghi chú");

        expenseService.addExpense(expense);
        expenseList.add(expense);

        expenseData.computeIfAbsent(month, k -> new HashMap<>())
                .merge(category, amount, Double::sum);

        Map<String, Double> monthExpenses = expenseData.get(month);

        Optional<Map.Entry<String, Double>> highestExpense = monthExpenses.entrySet().stream()
                .max(Map.Entry.comparingByValue());

        Optional<Map.Entry<String, Double>> lowestExpense = monthExpenses.entrySet().stream()
                .min(Map.Entry.comparingByValue());

        highestExpense.ifPresent(entry -> highestExpenseByMonth.put(month, entry));
        lowestExpense.ifPresent(entry -> lowestExpenseByMonth.put(month, entry));

        monthlyExpenseAnalysis.put(month, monthlyExpenseAnalysis.getOrDefault(month, 0.0) + amount);

        double totalExpense = expenseService.getExpenses().stream().mapToDouble(Expense::getAmount).sum();
        totalExpenseText.setText("Tổng chi tiêu hiện tại: " + totalExpense + " VND");

        updateCharts();
        updateExpenseTables();

        expenseHistoryTable.setItems(expenseList);
        expenseHistoryTable.refresh();

        amountTextField.clear();
    }

    @FXML
    public void addIncome(ActionEvent event) {
        String source = incomeSourceComboBox.getValue();
        double amount;

        if (source == null) {
            showAlert("Lỗi", "Vui lòng chọn nguồn thu nhập!", Alert.AlertType.ERROR);
            return;
        }

        try {
            amount = Double.parseDouble(incomeAmountTextField.getText());
        } catch (NumberFormatException e) {
            showAlert("Lỗi", "Số tiền không hợp lệ!", Alert.AlertType.ERROR);
            return;
        }

        Income income = new Income(source, amount, "Không có ghi chú");
        incomeService.addIncome(income);
        incomeList.add(income);

        updateTotalIncome();

        incomeHistoryTable.refresh();
        incomeAmountTextField.clear();
    }

    private void updateTotalIncome() {
        double totalIncome = incomeService.getIncomes().stream().mapToDouble(Income::getAmount).sum();
        totalIncomeText.setText("Tổng thu nhập hiện tại: " + totalIncome + " VND");
    }

    private void updateMonthlyExpenseTable() {
        ObservableList<Expense> monthlyExpenses = FXCollections.observableArrayList();
        expenseData.forEach((month, categories) -> {
            categories.forEach((category, amount) -> {
                monthlyExpenses.add(new Expense(month, category, amount, ""));
            });
        });
        monthlyExpenseTable.setItems(monthlyExpenses);
    }

    private void updateYearlyExpenseTable() {
        ObservableList<Expense> yearlyExpenses = FXCollections.observableArrayList();

        expenseData.forEach((month, categories) -> {
            double totalMonthExpense = categories.values().stream().mapToDouble(Double::doubleValue).sum();
            Map.Entry<String, Double> highest = highestExpenseByMonth.get(month);

            if (highest != null) {
                yearlyExpenses.add(new Expense(month, highest.getKey(), totalMonthExpense, ""));
            }
        });

        yearlyExpenseTable.setItems(yearlyExpenses);
        yearlyTotalExpenseColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getAmount()).asObject());
        yearlyHighestExpenseColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCategory()));
    }

    private void updateCharts() {
        expenseBarChart.getData().clear();
        expensePieChart.getData().clear();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Chi tiêu theo tháng");

        expenseData.forEach((month, categories) -> {
            double totalExpenseForMonth = categories.values().stream().mapToDouble(Double::doubleValue).sum();
            series.getData().add(new XYChart.Data<>(month, totalExpenseForMonth));
        });
        expenseBarChart.getData().add(series);

        Map<String, Double> cumulativeCategoryData = new HashMap<>();
        expenseData.forEach((month, categories) -> {
            categories.forEach((category, amount) -> {
                cumulativeCategoryData.merge(category, amount, Double::sum);
            });
        });

        cumulativeCategoryData.forEach((category, amount) -> {
            PieChart.Data slice = new PieChart.Data(category, amount);
            expensePieChart.getData().add(slice);
        });
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    public void clearHistory(ActionEvent event) {
        expenseService.clearHistory();
        expenseList.clear();
        expenseHistoryTable.setItems(expenseList);
        totalExpenseText.setText("Tổng chi tiêu hiện tại: 0 VND");
    }
}
