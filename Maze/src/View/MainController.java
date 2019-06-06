package View;

import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class MainController{

    @FXML
    public MazeDisplayer mazeDisplayer;//the canvas
    public javafx.scene.control.TextField textField_mazeRows;
    public javafx.scene.control.TextField textField_mazeColumns;
    public javafx.scene.control.Button StartGame;
    public javafx.scene.control.Button HelpButton;
    public javafx.scene.control.Button AboutButton;

    SearchableMaze solveMaze;
    Position PlayerSpot;
    int[][] maze = {
            {1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1},
            {1,1,0,0,0,1,1,1,1,1,0,0,0,0,1,0,0,1,1,1,1},
            {1,1,1,1,0,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1},
            {1,1,1,1,0,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,0,1,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,0,1,1,1,1,1,0,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,0,1,1,1,1,1,0,1,1,1,1,1,1,1,1},
            {1,1,1,1,0,0,0,0,0,1,1,1,0,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,0,1,1,0,0,0,1,0,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,1,1,0,1}
    };


    public void handleKeyPressed(KeyEvent ke){
        if (ke.getCode() == KeyCode.RIGHT) {
            if(PlayerSpot.getColumnIndex() < maze[0].length - 1 && maze[PlayerSpot.getRowIndex()][PlayerSpot.getColumnIndex() + 1] != 1){
                maze[PlayerSpot.getRowIndex()][PlayerSpot.getColumnIndex()] = 0;
                PlayerSpot.setColumnIndex(PlayerSpot.getColumnIndex() + 1);
                maze[PlayerSpot.getRowIndex()][PlayerSpot.getColumnIndex()] = 5;
                //mazeDisplayer.setDimentions(maze);
                mazeDisplayer.redraw();
            }
            else{
                //showAlert("You can't walk right anymore","no way");
            }
        }
        if (ke.getCode() == KeyCode.DOWN) {
            if(PlayerSpot.getRowIndex() < maze.length - 1 && maze[PlayerSpot.getRowIndex() + 1][PlayerSpot.getColumnIndex()] != 1){
                maze[PlayerSpot.getRowIndex()][PlayerSpot.getColumnIndex()] = 0;
                PlayerSpot.setRowIndex(PlayerSpot.getRowIndex() + 1);
                maze[PlayerSpot.getRowIndex()][PlayerSpot.getColumnIndex()] = 5;
                mazeDisplayer.setDimentions(maze);
                mazeDisplayer.redraw();
            }
            else{
                //showAlert("You can't walk down anymore","no way");
            }
        }
        if (ke.getCode() == KeyCode.UP) {
            if(PlayerSpot.getRowIndex() > 0 && maze[PlayerSpot.getRowIndex() - 1][PlayerSpot.getColumnIndex()] != 1){
                maze[PlayerSpot.getRowIndex()][PlayerSpot.getColumnIndex()] = 0;
                PlayerSpot.setRowIndex(PlayerSpot.getRowIndex() - 1);
                maze[PlayerSpot.getRowIndex()][PlayerSpot.getColumnIndex()] = 5;
                mazeDisplayer.setDimentions(maze);
                mazeDisplayer.redraw();
            }
            else{
                //showAlert("You can't walk up anymore","no way");
            }
        }
        if (ke.getCode() == KeyCode.LEFT) {
            if(PlayerSpot.getColumnIndex() > 0 && maze[PlayerSpot.getRowIndex()][PlayerSpot.getColumnIndex() - 1] != 1){
                maze[PlayerSpot.getRowIndex()][PlayerSpot.getColumnIndex()] = 0;
                PlayerSpot.setColumnIndex(PlayerSpot.getColumnIndex() - 1);
                maze[PlayerSpot.getRowIndex()][PlayerSpot.getColumnIndex()] = 5;
                mazeDisplayer.setDimentions(maze);
                mazeDisplayer.redraw();
            }
            else{
                //showAlert("You can't walk left anymore","no way");
            }
        }
    }

    /*public void openMazeWindow() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("MainWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),700,400);
        Stage stage = new Stage();
        stage.setTitle("TheMaze");
        stage.setScene(scene);
        stage.show();
        Stage oldStage = (Stage) StartGame.getScene().getWindow();
        oldStage.close();
    }*/
    public void openMazeWindow() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("MainWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),950,400);
        Stage stage = new Stage();
        stage.setTitle("TheMaze");
        stage.setScene(scene);
        stage.show();
        Stage oldStage = (Stage) StartGame.getScene().getWindow();
        oldStage.close();
    }

    private int[] getDimensionsFromDialog(){
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Set Maze Dimension");

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("Set", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();

        TextField from = new TextField("50");
        TextField to = new TextField("50");

        gridPane.add(new Label("Number of rows:"), 0, 0);
        gridPane.add(from, 1, 0);
        gridPane.add(new Label("Number of columns:"), 0, 1);
        gridPane.add(to, 1, 1);

        dialog.getDialogPane().setContent(gridPane);

        // Request focus on the username field by default.
        Platform.runLater(() -> from.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(from.getText(), to.getText());
            }
            return null;
        });
        final int[] RowsAndCols = new int[2];

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(pair -> {
            if(pair != null && !pair.getKey().equals("") && !pair.getValue().equals("")) {
                RowsAndCols[0] = Integer.parseInt(pair.getKey());
                RowsAndCols[1] = Integer.parseInt(pair.getValue());
            }
            else{
                RowsAndCols[0] = -1;
                RowsAndCols[1] = -1;
            }
        });
        return new int[]{RowsAndCols[0],RowsAndCols[1]};
    }

    public void generateMaze(){
        int[] RowsAndCols = new int[2];
        RowsAndCols = getDimensionsFromDialog();

        if(RowsAndCols[0] > 0 && RowsAndCols[1] > 0) {
            IMazeGenerator mazeGenerator = new MyMazeGenerator();
            Maze GeneratedMaze = mazeGenerator.generate(RowsAndCols[0], RowsAndCols[1]);
            maze = new int[RowsAndCols[0]][RowsAndCols[1]];
            for (int i = 0; i < GeneratedMaze.getNumOfRows(); i++) {
                for (int j = 0; j < GeneratedMaze.getNumOfColumns(); j++) {
                    maze[i][j] = GeneratedMaze.getMazeInfo(i, j);
                }
            }
            solveMaze = new SearchableMaze(GeneratedMaze);
            PlayerSpot = new Position(GeneratedMaze.getStartPosition().getRowIndex(), GeneratedMaze.getStartPosition().getColumnIndex());
            maze[PlayerSpot.getRowIndex()][PlayerSpot.getColumnIndex()] = 5;

            mazeDisplayer.setDimentions(maze);
            mazeDisplayer.redraw();
        }
    }

    private void showAlert(String alertMessage,String title) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(alertMessage);
        alert.setTitle(title);
        alert.show();
    }

    public void openAbout(){
        showAlert("this is about window\n" +
                "here you can see everything about us","About");
    }

    public void solveMaze(ActionEvent actionEvent) {
        BestFirstSearch bfs = new BestFirstSearch();
        Solution solution = bfs.solve(solveMaze);
        ArrayList<AState> solutionPath = solution.getSolutionPath();
        int i;
        for(i = 0; i < solutionPath.size(); ++i) {
            maze[((MazeState)solutionPath.get(i)).getCurrentPosition().getRowIndex()][((MazeState)solutionPath.get(i)).getCurrentPosition().getColumnIndex()] = 2;
        }
        mazeDisplayer.setDimentions(maze);
        mazeDisplayer.redraw();
    }
}
