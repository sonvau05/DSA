package com.example.demo7;

import Entity.Student;
import General.BubbleSort;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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

    private final ObservableList<Student> studentList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId()));
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        mathMarkColumn.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getMathMark()).asObject());
        literatureMarkColumn.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getLiteratureMark()).asObject());
        englishMarkColumn.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getEnglishMark()).asObject());
        averageMarkColumn.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().averageMark()).asObject());

        studentTableView.setItems(studentList);
    }

    @FXML
    private void addStudent() {
        try {
            String id = idField.getText();
            String name = nameField.getText();

            if (name.isEmpty()) {
                showAlert("Invalid Input", "Name cannot be empty.");
                return;
            }

            double mathMark = Double.parseDouble(mathMarkField.getText());
            double literatureMark = Double.parseDouble(literatureMarkField.getText());
            double englishMark = Double.parseDouble(englishMarkField.getText());

            studentList.add(new Student(id, name, mathMark, literatureMark, englishMark));

            idField.clear();
            nameField.clear();
            mathMarkField.clear();
            literatureMarkField.clear();
            englishMarkField.clear();
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Marks must be numbers.");
        }
    }

    @FXML
    private void sortStudents() {
        // Sort students by average mark (default) or math/literature/english if desired
        BubbleSort.sortByAverageMark(studentList);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
