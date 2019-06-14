package View;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class HelpPageController {
    @FXML
    public javafx.scene.control.Button ExitButton;

    public void handleCancelButton(){
        Stage oldStage = (Stage) ExitButton.getScene().getWindow();
        oldStage.close();
    }
}
