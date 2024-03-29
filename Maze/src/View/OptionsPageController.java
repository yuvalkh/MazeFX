package View;

import Model.Music;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
    public boolean hadMusicBefore;

    public void handleApplyButton() {
        Server.Configurations.setGenerateMazeAlgorithm(GeneratingMazeAlgorithmComboBox.getSelectionModel().getSelectedItem().toString());
        Server.Configurations.setSearchAlgorithm(SearchingAlgorithmComboBox.getSelectionModel().getSelectedItem().toString());
        Server.Configurations.setMaxNumberOfThreadsOnServer(NumOfThreadsTextField.getText());
        if (MusicCheckBox.isSelected()) {
            Music.setMusicOn();
        } else {
            Music.setMusicOff();
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Options applied !");
        alert.setTitle("Options");
        alert.showAndWait();
        Stage oldStage = (Stage) ApplyButton.getScene().getWindow();
        oldStage.close();
    }

    public void handleCancelButton() {
        Stage oldStage = (Stage) ApplyButton.getScene().getWindow();
        oldStage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (Music.isMusicOn()) {
            MusicCheckBox.setSelected(true);
            hadMusicBefore = true;
        } else {
            MusicCheckBox.setSelected(false);
            hadMusicBefore = false;
        }
        SearchingAlgorithmComboBox.setValue(Server.Configurations.getSearchAlgorithm());
        GeneratingMazeAlgorithmComboBox.setValue(Server.Configurations.getGenerateMazeAlgorithm());
        NumOfThreadsTextField.setText(Integer.toString(Server.Configurations.getMaxNumberOfThreadsOnServer()));
        SearchingAlgorithmComboBox.getItems().add("Breadth First Search");
        SearchingAlgorithmComboBox.getItems().add("Depth First Search");
        SearchingAlgorithmComboBox.getItems().add("Best First Search");
        GeneratingMazeAlgorithmComboBox.getItems().add("EmptyMazeGenerator");
        GeneratingMazeAlgorithmComboBox.getItems().add("SimpleMazeGenerator");
        GeneratingMazeAlgorithmComboBox.getItems().add("MyMazeGenerator");
    }
}
