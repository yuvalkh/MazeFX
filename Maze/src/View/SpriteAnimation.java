package View;

import javafx.animation.Transition;

import javax.swing.text.html.ImageView;
import java.time.Duration;

public class SpriteAnimation extends Transition {

    private final ImageView imageView;
    private final int count;
    private final int columns;
    private int offSetX;
    private int offSetY;
    private final int width;
    private final int hight;

    public SpriteAnimation(ImageView imageView, Duration duration,
                           int count, int columns, int offsetx,
                           int offsety,int width,int hight){

        this.imageView = imageView;
        this.count = count;
        this.columns  = columns;
        this.offSetX = offsetx;
        this.offSetY = offsety;
        this.width = width;
        this.hight = hight;
    }


    @Override
    protected void interpolate(double frac) {

    }
}
