package Model;

import javafx.scene.image.Image;

public class Outside {
    private Image image;

    public Outside(Image outside) {
        this.image = outside;
    }

    public Outside(Outside outside) {
        this.image = outside.image;
    }

    public Image GetOutside() {
        return this.image;
    }
}
