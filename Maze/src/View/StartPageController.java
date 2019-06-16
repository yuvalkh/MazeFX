package View;

import Server.Server;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class StartPageController implements Initializable {

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
        stage.setTitle("Help Window");
        scene.getStylesheets().addAll(this.getClass().getResource("HelpStyle.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void openAboutWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("AboutPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("About Window");
        scene.getStylesheets().addAll(this.getClass().getResource("AboutStyle.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void openMazeWindow() throws IOException {
        Music.stopMusic();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("GamePage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 900);
        Stage stage = new Stage();
        stage.setTitle("Maze Game");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest( event ->
        {
            Main.solveSearchProblemServer.stop();
            Main.mazeGeneratingServer.stop();
        });
        Stage oldStage = (Stage) StartGame.getScene().getWindow();
        oldStage.close();
    }
    public void openOptionsWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("OptionsPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 750, 350);
        Stage stage = new Stage();
        stage.setTitle("Options Window");
        stage.setScene(scene);
        scene.getStylesheets().addAll(this.getClass().getResource("OptionsStyle.css").toExternalForm());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    private void ExitGame(){
        Stage stage = (Stage) ExitButton.getScene().getWindow();
        stage.close();
        Main.mazeGeneratingServer.stop();
        Main.solveSearchProblemServer.stop();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Music music = new Music(getClass().getResource("/") + "/Music/MenuMusic.mp3");
        try {
            music.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*Music music = new Music("GameMusic");
        music.PlayMusic();*/
    }
}
