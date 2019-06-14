package View;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class OptionsPageController implements Initializable {
    @FXML
    public javafx.scene.control.Button ApplyButton;
    public javafx.scene.control.Button CancelButton;
    public javafx.scene.control.TextField NumOfThreadsTextField;
    public javafx.scene.control.CheckBox MusicCheckBox;
    public javafx.scene.control.ComboBox SearchingAlgorithmComboBox;
    public javafx.scene.control.ComboBox GeneratingMazeAlgorithmComboBox;


    public void handleApplyButton(){

    }

    public void handleCancelButton(){
        Stage oldStage = (Stage) ApplyButton.getScene().getWindow();
        oldStage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*SearchingAlgorithmComboBox.setValue(Server.Configurations.getSearchAlgorithm());
        GeneratingMazeAlgorithmComboBox.setValue(Server.Configurations.getGenerateMazeAlgorithm());
        NumOfThreadsTextField.setText(Integer.toString(Server.Configurations.getMaxNumberOfThreadsOnServer()));*/
        GeneratingMazeAlgorithmComboBox.setValue("Breadth First Search");
        NumOfThreadsTextField.setText("5");
    }
}
