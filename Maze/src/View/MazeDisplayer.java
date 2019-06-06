package View;

import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import algorithms.mazeGenerators.*;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Aviadjo on 3/9/2017.
 */
public class MazeDisplayer extends Canvas {

    private StringProperty someProperty;

    double characterPositionColumn;
    double characterPositionRow;
    int zoom = 10;

    public String getSomeProperty() {
        return someProperty.get();
    }

    public void setSomeProperty(String someProperty) {
        this.someProperty.set(someProperty);
    }

    private int[][] maze;

    public void ZoomOut() {
        this.zoom++;
    }

    public void ZoomIn() {
        this.zoom--;
    }

    public void setDimentions(int[][] maze) {
        this.maze = maze;
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] == 5) {
                    characterPositionColumn = j;
                    characterPositionRow = i;
                }
            }
        }
        redraw();
    }

    public void setDimentions(Maze newMaze) {
        maze = new int[newMaze.getNumOfRows()][newMaze.getNumOfColumns()];
        for (int i = 0; i < newMaze.getNumOfRows(); i++) {
            for (int j = 0; j < newMaze.getNumOfColumns(); j++) {
                maze[i][j] = newMaze.getMazeInfo(i, j);
            }
        }
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] == 5) {
                    characterPositionColumn = j;
                    characterPositionRow = i;
                }
            }
        }
        redraw();
    }

    public void redraw() {
        if (maze != null) {
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            double cellHeight = canvasHeight / (2 * zoom + 1);
            double cellWidth = canvasWidth / (2 * zoom + 1);


            try {
                Image wallImage = new Image("BlueWall.jpg");
                Image characterImage = new Image("Pacman.jpg");

                GraphicsContext gc = getGraphicsContext2D();
                gc.clearRect(0, 0, getWidth(), getHeight());
                gc.setFill(Color.BLACK);

                double printx = 0;//i
                double printy = 0;//j
                //Draw Maze
                for (int i = (int) characterPositionRow - zoom; i <= (int) characterPositionRow + zoom; i++) {
                    for (int j = (int) characterPositionColumn - zoom; j <= (int) characterPositionColumn + zoom; j++) {
                        if (i < 0 || j < 0 || i >= maze.length || j >= maze[i].length) {
                            gc.fillRect(printy * cellHeight, printx * cellWidth, cellHeight, cellWidth);
                        } else if (maze[i][j] == 1) {
                            gc.fillRect(printy * cellHeight, printx * cellWidth, cellHeight, cellWidth);
                            gc.drawImage(wallImage, printy * cellHeight, printx * cellWidth, cellHeight, cellWidth);
                        } else if (maze[i][j] == 0) {
                            gc.fillRect(printy * cellHeight, printx * cellWidth, cellHeight, cellWidth);
                        } else if (maze[i][j] == 5) {
                            //characterPositionColumn = j;
                            //characterPositionRow = i;
                            gc.drawImage(characterImage, printy * cellHeight, printx * cellWidth, cellHeight, cellWidth);
                        }
                        printy++;
                    }
                    printy = 0;
                    printx++;
                }

                //Draw Character
                //gc.setFill(Color.RED);
                //gc.fillOval(characterPositionColumn * cellHeight, characterPositionRow * cellWidth, cellHeight, cellWidth);
                //gc.drawImage(characterImage, characterPositionColumn * cellHeight, characterPositionRow * cellWidth, cellHeight, cellWidth);
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
    }
}
