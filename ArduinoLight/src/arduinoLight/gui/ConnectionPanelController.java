package arduinoLight.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;


public class ConnectionPanelController {

	
    @FXML
    private Button ConnectButton;

    @FXML
    private Slider FrequencySlider;

    @FXML
    private ComboBox<?> PortBox;

    
    public ConnectionPanelController()
    {
    	
    }

    @FXML
    void handleChannelBoxAction(ActionEvent event) {
    	
    }

    @FXML
    void handleChannelHolderBoxAction(ActionEvent event) {
    }

    @FXML
    void handleComPortBoxAction(ActionEvent event) {
    }

    @FXML
    void handleConnectButtonAction(ActionEvent event) {
    }

    @FXML
    void handlePortBoxAction(ActionEvent event) {
    }

    @FXML
    void initialize() {
        assert ConnectButton != null : "fx:id=\"ConnectButton\" was not injected: check your FXML file 'ChannelPane.fxml'.";
        assert FrequencySlider != null : "fx:id=\"FrequencySlider\" was not injected: check your FXML file 'ChannelPane.fxml'.";
        assert PortBox != null : "fx:id=\"PortBox\" was not injected: check your FXML file 'ChannelPane.fxml'.";
    }
}

