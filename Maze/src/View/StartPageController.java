package View;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;

public class StartPageController {

    @FXML
    public javafx.scene.control.Button StartGame;
    public javafx.scene.control.Button HelpButton;
    public javafx.scene.control.Button AboutButton;
    public javafx.scene.control.Button OptionsButton;
    public javafx.scene.control.Button ExitButton;

    public void openHelpWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("HelpPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Tests");
        scene.getStylesheets().addAll(this.getClass().getResource("HelpStyle.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void openMazeWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("GamePage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 900);
        Stage stage = new Stage();
        stage = new Stage();
        stage.setTitle("TheMaze");
        stage.setScene(scene);
        stage.show();
        Stage oldStage = (Stage) StartGame.getScene().getWindow();
        oldStage.close();
    }
    public void openOptionsWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("OptionsPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 300);
        Stage stage = new Stage();
        stage = new Stage();
        stage.setTitle("Options");
        stage.setScene(scene);
        scene.getStylesheets().addAll(this.getClass().getResource("OptionsStyle.css").toExternalForm());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    private void ExitGame(){
        Stage stage = (Stage) ExitButton.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String alertMessage, String title) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(alertMessage);
        alert.setTitle(title);
        alert.show();
    }

    public void openAbout() {
        showAlert("this is about window\n" +
                "here you can see everything about us", "About");
    }
}
