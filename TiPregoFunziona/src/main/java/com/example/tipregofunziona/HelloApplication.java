package com.example.tipregofunziona;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class HelloApplication extends Application {
    @FXML
    private ScrollPane ListaCorsi;
    @FXML
    private ScrollPane ListaAlunni;
    @FXML
    private ScrollPane ListaNomi;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("view.fxml"));
        fxmlLoader.setController(this);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("TiPregoFunziona");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        stage.setOnCloseRequest(event -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Conferma");
            dialog.setHeaderText("Inserisci il codice segreto per chiudere l'app");
            dialog.setContentText("Chiudi l'app:");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent() && result.get().equals("2317")) {
                Platform.exit();
            } else {
                event.consume();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Hai Sbagliato");
                alert.setHeaderText(null);
                alert.setContentText("Codice segreto errato, riprova!");
                alert.showAndWait();
            }
        });
    }

    

    public void initialize() {
        CASciuttiNomiCorsi caSciuttiNomiCorsi = new CASciuttiNomiCorsi();
        List<CASciuttiNomiCorsi.Corso> corsi = caSciuttiNomiCorsi.getCorsi();
        addButtons(corsi);
    }

    public void addButtons(List<CASciuttiNomiCorsi.Corso> corsi) {
        VBox vbox = new VBox();
        ToggleGroup group = new ToggleGroup();

        for (CASciuttiNomiCorsi.Corso corso : corsi) {
            ToggleButton button = new ToggleButton(corso.getTitolo().toUpperCase());
            button.getStyleClass().add("button");
            button.setPrefSize(345, 50);
            button.setFont(new Font(12));
            button.setAlignment(Pos.CENTER_LEFT);
            button.setToggleGroup(group);

            button.setOnAction(event -> {
                ListaNomi.setContent(null);
                addButtonsToListaAlunni(button.getText());
            });

            button.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
                if (isNowSelected) {
                    button.setStyle("-fx-font-weight: bold;");
                } else {
                    button.setStyle("-fx-font-weight: normal;");
                }
            });

            vbox.getChildren().addAll(button);
        }

        ListaCorsi.setContent(vbox);
    }

    public void addButtonsToListaAlunni(String courseId) {
        VBox vbox = new VBox();
        CASciuttiNomiAlunni caSciuttiNomiAlunni = new CASciuttiNomiAlunni();
        List<CASciuttiNomiAlunni.Alunno> alunni = caSciuttiNomiAlunni.getAlunni(courseId);
        ToggleGroup group = new ToggleGroup();

        if (alunni.isEmpty()) {
            Button button = new Button("NESSUN ALUNNO ISCRITTO AL CORSO");
            button.setPrefSize(345, 50);
            button.setFont(new Font(12));
            button.setAlignment(Pos.CENTER_LEFT);
            vbox.getChildren().addAll(button);
        } else {
            for (CASciuttiNomiAlunni.Alunno alunno : alunni) {
                ToggleButton button = new ToggleButton(alunno.getNome().toUpperCase());
                button.getStyleClass().add("button");
                button.setPrefSize(345, 50);
                button.setFont(new Font(12));
                button.setAlignment(Pos.CENTER_LEFT);
                button.setToggleGroup(group); 
                button.setOnAction(event -> addButtonsToListaNomi(button.getText(), courseId));

                button.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
                    if (isNowSelected) {
                        button.setStyle("-fx-font-weight: bold;");
                    } else {
                        button.setStyle("-fx-font-weight: normal;");
                    }
                });

                vbox.getChildren().addAll(button);
            }
        }

        ListaAlunni.setContent(vbox);
    }

    public void addButtonsToListaNomi(String alunnoName, String courseId) {
        VBox vbox = new VBox();
        CASciuttiOreAlunni caSciuttiOreAlunni = new CASciuttiOreAlunni();
        List<CASciuttiOreAlunni.Ora> oreList = caSciuttiOreAlunni.getCorsi(alunnoName, courseId);

        for (CASciuttiOreAlunni.Ora ora : oreList) {
            String ore = ora.getOre();
            Button button = new Button("LO STUDENTE HA SEGUITO IL CORSO PER " + ore + " ORE");
            button.setPrefSize(358, 70);
            button.setFont(new Font(12));
            button.setAlignment(Pos.CENTER);
            vbox.getChildren().addAll(button);
        }

        ListaNomi.setContent(vbox);
    }

    public static void main(String[] args) {
        launch();
    }
}