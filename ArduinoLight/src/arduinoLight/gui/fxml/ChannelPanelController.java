package arduinoLight.gui.fxml;

import arduinoLight.arduino.SerialConnection;
import arduinoLight.arduino.amblone.AmblonePackage;
import arduinoLight.arduino.amblone.AmbloneTransmission;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;


public class ChannelPanelController {

	private AmbloneTransmission ambloneTransmission;
	private SerialConnection serialConnection;
	
    @FXML
    private ComboBox<?> ChannelBox;

    @FXML
    private ComboBox<?> ChannelHolderBox;

    @FXML
    private ComboBox<?> ComPortBox;

    @FXML
    private Button ConnectButton;

    @FXML
    private Slider FrequencySlider;

    @FXML
    private ComboBox<?> PortBox;


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
        assert ChannelBox != null : "fx:id=\"ChannelBox\" was not injected: check your FXML file 'ChannelPane.fxml'.";
        assert ChannelHolderBox != null : "fx:id=\"ChannelHolderBox\" was not injected: check your FXML file 'ChannelPane.fxml'.";
        assert ComPortBox != null : "fx:id=\"ComPortBox\" was not injected: check your FXML file 'ChannelPane.fxml'.";
        assert ConnectButton != null : "fx:id=\"ConnectButton\" was not injected: check your FXML file 'ChannelPane.fxml'.";
        assert FrequencySlider != null : "fx:id=\"FrequencySlider\" was not injected: check your FXML file 'ChannelPane.fxml'.";
        assert PortBox != null : "fx:id=\"PortBox\" was not injected: check your FXML file 'ChannelPane.fxml'.";


    }

}

