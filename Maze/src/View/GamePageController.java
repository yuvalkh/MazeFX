package View;

import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.*;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.*;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.*;

public class GamePageController {
    @FXML
    public MazeDisplayer mazeDisplayer;//the canvas
    public Button BackButton;

    int SelectedCharacter;
    SearchableMaze solveMaze;
    Solution solutionMaze;
    Position PlayerSpot;
    //int[][] maze;
    Maze maze;
    Character character;
    EndPoint endPoint = new EndPoint(new Image("/Images/EndPoint.png"));
    Solve solve = new Solve(new Image("/Images/Pokeball.png"));
    Wall wall = new Wall(new Image("/Images/tree.png"));
    Floor floor = new Floor(new Image("/Images/Grass.png"));
    Outside outside = new Outside(new Image("/Images/Water.png"));
    boolean isControlPressed;
    boolean showSolution = false;


    public void backToMenu() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("StartPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 950, 680);
        Stage stage = new Stage();
        stage.setTitle("MainMenu");
        stage.setScene(scene);
        stage.show();
        Stage mazeStage = (Stage) BackButton.getScene().getWindow();
        mazeStage.close();
    }

    public void handleKeyPressed(KeyEvent ke) throws IOException {
        if (ke.getCode() == KeyCode.CONTROL) {
            isControlPressed = true;
        }
        if (ke.getCode() == KeyCode.RIGHT) {
            if (PlayerSpot.getColumnIndex() < maze.getNumOfColumns() - 1 && maze.getMazeInfo(PlayerSpot.getRowIndex(), PlayerSpot.getColumnIndex() + 1) != 1) {
                maze.setMazeInfo(PlayerSpot.getRowIndex(), PlayerSpot.getColumnIndex(), 0);
                PlayerSpot.setColumnIndex(PlayerSpot.getColumnIndex() + 1);
                maze.setMazeInfo(PlayerSpot.getRowIndex(), PlayerSpot.getColumnIndex(), 5);
                mazeDisplayer.setDimentions(maze);
                mazeDisplayer.updatePlayerSpot(PlayerSpot.getRowIndex(), PlayerSpot.getColumnIndex());
                mazeDisplayer.setCharacterImage(character.getRightImage());
                mazeDisplayer.redraw();
            } else {
                //showAlert("You can't walk right anymore","no way");
            }
        }
        if (ke.getCode() == KeyCode.DOWN) {
            if (PlayerSpot.getRowIndex() < maze.getNumOfRows() - 1 && maze.getMazeInfo(PlayerSpot.getRowIndex() + 1, PlayerSpot.getColumnIndex()) != 1) {
                maze.setMazeInfo(PlayerSpot.getRowIndex(), PlayerSpot.getColumnIndex(), 0);
                PlayerSpot.setRowIndex(PlayerSpot.getRowIndex() + 1);
                maze.setMazeInfo(PlayerSpot.getRowIndex(), PlayerSpot.getColumnIndex(), 5);
                mazeDisplayer.setDimentions(maze);
                mazeDisplayer.updatePlayerSpot(PlayerSpot.getRowIndex(), PlayerSpot.getColumnIndex());
                mazeDisplayer.setCharacterImage(character.getDownImage());
                mazeDisplayer.redraw();
            } else {

                //showAlert("You can't walk down anymore","no way");
            }
        }
        if (ke.getCode() == KeyCode.UP) {
            if (PlayerSpot.getRowIndex() > 0 && maze.getMazeInfo(PlayerSpot.getRowIndex() - 1, PlayerSpot.getColumnIndex()) != 1) {
                maze.setMazeInfo(PlayerSpot.getRowIndex(), PlayerSpot.getColumnIndex(), 0);
                PlayerSpot.setRowIndex(PlayerSpot.getRowIndex() - 1);
                maze.setMazeInfo(PlayerSpot.getRowIndex(), PlayerSpot.getColumnIndex(), 5);
                mazeDisplayer.setDimentions(maze);
                mazeDisplayer.updatePlayerSpot(PlayerSpot.getRowIndex(), PlayerSpot.getColumnIndex());
                mazeDisplayer.setCharacterImage(character.getUpImage());
                mazeDisplayer.redraw();
            } else {
                //showAlert("You can't walk up anymore","no way");

            }
        }
        if (ke.getCode() == KeyCode.LEFT) {
            if (PlayerSpot.getColumnIndex() > 0 && maze.getMazeInfo(PlayerSpot.getRowIndex(), PlayerSpot.getColumnIndex() - 1) != 1) {
                maze.setMazeInfo(PlayerSpot.getRowIndex(), PlayerSpot.getColumnIndex(), 0);
                PlayerSpot.setColumnIndex(PlayerSpot.getColumnIndex() - 1);
                maze.setMazeInfo(PlayerSpot.getRowIndex(), PlayerSpot.getColumnIndex(), 5);
                mazeDisplayer.setDimentions(maze);
                mazeDisplayer.updatePlayerSpot(PlayerSpot.getRowIndex(), PlayerSpot.getColumnIndex());
                mazeDisplayer.setCharacterImage(character.getLeftImage());
                mazeDisplayer.redraw();
            } else {

                //showAlert("You can't walk left anymore","no way");
            }
        }
        if (PlayerSpot.getColumnIndex() == maze.getGoalPosition().getColumnIndex() && PlayerSpot.getRowIndex() == maze.getGoalPosition().getRowIndex()) {
            endGame();
        }
    }

