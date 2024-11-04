import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GradeBookApp extends Application {

    private TextField firstNameField;
    private TextField lastNameField;
    private TextField courseField;
    private ComboBox<String> gradeComboBox;
    private TextArea resultArea;

    private static final String FILE_NAME = "grades.csv";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Grade Book App");


        firstNameField = new TextField();
        lastNameField = new TextField();
        courseField = new TextField();
        gradeComboBox = new ComboBox<>();
        gradeComboBox.getItems().addAll("A", "B", "C", "D", "E", "F");

        Button saveButton = new Button("Save Grade Entry");
        Button clearButton = new Button("Clear Form");
        Button viewButton = new Button("View Grades");

        resultArea = new TextArea();
        resultArea.setEditable(false);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(new Label("First Name:"), 0, 0);
        grid.add(firstNameField, 1, 0);
        grid.add(new Label("Last Name:"), 0, 1);
        grid.add(lastNameField, 1, 1);
        grid.add(new Label("Course:"), 0, 2);
        grid.add(courseField, 1, 2);
        grid.add(new Label("Grade:"), 0, 3);
        grid.add(gradeComboBox, 1, 3);
        grid.add(saveButton, 0, 4);
        grid.add(clearButton, 1, 4);
        grid.add(viewButton, 0, 5);
        grid.add(new Label("Saved Grades:"), 0, 6);
        grid.add(resultArea, 0, 7, 2, 1);

        // Button actions
        saveButton.setOnAction(e -> saveGrade());
        clearButton.setOnAction(e -> clearForm());
        viewButton.setOnAction(e -> viewGrades());

        // Setup scene
        Scene scene = new Scene(grid, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void saveGrade() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String course = courseField.getText();
        String grade = gradeComboBox.getValue();

        if (firstName.isEmpty() || lastName.isEmpty() || course.isEmpty() || grade == null) {
            showAlert("Please fill in all fields.");
            return;
        }

        Student student = new Student(firstName, lastName, course, grade);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(student.getFirstName() + "," + student.getLastName() + "," +
                    student.getCourse() + "," + student.getGrade());
            writer.newLine();
            clearForm();
        } catch (IOException e) {
            showAlert("Error saving the grade entry.");
        }
    }

    private void clearForm() {
        firstNameField.clear();
        lastNameField.clear();
        courseField.clear();
        gradeComboBox.setValue(null);
    }

    private void viewGrades() {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            resultArea.setText(content.toString());
        } catch (IOException e) {
            showAlert("Error reading the grades file.");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
