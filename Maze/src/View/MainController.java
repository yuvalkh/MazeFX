package View;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.*;

public class MainController {

    @FXML
    public javafx.scene.control.Button StartGame;
    public javafx.scene.control.Button HelpButton;
    public javafx.scene.control.Button AboutButton;

    public void openTestWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("MainTest.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Tests");
        stage.setScene(scene);
        stage.show();
        Stage oldStage = (Stage) StartGame.getScene().getWindow();
        oldStage.close();
    }

    public void openMazeWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("GamePage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 900);
        Stage stage = new Stage();
        stage = new Stage();
        stage.setTitle("TheMaze");
        stage.setScene(scene);
        stage.show();
        Stage oldStage = (Stage) StartGame.getScene().getWindow();
        oldStage.close();
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
