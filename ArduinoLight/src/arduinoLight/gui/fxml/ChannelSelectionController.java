package arduinoLight.gui.fxml;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import arduinoLight.channel.Channel;
import arduinoLight.channelholder.ModifiableChannelholder;
import arduinoLight.channelholder.ambientlight.Ambientlight;
import arduinoLight.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;

public class ChannelSelectionController {
	
	private ModifiableChannelholder _provider;
	private List<ActionListener> _listeners = new ArrayList<ActionListener>();
	
	@FXML
	private ComboBox<Channel> _channelBox;
	
	@FXML
	private ToggleButton _startButton;
	
	@FXML
	private Slider _frequencySlider;

	
	public ChannelSelectionController(){
		_provider = Model.getInstance().getAmbientlight();
	}


    @FXML
    void channelAdded(ActionEvent event) {
    	Channel newChannel = Model.getInstance().getChannelFactory().newChannel();
		_provider.addChannel(newChannel);
		_channelBox.getItems().add(newChannel);
		_channelBox.getSelectionModel().select(newChannel);
    }

    @FXML
    void channelRemoved(ActionEvent event) {
    	if(!_channelBox.getItems().isEmpty()){
	    	_provider.removeChannel(_channelBox.getSelectionModel().getSelectedItem());
	    	_channelBox.getItems().remove(_channelBox.getSelectionModel().getSelectedIndex());
    	} else{
    		//TODO error handling
    	}
    }

    @FXML
    void selectedChannelChanged(ActionEvent event) {
    	java.awt.event.ActionEvent evt = new java.awt.event.ActionEvent(this, 0, "CHANNEL_CHANGE"); //TODO Better Conversion of ActionEvents
		for (ActionListener l : _listeners)
			l.actionPerformed(evt);
    }

    @FXML
    void startPressed(ActionEvent event) {
    	Ambientlight al = (Ambientlight)_provider; //TODO fix the use of casting
    	if(al.isActive())
    	{
    		al.stop();
    	}
    	else
    	{
    		al.start((int)_frequencySlider.getValue());
    	}
    }

    @FXML
    void initialize() {
        assert _channelBox != null : "fx:id=\"channelBox\" was not injected: check your FXML file 'ChannelSelection.fxml'.";
    }
	
	public void addActionListener(ActionListener l)
	{
		_listeners.add(l);
	}
	
	public void removeActionListener(ActionListener l)
	{
		_listeners.remove(l);
	}
}
