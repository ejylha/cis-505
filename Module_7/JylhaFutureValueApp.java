import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class JylhaFutureValueApp extends Application {

    // Step 5: Create private variables
    private TextField txtInterestRate, txtYears, txtFutureValue;
    private TextArea txtOutput;
    private Label lblInterestRateFormat, lblYears, lblFutureValue, lblOutput, lblCalculation;
    private ComboBox<Integer> cmbYears;
    private Button btnCalculate, btnClear;

    @Override
    public void start(Stage primaryStage) {
        // Create a new GridPane
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new javafx.geometry.Insets(11.5, 12.5, 13.5, 14.5));
        gridPane.setHgap(5.5);
        gridPane.setVgap(5.5);

        // Step 5a: Initialize labels
        lblInterestRateFormat = new Label("Interest Rate:");
        lblInterestRateFormat.setTextFill(Color.RED);
        GridPane.setHalignment(lblInterestRateFormat, HPos.RIGHT);

        lblYears = new Label("Number of Years:");
        lblFutureValue = new Label("Future Value:");

        // Step 5b: Initialize buttons
        btnCalculate = new Button("Calculate");
        btnClear = new Button("Clear");

        // Step 5: Add controls to the GridPane
        txtInterestRate = new TextField();
        txtYears = new TextField();
        txtFutureValue = new TextField();
        txtOutput = new TextArea();

        cmbYears = new ComboBox<>();
        for (int i = 1; i <= 30; i++) {
            cmbYears.getItems().add(i);
        }

        // Add elements to the grid
        gridPane.add(lblInterestRateFormat, 0, 0);
        gridPane.add(txtInterestRate, 1, 0);
        gridPane.add(lblYears, 0, 1);
        gridPane.add(cmbYears, 1, 1);
        gridPane.add(lblFutureValue, 0, 2);
        gridPane.add(txtFutureValue, 1, 2);

        // Add Output Area
        gridPane.add(txtOutput, 0, 3, 2, 1);

        // Create an HBox for buttons
        HBox hBox = new HBox(10);
        hBox.setPadding(new javafx.geometry.Insets(15, 0, 15, 30));
        hBox.getChildren().addAll(btnCalculate, btnClear);

        gridPane.add(hBox, 0, 4, 2, 1);

        // Set the primary stage
        primaryStage.setTitle("YourLastName Future Value App");
        primaryStage.setScene(new Scene(gridPane, 400, 300));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
