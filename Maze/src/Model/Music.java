package Model;

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
        currentMusic = new Media(MusicPath);
        musicPlayer = new MediaPlayer(currentMusic);
        musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        if(isMusicOn) {
            musicPlayer.play();
        }

    }

    public static boolean isMusicOn(){
        return isMusicOn;
    }

    public static void stopMusic() {
        musicPlayer.stop();
    }

    public static void setMusicOff() {
        isMusicOn = false;
        musicPlayer.stop();
    }

    public static void setMusicOn() {
        isMusicOn = true;
        musicPlayer.play();
    }

}
