package Model;

import javafx.scene.image.Image;

public class Wall {
    private Image myWallImage;

    public Wall(Image WallImage) {
        this.myWallImage = WallImage;
    }

    public Wall(Wall wall) {
        this.myWallImage = wall.myWallImage;
    }

    public Image GetWall() {
        return myWallImage;
    }
}
