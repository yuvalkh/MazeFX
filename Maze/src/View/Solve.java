package View;

import javafx.scene.image.Image;

public class Solve {
    private Image image;


    public Solve(Image solve) {
        this.image = solve;
    }

    public Solve(Solve point) {
        this.image = point.image;
    }

    public Image GetSolve() {
        return this.image;
    }
}