    public void handleKeyReleased(KeyEvent ke) {
        if (ke.getCode() == KeyCode.CONTROL) {
            isControlPressed = false;
        }
    }

    public void loadGame(String GameName) throws IOException, ClassNotFoundException {
        FileInputStream fi = new FileInputStream(new File(getClass().getResource("/SavedGames").getPath() + "/" + GameName));
        ObjectInputStream oi = new ObjectInputStream(fi);
        //read the maze and the solutions
        String Date = (String) oi.readObject();
        int NumOfRows = oi.read();
        int NumOfColumns = oi.read();
        byte[] bytedLoadedMaze = (byte[]) oi.readObject();
        Position loadedPosition = (Position) oi.readObject();
        int zoom = oi.read();
        showSolution = false;
        String characterName = (String) oi.readObject();
        oi.close();
        fi.close();
        List<Image> Up = new LinkedList<>();
        List<Image> Right = new LinkedList<>();
        List<Image> Down = new LinkedList<>();
        List<Image> Left = new LinkedList<>();
        Up.add(new Image("/Images/" + characterName + "/Up0.png"));
        Up.add(new Image("/Images/" + characterName + "/Up1.png"));
        Right.add(new Image("/Images/" + characterName + "/Right0.png"));
        Right.add(new Image("/Images/" + characterName + "/Right1.png"));
        Down.add(new Image("/Images/" + characterName + "/Down0.png"));
        Down.add(new Image("/Images/" + characterName + "/Down1.png"));
        Left.add(new Image("/Images/" + characterName + "/Left0.png"));
        Left.add(new Image("/Images/" + characterName + "/Left1.png"));
        character = new Character(characterName, Up, Down, Right, Left);
        maze = new Maze(bytedLoadedMaze);
        maze.setMazeInfo(loadedPosition.getRowIndex(), loadedPosition.getColumnIndex(), 5);
        PlayerSpot = loadedPosition;
        mazeDisplayer.setDimentions(maze);
        mazeDisplayer.updatePlayerSpot(loadedPosition.getRowIndex(), loadedPosition.getColumnIndex());
        mazeDisplayer.setCharacterImage(character.getRightImage());
        mazeDisplayer.setZoom(zoom);
        solveMaze = new SearchableMaze(maze);
        mazeDisplayer.setImageProperties(character, wall, endPoint, solve, floor, outside);
        mazeDisplayer.redraw();
        //now we can set all the properties
    }

    public void zoom(ScrollEvent se) {
        if (isControlPressed) {
            if (se.getDeltaY() > 0) {
                mazeDisplayer.ZoomIn();
            } else {
                mazeDisplayer.ZoomOut();
            }
            mazeDisplayer.redraw();
        }
    }

