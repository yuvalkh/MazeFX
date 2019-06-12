package View;

import javafx.scene.image.Image;

public class Floor {
        private Image image;

        public Floor(Image floor) {
            this.image = floor;
        }

        public Floor(Floor floor) {
            this.image = floor.image;
        }

        public Image GetFloor() {
            /********************** NOT FINISHED **************************/
            return this.image;
        }
    }

