package Model;

import javafx.scene.image.Image;

public class EndPoint {
    private Image image;


    public EndPoint(Image point) {
        this.image = point;
    }

    public EndPoint(EndPoint point) {
        this.image = point.image;
    }

    public Image GetPoint() {
        return this.image;
    }

}
