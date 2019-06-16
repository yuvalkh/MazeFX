package Model;

import javafx.scene.image.Image;

import java.io.Serializable;
import java.util.List;

public class Character implements Serializable {

    private String name;
    private List<Image> GoingUP;
    private int UpCounter = 0;
    private List<Image> GoingDown;
    private int DownCounter = 0;
    private List<Image> GoingRight;
    private int RightCounter = 0;
    private List<Image> GoingLeft;
    private int LeftCounter = 0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Character(String name,List<Image> Up, List<Image> Down, List<Image> Right, List<Image> Left) {
        this.name = name;
        this.GoingUP = Up;
        this.GoingDown = Down;
        this.GoingRight = Right;
        this.GoingLeft = Left;

    }

    public Image getUpImage() {
        if (UpCounter >= GoingUP.size()) {
            UpCounter = 0;
        }
        if (GoingUP != null) {
            Image image = GoingUP.get(UpCounter);
            UpCounter++;
            return image;
        }
        System.out.println("That ImageList(GoingUP) is Empty or Nullified");
        return null;
    }

    public Image getDownImage() {
        if (DownCounter >= GoingDown.size()) {
            DownCounter = 0;
        }
        if (GoingDown != null) {
            Image image = GoingDown.get(DownCounter);
            DownCounter++;
            return image;
        }
        System.out.println("That ImageList(GoingDown) is Empty or Nullified");
        return null;
    }

    public Image getRightImage() {
        if (RightCounter >= GoingRight.size()) {
            RightCounter = 0;
        }
        if (GoingRight!= null) {
            Image image = GoingRight.get(RightCounter);
            RightCounter++;
            return image;
        }
        System.out.println("That ImageList(GoingRight) is Empty or Nullified");
        return null;
    }

    public Image getLeftImage() {
        if (LeftCounter >= GoingLeft.size()) {
            LeftCounter = 0;
        }
        if (GoingLeft!= null) {
            Image image = GoingLeft.get(LeftCounter);
            LeftCounter++;
            return image;
        }
        System.out.println("That ImageList(GoingLeft) is Empty or Nullified");
        return null;
    }

}