    public void generateMaze() {
        int[] RowsAndCols;
        RowsAndCols = getDimensionsFromDialog();

        if (RowsAndCols[0] > 0 && RowsAndCols[1] > 0 && SelectedCharacter > 0) {
            IMazeGenerator mazeGenerator = new MyMazeGenerator();
            Maze GeneratedMaze = mazeGenerator.generate(RowsAndCols[0], RowsAndCols[1]);
            maze = GeneratedMaze;
            solveMaze = new SearchableMaze(GeneratedMaze);
            PlayerSpot = new Position(GeneratedMaze.getStartPosition().getRowIndex(), GeneratedMaze.getStartPosition().getColumnIndex());
            maze.setMazeInfo(PlayerSpot.getRowIndex(), PlayerSpot.getColumnIndex(), 5);
            mazeDisplayer.setDimentions(maze);
            List<Image> Up = new LinkedList<>();
            List<Image> Right = new LinkedList<>();
            List<Image> Down = new LinkedList<>();
            List<Image> Left = new LinkedList<>();
            if (SelectedCharacter == 1) {
                Up.add(new Image("/Images/Pikachu/Up0.png"));
                Up.add(new Image("/Images/Pikachu/Up1.png"));
                Right.add(new Image("/Images/Pikachu/Right0.png"));
                Right.add(new Image("/Images/Pikachu/Right1.png"));
                Down.add(new Image("/Images/Pikachu/Down0.png"));
                Down.add(new Image("/Images/Pikachu/Down1.png"));
                Left.add(new Image("/Images/Pikachu/Left0.png"));
                Left.add(new Image("/Images/Pikachu/Left1.png"));
                character = new Character("Pikachu", Up, Down, Right, Left);
            } else if (SelectedCharacter == 2) {
                Up.add(new Image("/Images/Squirtel/Up0.png"));
                Up.add(new Image("/Images/Squirtel/Up1.png"));
                Right.add(new Image("/Images/Squirtel/Right0.png"));
                Right.add(new Image("/Images/Squirtel/Right1.png"));
                Down.add(new Image("/Images/Squirtel/Down0.png"));
                Down.add(new Image("/Images/Squirtel/Down1.png"));
                Left.add(new Image("/Images/Squirtel/Left0.png"));
                Left.add(new Image("/Images/Squirtel/Left1.png"));
                character = new Character("Squirtel", Up, Down, Right, Left);
            } else if (SelectedCharacter == 3) {
                Up.add(new Image("/Images/Torchic/Up0.png"));
                Up.add(new Image("/Images/Torchic/Up1.png"));
                Right.add(new Image("/Images/Torchic/Right0.png"));
                Right.add(new Image("/Images/Torchic/Right1.png"));
                Down.add(new Image("/Images/Torchic/Down0.png"));
                Down.add(new Image("/Images/Torchic/Down1.png"));
                Left.add(new Image("/Images/Torchic/Left0.png"));
                Left.add(new Image("/Images/Torchic/Left1.png"));
                character = new Character("Torchic", Up, Down, Right, Left);
            } else if (SelectedCharacter == 4) {
                Up.add(new Image("/Images/Treeco/Up0.png"));
                Up.add(new Image("/Images/Treeco/Up1.png"));
                Right.add(new Image("/Images/Treeco/Right0.png"));
                Right.add(new Image("/Images/Treeco/Right1.png"));
                Down.add(new Image("/Images/Treeco/Down0.png"));
                Down.add(new Image("/Images/Treeco/Down1.png"));
                Left.add(new Image("/Images/Treeco/Left0.png"));
                Left.add(new Image("/Images/Treeco/Left1.png"));
                character = new Character("Treeco", Up, Down, Right, Left);
            } else {
                showAlert("You didn't choose a character", "aww man");
            }
            mazeDisplayer.setImageProperties(character, wall, endPoint, solve, floor, outside);
            mazeDisplayer.setCharacterImage(character.getRightImage());
            mazeDisplayer.redraw();
        }
    }

