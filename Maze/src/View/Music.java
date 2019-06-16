package View;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class Music extends Application {

    private static boolean isMusicOn = true;
    private static Media currentMusic;
    private static MediaPlayer musicPlayer;
    private static String MusicPath;

    public Music(String Path) {//private constructor to make this class static
        MusicPath = Path;

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        currentMusic = new Media( MusicPath);
        musicPlayer = new MediaPlayer(currentMusic);
        musicPlayer.play();
    }

    public static boolean isMusicOn(){
        return isMusicOn;
    }

    /*public static void setMusic(String nameOfMusic) {
        currentMusic = new Media( "file://" + nameOfMusic + ".wav");
        musicPlayer = new MediaPlayer(currentMusic);
        musicPlayer.play();
    }*/
    public static void SetPath (String Path) {
        MusicPath = Path;
    }
    public static void setMusicOff() {
        musicPlayer.stop();
    }

    public static void setMusicOn() {
        musicPlayer.play();
    }
}
