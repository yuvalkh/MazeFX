package View;

import Server.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Server mazeGeneratingServer;
    public static Server solveSearchProblemServer;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Configurations.loadFilePath(getClass().getResource("/config.properties").getPath());

        mazeGeneratingServer = new Server(5400, 1000, new ServerStrategyGenerateMaze());
        solveSearchProblemServer = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
        //Starting  servers
        solveSearchProblemServer.start();
        mazeGeneratingServer.start();

        primaryStage.setTitle("MazeGame");
        Parent root = FXMLLoader.load(getClass().getResource("StartPage.fxml"));
        Scene scene = new Scene(root,950,680);
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest( event ->
        {
            Main.solveSearchProblemServer.stop();
            Main.mazeGeneratingServer.stop();
        });
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
