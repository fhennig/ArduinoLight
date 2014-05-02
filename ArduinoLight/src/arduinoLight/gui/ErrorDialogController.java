package arduinoLight.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;


public class ErrorDialogController {

    @FXML
    private ImageView _errorImage;

    @FXML
    private TextArea _errorTextField;

    @FXML
    private TextArea _messageTextField;

    @FXML
    private Button _okButton;


    @FXML
    void initialize() {
        assert _errorImage != null : "fx:id=\"_errorImage\" was not injected: check your FXML file 'ErrorDialog.fxml'.";
        assert _errorTextField != null : "fx:id=\"_errorTextField\" was not injected: check your FXML file 'ErrorDialog.fxml'.";
        assert _messageTextField != null : "fx:id=\"_messageTextField\" was not injected: check your FXML file 'ErrorDialog.fxml'.";
        assert _okButton != null : "fx:id=\"_okButton\" was not injected: check your FXML file 'ErrorDialog.fxml'.";


    }

}

