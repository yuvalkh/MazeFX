package View;

import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class MainController{

    @FXML
    public MazeDisplayer mazeDisplayer;
    public javafx.scene.control.TextField textField_mazeRows;
    public javafx.scene.control.TextField textField_mazeColumns;
    public javafx.scene.control.Button closeButton;
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
            if(PlayerSpot.getColumnIndex() < maze[0].length - 1){
                maze[PlayerSpot.getRowIndex()][PlayerSpot.getColumnIndex()] = 0;
                PlayerSpot.setColumnIndex(PlayerSpot.getColumnIndex() + 1);
                maze[PlayerSpot.getRowIndex()][PlayerSpot.getColumnIndex()] = 5;
                //mazeDisplayer.setDimentions(maze);
                mazeDisplayer.redraw();
            }
            else{
                showAlert("You can't walk right anymore","no way");
            }
        }
        if (ke.getCode() == KeyCode.DOWN) {
            if(PlayerSpot.getRowIndex() < maze.length - 1){
                maze[PlayerSpot.getRowIndex()][PlayerSpot.getColumnIndex()] = 0;
                PlayerSpot.setRowIndex(PlayerSpot.getRowIndex() + 1);
                maze[PlayerSpot.getRowIndex()][PlayerSpot.getColumnIndex()] = 5;
                mazeDisplayer.setDimentions(maze);
                mazeDisplayer.redraw();
            }
            else{
                showAlert("You can't walk down anymore","no way");
            }
        }
        if (ke.getCode() == KeyCode.UP) {
            if(PlayerSpot.getRowIndex() > 0){
                maze[PlayerSpot.getRowIndex()][PlayerSpot.getColumnIndex()] = 0;
                PlayerSpot.setRowIndex(PlayerSpot.getRowIndex() - 1);
                maze[PlayerSpot.getRowIndex()][PlayerSpot.getColumnIndex()] = 5;
                mazeDisplayer.setDimentions(maze);
                mazeDisplayer.redraw();

            }
            else{
                showAlert("You can't walk up anymore","no way");
            }
        }
        if (ke.getCode() == KeyCode.LEFT) {
            if(PlayerSpot.getColumnIndex() > 0){
                maze[PlayerSpot.getRowIndex()][PlayerSpot.getColumnIndex()] = 0;
                PlayerSpot.setColumnIndex(PlayerSpot.getColumnIndex() - 1);
                maze[PlayerSpot.getRowIndex()][PlayerSpot.getColumnIndex()] = 5;
                mazeDisplayer.setDimentions(maze);
                mazeDisplayer.redraw();
            }
            else{
                showAlert("You can't walk left anymore","no way");
            }
        }
    }

    public void openMazeWindow() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("MainWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),700,400);
        Stage stage = new Stage();
        stage.setTitle("TheMaze");
        stage.setScene(scene);
        stage.show();
        Stage oldStage = (Stage) closeButton.getScene().getWindow();
        oldStage.close();
    }

    public void generateMaze(){
        IMazeGenerator mazeGenerator = new MyMazeGenerator();
        Maze GeneratedMaze = mazeGenerator.generate(Integer.valueOf(textField_mazeRows.getText()),Integer.valueOf(textField_mazeColumns.getText()));
        maze = new int[Integer.valueOf(textField_mazeRows.getText())][Integer.valueOf(textField_mazeColumns.getText())];
        for (int i = 0; i < GeneratedMaze.getNumOfRows(); i++) {
            for (int j = 0; j < GeneratedMaze.getNumOfColumns(); j++) {
                maze[i][j] = GeneratedMaze.getMazeInfo(i,j);
            }
        }
        solveMaze = new SearchableMaze(GeneratedMaze);
        PlayerSpot = new Position(GeneratedMaze.getStartPosition().getRowIndex(),GeneratedMaze.getStartPosition().getColumnIndex());
        maze[PlayerSpot.getRowIndex()][PlayerSpot.getColumnIndex()] = 5;
        mazeDisplayer.setDimentions(maze);
        mazeDisplayer.redraw();
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
