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
import javax.xml.ws.Endpoint;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Aviadjo on 3/9/2017.
 */
public class MazeDisplayer extends Canvas {

    double characterPositionColumn;
    double characterPositionRow;
    int zoom = 10;
    //Position start;
    //Position end;

    //private int[][] maze;
    private Maze maze;

    public int getZoom(){
        return zoom;
    }

    public void setZoom(int Zoom){
        this.zoom = Zoom;
    }

    public void ZoomOut() {
        this.zoom++;
    }

    public void ZoomIn() {
        if(zoom > 2) {
            this.zoom--;
        }
    }


    public void updatePlayerSpot(double positionRow,double positionColumn){
        characterPositionRow = positionRow;
        characterPositionColumn = positionColumn;
    }

    public void setDimentions(Maze newMaze) {
        maze = new Maze(newMaze);
        characterPositionColumn = maze.getStartPosition().getColumnIndex();
        characterPositionRow = maze.getStartPosition().getRowIndex();
    }

    public void redraw(Character myChar, Wall myWall, EndPoint myEndPoint, Solve mySolve,Floor myFloor) {
        if (maze != null) {
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            double cellHeight = canvasHeight / (2 * zoom + 1);
            double cellWidth = canvasWidth / (2 * zoom + 1);


            try {
                //Image wallImage = new Image("BlueWall.jpg");
                //Image characterImage = new Image("Pacman.png");
                Image wallImage = myWall.GetWall();
                Image solutionImage = mySolve.GetSolve();
                Image EndPointImage = myEndPoint.GetPoint();
                Image characterImage = myChar.GetChar();
                Image floorImage = myFloor.GetFloor();
                GraphicsContext gc = getGraphicsContext2D();
                gc.clearRect(0, 0, getWidth(), getHeight());
                gc.setFill(Color.BLACK);

                double printx = 0;//i
                double printy = 0;//j
                //Draw Maze
                for (int i = (int) characterPositionRow - zoom; i <= (int) characterPositionRow + zoom; i++) {
                    for (int j = (int) characterPositionColumn - zoom; j <= (int) characterPositionColumn + zoom; j++) {
                        if (i < 0 || j < 0 || i >= maze.getNumOfRows() || j >= maze.getNumOfColumns()) {
                            gc.fillRect(printy * cellHeight, printx * cellWidth, cellHeight, cellWidth);
                        } else if (maze.getMazeInfo(i,j) == 1) {
                            gc.fillRect(printy * cellHeight, printx * cellWidth, cellHeight, cellWidth);
                            gc.drawImage(wallImage, printy * cellHeight, printx * cellWidth, cellHeight, cellWidth);
                        } else if(i == maze.getGoalPosition().getRowIndex() && j == maze.getGoalPosition().getColumnIndex()){
                            gc.drawImage(EndPointImage, printy * cellHeight, printx * cellWidth, cellHeight, cellWidth);
                        } else if (maze.getMazeInfo(i,j) == 0) {
                            gc.drawImage(floorImage, printy * cellHeight, printx * cellWidth, cellHeight, cellWidth);
                        } else if (maze.getMazeInfo(i,j) == 5) {
                            gc.drawImage(characterImage, printy * cellHeight, printx * cellWidth, cellHeight, cellWidth);
                        } else if(maze.getMazeInfo(i,j) == 2){
                            gc.drawImage(solutionImage, printy * cellHeight, printx * cellWidth, cellHeight, cellWidth);
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