    public void solveMaze(ActionEvent actionEvent) {
        if (solveMaze != null) {
            if (!showSolution) {
                BestFirstSearch bfs = new BestFirstSearch();
                solveMaze.setStartPosition(PlayerSpot);
                solutionMaze = bfs.solve(solveMaze);
                ArrayList<AState> solutionPath = solutionMaze.getSolutionPath();
                int i;
                for (i = 0; i < solutionPath.size(); ++i) {
                    if (maze.getMazeInfo(((MazeState) solutionPath.get(i)).getCurrentPosition().getRowIndex(), ((MazeState) solutionPath.get(i)).getCurrentPosition().getColumnIndex()) == 0) {
                        maze.setMazeInfo(((MazeState) solutionPath.get(i)).getCurrentPosition().getRowIndex(), ((MazeState) solutionPath.get(i)).getCurrentPosition().getColumnIndex(), 2);
                    }
                }
                showSolution = true;
            } else {
                for (int j = 0; j < maze.getNumOfRows(); j++) {
                    for (int k = 0; k < maze.getNumOfColumns(); k++) {
                        if (maze.getMazeInfo(j, k) == 2) {
                            maze.setMazeInfo(j, k, 0);
                        }
                    }
                }
                showSolution = false;
            }
            mazeDisplayer.setDimentions(maze);
            mazeDisplayer.updatePlayerSpot(PlayerSpot.getRowIndex(), PlayerSpot.getColumnIndex());
            mazeDisplayer.redraw();
        } else {
            showAlert("In order to solve a maze you need to have a maze", "Maze Not Found");
        }
    }

