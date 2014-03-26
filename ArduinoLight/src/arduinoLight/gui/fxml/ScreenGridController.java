package arduinoLight.gui.fxml;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;


public class ScreenGridController {

    @FXML
    private Button ClearButton;

    @FXML
    private ComboBox<?> ColumnBox;

    @FXML
    private ComboBox<?> RowBox;


    @FXML
    void handleClearButtonAction(ActionEvent event) {
    }

    @FXML
    void handleColumnBoxAction(ActionEvent event) {
    }

    @FXML
    void handleRowBoxAction(ActionEvent event) {
    }

    @FXML
    void initialize() {
        assert ClearButton != null : "fx:id=\"ClearButton\" was not injected: check your FXML file 'ScreenGrid.fxml'.";
        assert ColumnBox != null : "fx:id=\"ColumnBox\" was not injected: check your FXML file 'ScreenGrid.fxml'.";
        assert RowBox != null : "fx:id=\"RowBox\" was not injected: check your FXML file 'ScreenGrid.fxml'.";


    }

}
