package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("MazeGame");
        Parent root = FXMLLoader.load(getClass().getResource("MyView.fxml"));
        Scene scene = new Scene(root,850,600);
        scene.getStylesheets().add(getClass().getResource("MainStyle.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
