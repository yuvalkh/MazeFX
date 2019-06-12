package View;

public class LoadedGame {
    private String GameID;
    private String Date;
    private String Dimensions;
    private String PlayerPosition;

    public LoadedGame(String GameID, String date, String dimensions, String playerPosition) {
        this.GameID = GameID;
        Date = date;
        Dimensions = dimensions;
        PlayerPosition = playerPosition;
    }

    public String getDate() {
        return Date;
    }

    public String getGameID() {
        return GameID;
    }

    public void setGameID(String gameID) {
        GameID = gameID;
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
