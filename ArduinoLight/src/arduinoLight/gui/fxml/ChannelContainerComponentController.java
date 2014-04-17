package arduinoLight.gui.fxml;

import arduinoLight.model.Model;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

//TODO sein ernie...
public class ChannelContainerComponentController {

	@FXML
	// TODO nicht wirklich benoetigt
	private Label _channelLabel;

	@FXML
	private ChannelListView _channelList;

	@FXML
	private Label _maxChannelLabel;

	@FXML
	void initialize() {
		assert _channelLabel != null : "fx:id=\"_channelLabel\" was not injected: check your FXML file 'ChannelContainerComponent.fxml'.";
		assert _channelList != null : "fx:id=\"_channelList\" was not injected: check your FXML file 'ChannelContainerComponent.fxml'.";
		assert _maxChannelLabel != null : "fx:id=\"_maxChannelLabel\" was not injected: check your FXML file 'ChannelContainerComponent.fxml'.";

		_channelList.getItems().add(
				Model.getInstance().getChannelFactory()
						.newChannel("TestChannel 1"));
		_channelList.getItems().add(
				Model.getInstance().getChannelFactory()
						.newChannel("TestChannel 2"));
	}
}
