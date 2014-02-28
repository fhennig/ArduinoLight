package arduinoLight.gui.fxml;

import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import arduinoLight.channel.Channel;
import arduinoLight.channelholder.ChannelsChangedEventArgs;
import arduinoLight.channelholder.ChannelsChangedListener;
import arduinoLight.channelholder.ModifiableChannelholder;
import arduinoLight.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;


public class ChannelSelectionController implements ChannelsChangedListener {
	
	private ModifiableChannelholder _provider;
	
	@FXML
	private ComboBox<Channel> channelBox;
	@FXML
	private Button removeButton;
	private List<ActionListener> _listeners = new ArrayList<ActionListener>();
	@FXML
	private ResourceBundle resources;
	
	@FXML
	private URL location;
	 
	
	public ChannelSelectionController(){
		//_provider = provider;
	}


    @FXML
    void channelAdded(ActionEvent event) {
    	Channel newChannel = Model.getInstance().getChannelFactory().newChannel();
		_provider.addChannel(newChannel);
		channelBox.getSelectionModel().select(newChannel);
    }

    @FXML
    void channelRemoved(ActionEvent event) {
    	_provider.removeChannel(channelBox.getSelectionModel().getSelectedItem());
    }

    @FXML
    void refreshRateChanged(ActionEvent event) {
    	
    }

    @FXML
    void selectedChannelChanged(ActionEvent event) {
    	java.awt.event.ActionEvent evt = new java.awt.event.ActionEvent(this, 0, "CHANNEL_CHANGE"); //TODO Better Conversion of ActionEvents
		for (ActionListener l : _listeners)
			l.actionPerformed(evt);
    }

    @FXML
    void startPressed(ActionEvent event) {
    }

    @FXML
    void initialize() {


    }
    
    public void addModel(ModifiableChannelholder provider){
    	_provider = provider;
    }

	@Override
	public void channelsChanged(ChannelsChangedEventArgs e) {
		int channels = _provider.getChannels().size();
		if (channels < 2)
			removeButton.setDisable(true);
		else
			removeButton.setDisable(false);
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
