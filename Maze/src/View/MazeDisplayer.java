package View;

import Model.*;
import Model.Character;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import algorithms.mazeGenerators.*;

public class MazeDisplayer extends Canvas {

    double characterPositionColumn;
    double characterPositionRow;
    int zoom = 10;
    Character character;
    Image characterImage;
    Wall wall;
    Floor floor;
    Solve solve;
    EndPoint endPoint;
    Outside outside;

    private Maze maze;

    public void setCharacterImage(Image characterr){
        characterImage = characterr;
    }

    @Override
    public double minHeight(double width)
    {
        return 50;
    }

    @Override
    public double maxHeight(double width)
    {
        return 2000;
    }

    @Override
    public double prefHeight(double width)
    {
        return 1200;
    }

    @Override
    public double prefWidth(double height) {
        return 1000;
    }
    @Override
    public double minWidth(double height)
    {
        return 50;
    }

    @Override
    public double maxWidth(double height)
    {
        return 2000;
    }

    @Override
    public boolean isResizable()
    {
        return true;
    }

    @Override
    public void resize(double width, double height)
    {
        if(width > height){
            super.setWidth(height);
            super.setHeight(height);
        }
        else{
            super.setWidth(width);
            super.setHeight(width);
        }
        //super.setWidth(width);
        //super.setHeight(height);
        redraw();
    }

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

    public void setImageProperties(Character myChar, Wall myWall, EndPoint myEndPoint, Solve mySolve,Floor myFloor,Outside myOutside){
        character = myChar;
        wall = myWall;
        endPoint = myEndPoint;
        solve = mySolve;
        floor = myFloor;
        outside = myOutside;
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

    public void redraw() {
        if (maze != null) {
            double canvasHeight = getWidth();
            double canvasWidth = getHeight();
            double cellHeight = canvasHeight / (2 * zoom + 1);
            double cellWidth = canvasWidth / (2 * zoom + 1);

            try {
                GraphicsContext gc = getGraphicsContext2D();
                gc.clearRect(0, 0, getWidth(), getHeight());
                gc.setFill(Color.BLUE);

                double printx = 0;//i
                double printy = 0;//j
                //Draw Maze
                for (int i = (int) characterPositionRow - zoom; i <= (int) characterPositionRow + zoom; i++) {
                    for (int j = (int) characterPositionColumn - zoom; j <= (int) characterPositionColumn + zoom; j++) {
                        if (i < 0 || j < 0 || i >= maze.getNumOfRows() || j >= maze.getNumOfColumns()) {
                            gc.fillRect(printy * cellHeight, printx * cellWidth, cellHeight, cellWidth);
                            gc.drawImage(outside.GetOutside(), printy * cellHeight, printx * cellWidth, cellHeight, cellWidth);
                        } else if (maze.getMazeInfo(i,j) == 1) {
                            gc.drawImage(floor.GetFloor(), printy * cellHeight, printx * cellWidth, cellHeight, cellWidth);
                            gc.drawImage(wall.GetWall(), printy * cellHeight, printx * cellWidth, cellHeight, cellWidth);
                        } else  if(i == maze.getGoalPosition().getRowIndex() && j == maze.getGoalPosition().getColumnIndex()){
                            gc.drawImage(floor.GetFloor(), printy * cellHeight, printx * cellWidth, cellHeight, cellWidth);
                            gc.drawImage(endPoint.GetPoint(), printy * cellHeight, printx * cellWidth, cellHeight, cellWidth);
                        } else if (maze.getMazeInfo(i,j) == 0) {
                            gc.drawImage(floor.GetFloor(), printy * cellHeight, printx * cellWidth, cellHeight, cellWidth);
                        } else if (maze.getMazeInfo(i,j) == 5) {
                            gc.drawImage(floor.GetFloor(), printy * cellHeight, printx * cellWidth, cellHeight, cellWidth);
                            gc.drawImage(characterImage, printy * cellHeight, printx * cellWidth, cellHeight, cellWidth);
                        } else if(maze.getMazeInfo(i,j) == 2){
                            gc.drawImage(floor.GetFloor(), printy * cellHeight, printx * cellWidth, cellHeight, cellWidth);
                            gc.drawImage(solve.GetSolve(), printy * cellHeight, printx * cellWidth, cellHeight, cellWidth);
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