    public void saveGame() throws IOException, URISyntaxException {
        if (maze != null) {
            TextInputDialog dialog = new TextInputDialog();
            Maze savingMaze = new Maze(maze);
            dialog.setTitle("Saving Maze");
            String[] saveName = new String[1];
            dialog.setHeaderText("Enter the name of your save file:");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(name -> {
                saveName[0] = name;
            });
            for (int i = 0; i < maze.getNumOfRows(); i++) {
                for (int j = 0; j < maze.getNumOfColumns(); j++) {
                    if (savingMaze.getMazeInfo(i, j) != 1) {
                        savingMaze.setMazeInfo(i, j, 0);
                    }
                }
            }
            if (!saveName[0].equals("")) {
                if (!SaveFileExist(saveName[0])) {
                    FileOutputStream f = new FileOutputStream(new File(getClass().getResource("/SavedGames").getPath() + "/" + saveName[0]));
                    ObjectOutputStream o = new ObjectOutputStream(f);
                    String timeStamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
                    //write the maze,player's spot, Current zoom
                    o.writeObject(timeStamp);
                    o.write(maze.getNumOfRows());
                    o.write(maze.getNumOfColumns());
                    //o.writeObject(maze.toByteArray());
                    o.writeObject(savingMaze.toByteArray());
                    o.writeObject(PlayerSpot);
                    o.write(mazeDisplayer.getZoom());
                    o.writeObject(character.getName());
                    o.close();
                    f.close();
                    showAlert("Game has been saved successfully !", "SaveGame");
                } else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Dialog");
                    alert.setHeaderText("It seems like there is already a save file called like this");
                    alert.setContentText("Do you wish to overwrite it?");

                    Optional<ButtonType> result2 = alert.showAndWait();
                    if (result2.get() == ButtonType.OK) {
                        FileOutputStream f = new FileOutputStream(new File(getClass().getResource("/SavedGames").getPath() + "/" + saveName[0]));
                        ObjectOutputStream o = new ObjectOutputStream(f);
                        String timeStamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
                        //write the maze,player's spot, Current zoom
                        o.writeObject(timeStamp);
                        o.write(maze.getNumOfRows());
                        o.write(maze.getNumOfColumns());
                        //o.writeObject(maze.toByteArray());
                        o.writeObject(savingMaze.toByteArray());
                        o.writeObject(PlayerSpot);
                        o.write(mazeDisplayer.getZoom());
                        o.writeObject(saveName[0]);
                        o.writeObject(character.getName());
                        o.close();
                        f.close();
                        showAlert("Game has been saved successfully !", "SaveGame");
                    } else {

                    }
                }
            }
        } else {
            showAlert("In order to save a maze you need to have a maze", "Maze Not Found");
        }
    }

    private boolean SaveFileExist(String fileName) {
        final File folder = new File(getClass().getResource("/SavedGames").getPath());
        for (final File fileEntry : folder.listFiles()) {
            if (fileName.equals(fileEntry.getName())) {
                return true;
            }
        }
        return false;
    }

    public void openChoosingLoadedGame() throws IOException, ClassNotFoundException {
        Scene scene = new Scene(new Group());
        Stage stage = new Stage();
        scene.getStylesheets().addAll(this.getClass().getResource("LoadGame.css").toExternalForm());

        TableView table = new TableView();
        Label label = new Label("Choose a game from the table and then click on Load");
        table.setPrefHeight(500);
        table.setPrefWidth(300);
        //table.setEditable(true);

        TableColumn GameIdCol = new TableColumn("GameName");
        GameIdCol.setCellValueFactory(new PropertyValueFactory<>("GameName"));
        TableColumn DimensionsCol = new TableColumn("Dimensions");
        DimensionsCol.setCellValueFactory(new PropertyValueFactory<>("Dimensions"));
        TableColumn PlayerPositionCol = new TableColumn("PlayerPosition");
        PlayerPositionCol.setCellValueFactory(new PropertyValueFactory<>("PlayerPosition"));
        TableColumn DateCol = new TableColumn("Date");
        DateCol.setCellValueFactory(new PropertyValueFactory<>("Date"));

        table.getColumns().addAll(GameIdCol, DimensionsCol, PlayerPositionCol, DateCol);

        Button LoadButton = new Button("Load");
        Button ExitButton = new Button("Exit");

        LoadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                LoadedGame SelectedGame = (LoadedGame) table.getSelectionModel().getSelectedItem();
                if (SelectedGame != null) {//load the game
                    try {
                        loadGame(SelectedGame.getGameName());
                        stage.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    showAlert("Please choose a game to load", "oh hell no");
                }
            }
        });
        ExitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                stage.close();
            }
        });

        final File folder = new File(getClass().getResource("/SavedGames").getPath());
        for (final File fileEntry : folder.listFiles()) {
            FileInputStream fi = new FileInputStream(fileEntry);
            ObjectInputStream oi = new ObjectInputStream(fi);
            //read the properties
            String Date = (String) oi.readObject();
            int NumOfRows = oi.read();
            int NumOfColumns = oi.read();
            byte[] LoadedMaze = (byte[]) oi.readObject();
            Position loadedPosition = (Position) oi.readObject();
            int zoom = oi.read();
            String gameid = fileEntry.getName();
            LoadedGame lg = new LoadedGame(gameid, Date, NumOfRows + "x" + NumOfColumns, loadedPosition.getRowIndex() + "," + loadedPosition.getColumnIndex());
            table.getItems().add(lg);
            oi.close();
            fi.close();
        }


        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        HBox hbox = new HBox(10);
        hbox.setPadding(new Insets(10, 0, 0, 10));
        StackPane sp = new StackPane(hbox);
        sp.prefWidth(200);
        sp.prefHeight(200);

        HBox.setHgrow(table, Priority.ALWAYS);
        HBox.setHgrow(ExitButton, Priority.ALWAYS);
        HBox.setHgrow(LoadButton, Priority.ALWAYS);
        VBox.setVgrow(table, Priority.ALWAYS);
        VBox.setVgrow(ExitButton, Priority.ALWAYS);
        VBox.setVgrow(LoadButton, Priority.ALWAYS);
        hbox.setAlignment(Pos.CENTER);
        VBox.setVgrow(table, Priority.ALWAYS);
        vbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(LoadButton, ExitButton);
        vbox.getChildren().addAll(label, table, sp);
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        //table.prefWidthProperty().bind(stage.widthProperty());
        //table.prefHeightProperty().bind(vbox.heightProperty());
        hbox.prefHeightProperty().bind(table.heightProperty());
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        vbox.prefHeightProperty().bind(scene.heightProperty());
        vbox.prefWidthProperty().bind(scene.widthProperty());
        stage.setTitle("Choose loaded game");
        stage.setScene(scene);
        stage.setHeight(600);
        stage.setWidth(400);
        stage.show();
    }

    private int[] getDimensionsFromDialog() {
        final int[] RowsAndCols = new int[2];
        final boolean[] clickedCancel = new boolean[1];
        do {
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
            gridPane.add(new Label("Pick your character:"), 0, 2);

            ImageView firstCharacter = new ImageView("/Images/Pikachu/Down0.png");
            StackPane firstPane = new StackPane(firstCharacter);
            firstCharacter.setFitHeight(50);
            firstCharacter.setFitWidth(50);
            gridPane.add(firstPane, 0, 3);
            ImageView secondCharacter = new ImageView("/Images/Squirtel/Down0.png");
            StackPane secondPane = new StackPane(secondCharacter);
            secondCharacter.setFitHeight(50);
            secondCharacter.setFitWidth(50);
            gridPane.add(secondPane, 1, 3);
            ImageView thirdCharacter = new ImageView("/Images/Torchic/Down0.png");
            StackPane thirdPane = new StackPane(thirdCharacter);
            thirdCharacter.setFitHeight(50);
            thirdCharacter.setFitWidth(50);
            gridPane.add(thirdPane, 0, 4);
            ImageView fourthCharacter = new ImageView("/Images/Treeco/Down0.png");
            StackPane fourthPane = new StackPane(fourthCharacter);
            fourthCharacter.setFitHeight(50);
            fourthCharacter.setFitWidth(50);
            gridPane.add(fourthPane, 1, 4);
            firstPane.addEventHandler(MouseEvent.MOUSE_PRESSED,
                    new EventHandler<MouseEvent>() {

                        public void handle(MouseEvent e) {
                            if (SelectedCharacter != 1) {
                                firstPane.setBorder(new Border(new BorderStroke(Color.RED,
                                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                                secondPane.setBorder(new Border(new BorderStroke(Color.TRANSPARENT,
                                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                                thirdPane.setBorder(new Border(new BorderStroke(Color.TRANSPARENT,
                                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                                fourthPane.setBorder(new Border(new BorderStroke(Color.TRANSPARENT,
                                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                                SelectedCharacter = 1;
                            }
                        }
                    });
            secondPane.addEventHandler(MouseEvent.MOUSE_PRESSED,
                    new EventHandler<MouseEvent>() {

                        public void handle(MouseEvent e) {
                            if (SelectedCharacter != 2) {
                                secondPane.setBorder(new Border(new BorderStroke(Color.RED,
                                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                                firstPane.setBorder(new Border(new BorderStroke(Color.TRANSPARENT,
                                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                                thirdPane.setBorder(new Border(new BorderStroke(Color.TRANSPARENT,
                                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                                fourthPane.setBorder(new Border(new BorderStroke(Color.TRANSPARENT,
                                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                                SelectedCharacter = 2;
                            }
                        }
                    });
            thirdPane.addEventHandler(MouseEvent.MOUSE_PRESSED,
                    new EventHandler<MouseEvent>() {

                        public void handle(MouseEvent e) {
                            if (SelectedCharacter != 3) {
                                thirdPane.setBorder(new Border(new BorderStroke(Color.RED,
                                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                                secondPane.setBorder(new Border(new BorderStroke(Color.TRANSPARENT,
                                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                                firstPane.setBorder(new Border(new BorderStroke(Color.TRANSPARENT,
                                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                                fourthPane.setBorder(new Border(new BorderStroke(Color.TRANSPARENT,
                                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                                SelectedCharacter = 3;
                            }
                        }
                    });
            fourthPane.addEventHandler(MouseEvent.MOUSE_PRESSED,
                    new EventHandler<MouseEvent>() {

                        public void handle(MouseEvent e) {
                            if (SelectedCharacter != 4) {
                                fourthPane.setBorder(new Border(new BorderStroke(Color.RED,
                                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                                secondPane.setBorder(new Border(new BorderStroke(Color.TRANSPARENT,
                                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                                thirdPane.setBorder(new Border(new BorderStroke(Color.TRANSPARENT,
                                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                                firstPane.setBorder(new Border(new BorderStroke(Color.TRANSPARENT,
                                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                                SelectedCharacter = 4;
                            }
                        }
                    });
            dialog.getDialogPane().setContent(gridPane);

            Platform.runLater(() -> from.requestFocus());

            // Convert the result to a username-password-pair when the login button is clicked.
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == loginButtonType) {
                    clickedCancel[0] = false;
                    return new Pair<>(from.getText(), to.getText());
                }
                clickedCancel[0] = true;
                return null;
            });

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
            if(SelectedCharacter == 0){
                showAlert("You need to select a character","Error");
                clickedCancel[0] = false;
            }
        }
        while ((RowsAndCols[0] <= 0 || RowsAndCols[1] <= 0) && !clickedCancel[0] || SelectedCharacter == 0);
        if(clickedCancel[0]){
            SelectedCharacter = 0;
        }
        return new int[]{RowsAndCols[0], RowsAndCols[1]};
    }

    private void showAlert(String alertMessage, String title) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(alertMessage);
        alert.setTitle(title);
        alert.showAndWait();
    }

    public void endGame() throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("You have finished the game !\n you will now be sent to Main Menu screen");
        alert.setTitle("Well done !");
        alert.showAndWait();
        backToMenu();
    }
}
