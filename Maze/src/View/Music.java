package View;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Music {

    private static boolean isMusicOn = true;
    private static Media currentMusic;
    private static MediaPlayer musicPlayer;

    private Music() {//private constructor to make this class static

    }

    public static boolean isMusicOn(){
        return isMusicOn;
    }

    public static void setMusic(String nameOfMusic) {
        currentMusic = new Media("resource/" + nameOfMusic + ".mp3");
        musicPlayer = new MediaPlayer(currentMusic);
        musicPlayer.play();
    }

    public static void setMusicOff() {
        musicPlayer.stop();
    }

    public static void setMusicOn() {
        musicPlayer.play();
    }
}
