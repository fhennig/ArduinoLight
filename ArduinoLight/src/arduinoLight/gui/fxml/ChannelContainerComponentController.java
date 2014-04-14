package arduinoLight.gui.fxml;

import arduinoLight.channel.Channel;
import arduinoLight.channel.ThreadingChannel;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.util.Callback;

//TODO sein ernie...
public class ChannelContainerComponentController {

    @FXML //TODO nicht wirklich benötigt
    private Label _channelLabel;

    @FXML
    private ListView<Channel> _channelList;

    @FXML
    private Label _maxChannelLabel;

    @FXML
    void initialize() {
        assert _channelLabel != null : "fx:id=\"_channelLabel\" was not injected: check your FXML file 'ChannelContainerComponent.fxml'.";
        assert _channelList != null : "fx:id=\"_channelList\" was not injected: check your FXML file 'ChannelContainerComponent.fxml'.";
        assert _maxChannelLabel != null : "fx:id=\"_maxChannelLabel\" was not injected: check your FXML file 'ChannelContainerComponent.fxml'.";

        initListView();
        _channelList.getItems().add(new ThreadingChannel(0));
    }

	private void initListView() {
		_channelList.setCellFactory(new Callback<ListView<Channel>, ListCell<Channel>> () {
			@Override
			public ListCell<Channel> call(ListView<Channel> arg0) {
				return new ChannelCell();
			}
		});
	}
	
	class ChannelCell extends ListCell<Channel>{
		
		ToggleButton _button = new ToggleButton("Filter");
		HBox _box = new HBox();
		Label _name = new Label();
		
		public ChannelCell(){
		    _button.setAlignment(Pos.CENTER_RIGHT);
		    _name.setAlignment(Pos.CENTER_LEFT);
			HBox.setHgrow(_name, Priority.ALWAYS);
			_name.setPrefWidth(100);
			_box.getChildren().add(_name);
			_box.getChildren().add(_button);
		}
		
		@Override
		protected void updateItem(Channel item, boolean empty) {
			super.updateItem(item, empty);
				setText(null);
				if(item != null){
					_name.setText(item.getName());
					setGraphic(_box);
				}
		}
	}
}
