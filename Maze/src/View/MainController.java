package View;

import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainController {

    @FXML
    public MazeDisplayer mazeDisplayer;//the canvas
    public javafx.scene.control.TextField textField_mazeRows;
    public javafx.scene.control.TextField textField_mazeColumns;
    public javafx.scene.control.Button StartGame;
    public javafx.scene.control.Button HelpButton;
    public javafx.scene.control.Button AboutButton;

    static int gameID = 0;
    SearchableMaze solveMaze;
    Position PlayerSpot;
    //int[][] maze;
    Maze maze;
    Character character;
    EndPoint endPoint;
    Solve solve;
    Wall wall;

    public void handleKeyPressed(KeyEvent ke) {
        if (ke.getCode() == KeyCode.RIGHT) {
            if (PlayerSpot.getColumnIndex() < maze.getNumOfColumns() - 1 && maze.getMazeInfo(PlayerSpot.getRowIndex(),PlayerSpot.getColumnIndex() + 1) != 1) {
                maze.setMazeInfo(PlayerSpot.getRowIndex(),PlayerSpot.getColumnIndex(),0);
                PlayerSpot.setColumnIndex(PlayerSpot.getColumnIndex() + 1);
                maze.setMazeInfo(PlayerSpot.getRowIndex(),PlayerSpot.getColumnIndex(),5);
                mazeDisplayer.setDimentions(maze);
                mazeDisplayer.updatePlayerSpot(PlayerSpot.getRowIndex(),PlayerSpot.getColumnIndex());
                character = new Character(new Image("PacmanRight.png"));
                mazeDisplayer.redraw(character, wall, endPoint, solve);
            } else {
                //showAlert("You can't walk right anymore","no way");
            }
        }
        if (ke.getCode() == KeyCode.DOWN) {
            if (PlayerSpot.getRowIndex() < maze.getNumOfRows() - 1 && maze.getMazeInfo(PlayerSpot.getRowIndex() + 1,PlayerSpot.getColumnIndex()) != 1) {
                maze.setMazeInfo(PlayerSpot.getRowIndex(),PlayerSpot.getColumnIndex(),0);
                PlayerSpot.setRowIndex(PlayerSpot.getRowIndex() + 1);
                maze.setMazeInfo(PlayerSpot.getRowIndex(),PlayerSpot.getColumnIndex(),5);
                mazeDisplayer.setDimentions(maze);
                mazeDisplayer.updatePlayerSpot(PlayerSpot.getRowIndex(),PlayerSpot.getColumnIndex());
                character = new Character(new Image("PacmanDown.png"));
                mazeDisplayer.redraw(character, wall, endPoint, solve);
            } else {

                //showAlert("You can't walk down anymore","no way");
            }
        }
        if (ke.getCode() == KeyCode.UP) {
            if (PlayerSpot.getRowIndex() > 0 && maze.getMazeInfo(PlayerSpot.getRowIndex() - 1,PlayerSpot.getColumnIndex()) != 1) {
                maze.setMazeInfo(PlayerSpot.getRowIndex(),PlayerSpot.getColumnIndex(),0);
                PlayerSpot.setRowIndex(PlayerSpot.getRowIndex() - 1);
                maze.setMazeInfo(PlayerSpot.getRowIndex(),PlayerSpot.getColumnIndex(),5);
                mazeDisplayer.setDimentions(maze);
                mazeDisplayer.updatePlayerSpot(PlayerSpot.getRowIndex(),PlayerSpot.getColumnIndex());
                character = new Character(new Image("PacmanUp.png"));
                mazeDisplayer.redraw(character, wall, endPoint, solve);
            } else {
                //showAlert("You can't walk up anymore","no way");

            }
        }
        if (ke.getCode() == KeyCode.LEFT) {
            if (PlayerSpot.getColumnIndex() > 0 && maze.getMazeInfo(PlayerSpot.getRowIndex(),PlayerSpot.getColumnIndex() - 1) != 1) {
                maze.setMazeInfo(PlayerSpot.getRowIndex(),PlayerSpot.getColumnIndex(),0);
                PlayerSpot.setColumnIndex(PlayerSpot.getColumnIndex() - 1);
                maze.setMazeInfo(PlayerSpot.getRowIndex(),PlayerSpot.getColumnIndex(),5);
                mazeDisplayer.setDimentions(maze);
                mazeDisplayer.updatePlayerSpot(PlayerSpot.getRowIndex(),PlayerSpot.getColumnIndex());
                character = new Character(new Image("PacmanLeft.png"));
                mazeDisplayer.redraw(character, wall, endPoint, solve);
            } else {

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
        Scene scene = new Scene(fxmlLoader.load(), 950, 400);
        Stage stage = new Stage();
        stage.setTitle("TheMaze");
        stage.setScene(scene);
        stage.show();
        Stage oldStage = (Stage) StartGame.getScene().getWindow();
        oldStage.close();
    }

    /**
     * need to save:
     * Date saved
     * Dimensions
     * player's spot
     * Maze
     * Zoom
     *
     */
    public void saveGame() throws IOException, URISyntaxException {
        FileOutputStream f = new FileOutputStream(new File(getClass().getResource("/SavedGames").getPath() + "/Game"+ gameID +".txt"));
        ObjectOutputStream o = new ObjectOutputStream(f);
        String timeStamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        //write the maze,player's spot, Current zoom
        o.writeObject(timeStamp);
        o.write(maze.getNumOfRows());
        o.write(maze.getNumOfColumns());
        o.writeObject(maze.toByteArray());
        o.writeObject(PlayerSpot);
        o.write(mazeDisplayer.getZoom());
        o.write(gameID);
        o.close();
        f.close();
        gameID++;
    }

    /*private int getHighestGameID(){
        final File folder = new File(getClass().getResource("/SavedGames").getPath());
        int maxID = 0;
        for (final File fileEntry : folder.listFiles()) {

        }
        return maxID;
    }*/

    /**
     * need to save:
     * player's spot
     * Maze
     * Zoom
     *
     */
    public void loadGame(int id) throws IOException, ClassNotFoundException {
        FileInputStream fi = new FileInputStream(new File(getClass().getResource("/SavedGames").getPath() + "/Game"+ id +".txt"));
        ObjectInputStream oi = new ObjectInputStream(fi);
        //read the maze and the solutions
        String Date = (String)oi.readObject();
        int NumOfRows = oi.read();
        int NumOfColumns = oi.read();
        byte[] bytedLoadedMaze = (byte[]) oi.readObject();
        Position loadedPosition = (Position) oi.readObject();
        int zoom = oi.read();
        oi.close();
        fi.close();
        Maze LoadMaze = new Maze(bytedLoadedMaze);
        mazeDisplayer.updatePlayerSpot(loadedPosition.getRowIndex(),loadedPosition.getColumnIndex());
        mazeDisplayer.setDimentions(LoadMaze);
        mazeDisplayer.setZoom(zoom);
        //now we can set all the properties
    }

    public void openChoosingLoadedGame() throws IOException, ClassNotFoundException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("LoadGame.fxml"));
        //Scene scene = new Scene(fxmlLoader.load(),700,400);
        Scene scene = new Scene(new Group());
        Stage stage = new Stage();
        TableView table = new TableView();
        Label label = new Label("Choose a game from the table and then click on Load");
        table.setPrefHeight(500);
        table.setPrefWidth(300);
        //table.setEditable(true);

        TableColumn GameIdCol = new TableColumn("GameID");
        GameIdCol.setCellValueFactory(new PropertyValueFactory<>("GameID"));
        TableColumn DimensionsCol = new TableColumn("Dimensions");
        DimensionsCol.setCellValueFactory(new PropertyValueFactory<>("Dimensions"));
        TableColumn PlayerPositionCol = new TableColumn("PlayerPosition");
        PlayerPositionCol.setCellValueFactory(new PropertyValueFactory<>("PlayerPosition"));
        TableColumn DateCol = new TableColumn("Date");
        DateCol.setCellValueFactory(new PropertyValueFactory<>("Date"));

        table.getColumns().addAll(GameIdCol, DimensionsCol, PlayerPositionCol,DateCol);

        Button LoadButton = new Button("Load");
        Button ExitButton = new Button("Exit");

        LoadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                LoadedGame SelectedGame = (LoadedGame)table.getSelectionModel().getSelectedItem();
                if(SelectedGame != null) {//load the game
                    try {
                        loadGame(Integer.parseInt(SelectedGame.getGameID()));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    }
                }
                else{
                    showAlert("Please choose a game to load","oh hell no");
                }
            }
        });
        ExitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                stage.close();
            }
        });

        final File folder = new File(getClass().getResource("/SavedGames").getPath());
        for (final File fileEntry : folder.listFiles()) {
            FileInputStream fi = new FileInputStream(fileEntry);
            ObjectInputStream oi = new ObjectInputStream(fi);
            //read the properties
            String Date = (String)oi.readObject();
            int NumOfRows = oi.read();
            int NumOfColumns = oi.read();
            byte[] bytedLoadedMaze = (byte[]) oi.readObject();
            Position loadedPosition = (Position) oi.readObject();
            int zoom = oi.read();
            int gameid = oi.read();
            LoadedGame lg = new LoadedGame(Integer.toString(gameid),Date,  NumOfRows+ "x" +NumOfColumns,loadedPosition.getRowIndex()+","+loadedPosition.getColumnIndex());
            table.getItems().add(lg);
            oi.close();
            fi.close();
        }


        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table,LoadButton,ExitButton);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setTitle("Choose loaded game");
        stage.setScene(scene);
        stage.show();
    }

    private int[] getDimensionsFromDialog() {
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
            if (pair != null && !pair.getKey().equals("") && !pair.getValue().equals("")) {
                RowsAndCols[0] = Integer.parseInt(pair.getKey());
                RowsAndCols[1] = Integer.parseInt(pair.getValue());
            } else {
                RowsAndCols[0] = -1;
                RowsAndCols[1] = -1;
            }
        });
        return new int[]{RowsAndCols[0], RowsAndCols[1]};
    }

    public void generateMaze() {
        character = new Character(new Image("PacmanRight.png"));
        endPoint = new EndPoint(new Image("EndPoint.png"));
        solve = new Solve(new Image("Food.jpg"));
        wall = new Wall(new Image("BlueWall.jpg"));
        int[] RowsAndCols = new int[2];
        RowsAndCols = getDimensionsFromDialog();

        if (RowsAndCols[0] > 0 && RowsAndCols[1] > 0) {
            IMazeGenerator mazeGenerator = new MyMazeGenerator();
            Maze GeneratedMaze = mazeGenerator.generate(RowsAndCols[0], RowsAndCols[1]);
            maze = GeneratedMaze;
            solveMaze = new SearchableMaze(GeneratedMaze);
            PlayerSpot = new Position(GeneratedMaze.getStartPosition().getRowIndex(), GeneratedMaze.getStartPosition().getColumnIndex());
            maze.setMazeInfo(PlayerSpot.getRowIndex(),PlayerSpot.getColumnIndex(), 5);
            mazeDisplayer.setDimentions(maze);
            mazeDisplayer.redraw(character, wall, endPoint, solve);
        }
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

    public void solveMaze(ActionEvent actionEvent) {
        BestFirstSearch bfs = new BestFirstSearch();
        solveMaze.setStartPosition(PlayerSpot);
        Solution solution = bfs.solve(solveMaze);
        ArrayList<AState> solutionPath = solution.getSolutionPath();
        int i;
        for (int j = 0; j < maze.getNumOfRows(); j++) {
            for (int k = 0; k < maze.getNumOfColumns(); k++) {
                if(maze.getMazeInfo(j,k) == 2){
                    maze.setMazeInfo(j,k,0);
                }
            }
        }
        for (i = 0; i < solutionPath.size(); ++i) {
            if(maze.getMazeInfo(((MazeState) solutionPath.get(i)).getCurrentPosition().getRowIndex(),((MazeState) solutionPath.get(i)).getCurrentPosition().getColumnIndex()) == 0){
                maze.setMazeInfo(((MazeState) solutionPath.get(i)).getCurrentPosition().getRowIndex(),((MazeState) solutionPath.get(i)).getCurrentPosition().getColumnIndex(), 2);
            }
        }
        mazeDisplayer.setDimentions(maze);
        mazeDisplayer.redraw(character, wall, endPoint, solve);
    }

}
