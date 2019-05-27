package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle("MazeGame");
        Parent root = FXMLLoader.load(getClass().getResource("MyView.fxml"));

        StackPane pane = new StackPane();
        Scene scene = new Scene(pane,950,680);


        pane.getChildren().add(root);
        pane.setStyle("-fx-border-color: black;");

        //root.prefHeightProperty().bind(pane.heightProperty());
        //root.prefWidthProperty().bind(scene.widthProperty());

        scene.getStylesheets().add(getClass().getResource("MainStyle.css").toExternalForm());


        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
