package ViewModel;

public class LoadedGame {
    private String GameName;
    private String Date;
    private String Dimensions;
    private String PlayerPosition;

    public LoadedGame(String GameName, String date, String dimensions, String playerPosition) {
        this.GameName = GameName;
        Date = date;
        Dimensions = dimensions;
        PlayerPosition = playerPosition;
    }

    public String getDate() {
        return Date;
    }

    public String getGameName() {
        return GameName;
    }

    public void setGameName(String gameName) {
        GameName = gameName;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDimensions() {
        return Dimensions;
    }

    public void setDimensions(String dimensions) {
        Dimensions = dimensions;
    }

    public String getPlayerPosition() {
        return PlayerPosition;
    }

    public void setPlayerPosition(String playerPosition) {
        PlayerPosition = playerPosition;
    }
}
