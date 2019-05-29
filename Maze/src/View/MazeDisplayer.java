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
import java.io.IOException;
import java.net.URL;

/**
 * Created by Aviadjo on 3/9/2017.
 */
public class MazeDisplayer extends Canvas {

    private StringProperty someProperty;

    public String getSomeProperty() {
        return someProperty.get();
    }

    public void setSomeProperty(String someProperty) {
        this.someProperty.set(someProperty);
    }

    private int[][] maze;

    public void setDimentions(int[][] maze){
        this.maze = maze;
        redraw();
    }

    public void setDimentions(Maze newMaze){
        maze = new int[newMaze.getNumOfRows()][newMaze.getNumOfColumns()];
        for (int i = 0; i < newMaze.getNumOfRows(); i++) {
            for (int j = 0; j < newMaze.getNumOfColumns(); j++) {
                maze[i][j] = newMaze.getMazeInfo(i,j);
            }
        }
        redraw();
    }

    public void redraw() {
        if (maze != null){




            /*
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            double cellHeight = canvasHeight/maze.length;
            double cellWidth = canvasWidth/maze[0].length;

            GraphicsContext graphicsContext2D = getGraphicsContext2D();
            graphicsContext2D.clearRect(0,0,canvasWidth,canvasHeight); //Clean the Canvas
            graphicsContext2D.setFill(Color.BLUE); //Set color to the context

            //Draw maze

            for (int row = 0; row < maze.length; row++) {
                for (int column = 0; column < maze[row].length; column++) {
                    if (maze[row][column] == 1){
                        graphicsContext2D.fillRect(column*cellHeight,row*cellWidth,cellWidth,cellHeight); //Draw Wall
                    }
                    if(maze[row][column] == 2){
                        graphicsContext2D.setFill(Color.BROWN); //Set color to the context
                        graphicsContext2D.fillRect(column*cellHeight,row*cellWidth,cellWidth,cellHeight); //Draw Solution
                        graphicsContext2D.setFill(Color.BLUE); //Set color to the context
                    }
                    if(maze[row][column] == 5){
                        graphicsContext2D.setFill(Color.YELLOW);
                        graphicsContext2D.fillRect(column*cellHeight,row*cellWidth,cellWidth,cellHeight); //Draw Solution
                        graphicsContext2D.setFill(Color.BLUE); //Set color to the context
                    }
                }
            }*/
        }
    }
}
