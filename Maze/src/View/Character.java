package View;
import javafx.scene.image.Image;

public class Character {
    private Image image;


    public Character(Image Char) {
        this.image = Char;
    }

    public Character(Character Char) {
        this.image = Char.image;
    }

    public Image GetChar() {
        /********************** NOT FINISHED **************************/
        return this.image;
    }

}
