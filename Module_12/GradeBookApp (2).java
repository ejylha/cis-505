import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GradeBookApp extends Application {

    private TextField firstNameField, lastNameField, courseField, filterField;
    private ComboBox<String> gradeComboBox;
    private TableView<GradeEntry> gradeTable;
    private List<GradeEntry> gradeEntries = new ArrayList<>();

    @SuppressWarnings({ "unchecked", "unused" })
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("GradeBook App");

        // Form input fields and buttons
        firstNameField = new TextField();
        lastNameField = new TextField();
        courseField = new TextField();
        filterField = new TextField();
        gradeComboBox = new ComboBox<>();
        gradeComboBox.getItems().addAll("A", "B", "C", "D", "F");

        Button saveButton = new Button("Save");
        Button clearButton = new Button("Clear Form");
        Button filterButton = new Button("Filter");
        Button clearFilterButton = new Button("Clear Filter");

        saveButton.setOnAction(e -> saveGradeEntry());
        clearButton.setOnAction(e -> clearForm());
        filterButton.setOnAction(e -> filterGrades());
        clearFilterButton.setOnAction(e -> clearFilter());

        // Layout for the form
        GridPane formPane = new GridPane();
        formPane.setPadding(new Insets(10));
        formPane.setHgap(10);
        formPane.setVgap(10);

        formPane.add(new Label("First Name:"), 0, 0);
        formPane.add(firstNameField, 1, 0);
        formPane.add(new Label("Last Name:"), 0, 1);
        formPane.add(lastNameField, 1, 1);
        formPane.add(new Label("Course Name:"), 0, 2);
        formPane.add(courseField, 1, 2);
        formPane.add(new Label("Grade:"), 0, 3);
        formPane.add(gradeComboBox, 1, 3);
        formPane.add(saveButton, 0, 4);
        formPane.add(clearButton, 1, 4);

        // Filter layout
        HBox filterBox = new HBox(10);
        filterBox.setPadding(new Insets(10));
        filterBox.getChildren().addAll(new Label("Filter by Course Name:"), filterField, filterButton,
                clearFilterButton);

        // Table setup
        gradeTable = new TableView<>();
        TableColumn<GradeEntry, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<GradeEntry, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<GradeEntry, String> courseCol = new TableColumn<>("Course");
        courseCol.setCellValueFactory(new PropertyValueFactory<>("course"));

        TableColumn<GradeEntry, String> gradeCol = new TableColumn<>("Grade");
        gradeCol.setCellValueFactory(new PropertyValueFactory<>("grade"));

        gradeTable.getColumns().addAll(firstNameCol, lastNameCol, courseCol, gradeCol);
        loadGradesFromCSV();

        BorderPane root = new BorderPane();
        root.setTop(formPane);
        root.setCenter(gradeTable);
        root.setBottom(filterBox);

        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    // Method to save a grade entry
    private void saveGradeEntry() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String course = courseField.getText().trim();
        String grade = gradeComboBox.getValue();

        // Validation
        if (firstName.isEmpty() || lastName.isEmpty() || course.isEmpty() || grade == null) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "All fields are required.");
            return;
        }

        GradeEntry entry = new GradeEntry(firstName, lastName, course, grade);
        gradeEntries.add(entry);
        gradeTable.getItems().add(entry);
        saveToCSV(entry);
        showAlert(Alert.AlertType.INFORMATION, "Success", "Grade entry saved successfully.");
        clearForm();
    }

    // Method to save an entry to CSV
    private void saveToCSV(GradeEntry entry) {
        try (FileWriter fw = new FileWriter("grades.csv", true)) {
            fw.write(entry.toCSV() + "\n");
        } catch (IOException ex) {
            showAlert(Alert.AlertType.ERROR, "File Error", "Error saving to file.");
        }
    }

    // Method to clear the form fields
    private void clearForm() {
        firstNameField.clear();
        lastNameField.clear();
        courseField.clear();
        gradeComboBox.getSelectionModel().clearSelection();
    }

    // Method to filter grades by course name
    private void filterGrades() {
        String courseFilter = filterField.getText().trim();
        if (courseFilter.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Filter Error", "Enter a course name to filter.");
            return;
        }

        gradeTable.getItems().clear();
        for (GradeEntry entry : gradeEntries) {
            if (entry.getCourse().equalsIgnoreCase(courseFilter)) {
                gradeTable.getItems().add(entry);
            }
        }

        if (gradeTable.getItems().isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "No Results", "No results found.");
        }
    }

    // Method to clear the filter and reload all grades
    private void clearFilter() {
        filterField.clear();
        gradeTable.getItems().clear();
        gradeTable.getItems().addAll(gradeEntries);
    }

    // Method to load all grades from the CSV file
    private void loadGradesFromCSV() {
        gradeEntries.clear();
        gradeTable.getItems().clear();
        try (BufferedReader br = new BufferedReader(new FileReader("grades.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                GradeEntry entry = new GradeEntry(data[0], data[1], data[2], data[3]);
                gradeEntries.add(entry);
                gradeTable.getItems().add(entry);
            }
        } catch (IOException ex) {
            showAlert(Alert.AlertType.ERROR, "File Error", "Error loading data from file.");
        }
    }

    // Utility method to show alerts
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }

    // Inner class representing a grade entry
    public static class GradeEntry {
        private String firstName;
        private String lastName;
        private String course;
        private String grade;

        public GradeEntry(String firstName, String lastName, String course, String grade) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.course = course;
            this.grade = grade;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getCourse() {
            return course;
        }

        public String getGrade() {
            return grade;
        }

        public String toCSV() {
            return String.join(",", firstName, lastName, course, grade);
        }
    }
}
