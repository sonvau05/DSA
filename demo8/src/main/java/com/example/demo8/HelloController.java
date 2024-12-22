package com.example.demo8;

import Entity.Student;
import General.SelectionSort;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;

public class HelloController {
    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField mathMarkField;
    @FXML
    private TextField literatureMarkField;
    @FXML
    private TextField englishMarkField;
    @FXML
    private TableView<Student> studentTableView;
    @FXML
    private TableColumn<Student, String> idColumn;
    @FXML
    private TableColumn<Student, String> nameColumn;
    @FXML
    private TableColumn<Student, Double> mathMarkColumn;
    @FXML
    private TableColumn<Student, Double> literatureMarkColumn;
    @FXML
    private TableColumn<Student, Double> englishMarkColumn;
    @FXML
    private TableColumn<Student, Double> averageMarkColumn;

    private final ArrayList<Student> studentArrayList = new ArrayList<>();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId()));
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        mathMarkColumn.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getMathMark()).asObject());
        literatureMarkColumn.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getLiteratureMark()).asObject());
        englishMarkColumn.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getEnglishMark()).asObject());
        averageMarkColumn.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().averageMark()).asObject());

        updateTable();
    }

    private void updateTable() {
        ObservableList<Student> studentList = FXCollections.observableArrayList(studentArrayList);
        studentTableView.setItems(studentList);
    }

    @FXML
    private void addStudent() {
        try {
            String idInput = idField.getText().trim();
            String name = nameField.getText().trim();

            if (!idInput.matches("\\d+") || Integer.parseInt(idInput) <= 0) {
                showAlert("Invalid Input", "ID must be a positive integer.");
                return;
            }

            int id = Integer.parseInt(idInput);

            if (name.isEmpty()) {
                showAlert("Invalid Input", "Name cannot be empty.");
                return;
            }

            double mathMark = roundToQuarter(Double.parseDouble(mathMarkField.getText()));
            double literatureMark = roundToQuarter(Double.parseDouble(literatureMarkField.getText()));
            double englishMark = roundToQuarter(Double.parseDouble(englishMarkField.getText()));

            if (mathMark < 0 || literatureMark < 0 || englishMark < 0) {
                showAlert("Invalid Input", "Marks cannot be negative.");
                return;
            }

            studentArrayList.add(new Student(String.valueOf(id), name, mathMark, literatureMark, englishMark));
            updateTable();

            idField.clear();
            nameField.clear();
            mathMarkField.clear();
            literatureMarkField.clear();
            englishMarkField.clear();
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Marks must be valid decimal numbers.");
        }
    }

    private double roundToQuarter(double value) {
        return Math.round(value * 4) / 4.0;
    }

    @FXML
    private void sortStudents() {
        SelectionSort.sortByAverageMark(studentArrayList);
        updateTable();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
