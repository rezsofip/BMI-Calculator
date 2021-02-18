package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.text.DecimalFormat;

public class Controller {

    @FXML
    private TextField weightTextField;
    @FXML
    private TextField heightTextField;
    @FXML
    private Label result;
    @FXML
    private TableView<Description> descriptionTableView;
    @FXML
    private Label normalWeightResult;

    @FXML
    public void initialize() {

        ObservableList<Description> data = FXCollections.observableArrayList();

        Description firstRow = new Description("< 16", "súlyos soványság");
        Description secondRow = new Description("16 – 16,99", "mérsékelt soványság");
        Description thirdRow = new Description("17 – 18,49", "enyhe soványság");
        Description fourthRow = new Description("18,5 – 24,99", "normális testsúly");
        Description fifthRow = new Description("25 – 29,99", "túlsúlyos");
        Description sixthRow = new Description("30 – 34,99", "I. fokú elhízás");
        Description seventhRow = new Description("35 – 39,99", "II. fokú elhízás");
        Description eighthRow = new Description("≥ 40", "III. fokú (súlyos) elhízás");

        data.add(firstRow);
        data.add(secondRow);
        data.add(thirdRow);
        data.add(fourthRow);
        data.add(fifthRow);
        data.add(sixthRow);
        data.add(seventhRow);
        data.add(eighthRow);


        TableColumn range = new TableColumn("BMI intervallum");
        TableColumn name = new TableColumn("Megnevezés");
        range.setCellValueFactory(new PropertyValueFactory<Description, String>("range"));
        name.setCellValueFactory(new PropertyValueFactory<Description, String>("name"));
        descriptionTableView.getColumns().addAll(range, name);
        descriptionTableView.setItems(data);

    }

    public static double calculateBMI(double weight, double height) {
        double heightInMeter = height / 100;
        return Math.round((weight / (heightInMeter * heightInMeter)) * 100.0) / 100.0;
    }

    @FXML
    public void setResultField() {
        double weight = 0;
        double height = 0;
        try {
            weight = Double.parseDouble(weightTextField.getText());
        } catch (NumberFormatException e) {
            result.setText("A beírt tömeg nem szám formátumú");
            return;
        }

        try {
            height = Double.parseDouble(heightTextField.getText());
        } catch (NumberFormatException e) {
            result.setText("A beírt magasság nem szám formátumú");
            return;
        }

        if (weight <= 0 || weight >= 300
                || weightTextField.getText().isEmpty()) {
            result.setText("A tömegnek 0 és 300 között kell lennie.");
        } else {
            if (height <= 0 || height >= 250
                    || heightTextField.getText().isEmpty()) {
                result.setText("A magasságnak 0 és 250 között kell lennie.");
            } else {
                double BMIresult = calculateBMI(weight, height);
                if (BMIresult < 16) {
                    result.setText(BMIresult + "\nsúlyos soványság");
                    descriptionTableView.getSelectionModel().select(0);
                } else if (BMIresult >= 16 && BMIresult <= 16.99) {
                    result.setText(BMIresult + "\nmérsékelt soványság");
                    descriptionTableView.getSelectionModel().select(1);
                } else if (BMIresult >= 17 && BMIresult <= 18.49) {
                    result.setText(BMIresult + "\nenyhe soványság");
                    descriptionTableView.getSelectionModel().select(2);
                } else if (BMIresult >= 18.5 && BMIresult <= 24.99) {
                    result.setText(BMIresult + "\nnormális testsúly");
                    descriptionTableView.getSelectionModel().select(3);
                } else if (BMIresult >= 25 && BMIresult <= 29.99) {
                    result.setText(BMIresult + "\ntúlsúlyos");
                    descriptionTableView.getSelectionModel().select(4);
                } else if (BMIresult >= 30 && BMIresult <= 34.99) {
                    result.setText(BMIresult + "\nI. fokú elhízás");
                    descriptionTableView.getSelectionModel().select(5);
                } else if (BMIresult >= 35 && BMIresult <= 39.99) {
                    result.setText(BMIresult + "\nII. fokú elhízás");
                    descriptionTableView.getSelectionModel().select(6);
                } else if (BMIresult >= 40) {
                    result.setText(BMIresult + "\nIII. fokú (súlyos) elhízás");
                    descriptionTableView.getSelectionModel().select(7);
                }
            }
        }
    }

    public double[] calculateNormalWeightInterval(double heightInMeters) {
        double lowestWeight = 18.5 * heightInMeters * heightInMeters;
        double highestWeight = 24.99 * heightInMeters * heightInMeters;

        double[] weightInterval = new double[2];

        weightInterval[0] = lowestWeight;
        weightInterval[1] = highestWeight;

        return weightInterval;
    }

    @FXML
    public void showNormalWeightInterval() {
        double heightInMeters;
        try {
            heightInMeters = Double.parseDouble(heightTextField.getText());
        } catch (NumberFormatException e) {
            normalWeightResult.setText("A beírt magasság nem szám formátumú");
            return;
        }


        if (heightInMeters <= 0 || heightInMeters >= 250
                || heightTextField.getText().isEmpty()) {
            normalWeightResult.setText("A magasságnak 0 és 250 között kell lennie.");
            return;
        } else {
            double[] weightInterval = calculateNormalWeightInterval(Double.parseDouble(heightTextField.getText()) / 100);
            String resultText = "A normális testsúlytartomány: " + String.format("%.2f", weightInterval[0]) + " kg - " + String.format("%.2f", weightInterval[1]) + " kg.";


            normalWeightResult.setText(resultText);
            return;
        }
    }

        //        descriptionTextArea.setText("Testtömegindex (kg/m²)	Testsúlyosztályozás\n" +
//                "< 16\tsúlyos soványság\n" +
//                "16 – 16,99\t	mérsékelt soványság\n" +
//                "17 – 18,49\tenyhe soványság\n" +
//                "18,5 – 24,99\tnormális testsúly\n" +
//                "25 – 29,99\ttúlsúlyos\n" +
//                "30 – 34,99\tI. fokú elhízás\n" +
//                "35 – 39,99\tII. fokú elhízás\n" +
//                "≥ 40\tIII. fokú (súlyos) elhízás");
    }
