package arduinoLight.gui.fxml;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import arduinoLight.channel.Channel;

public class ChannelCell extends ListCell<Channel>{
	
	public static final DataFormat CHANNEL =
		    new DataFormat("channel");
	
	ArrayList<ChannelPositionChangeListener> _listeners  = new ArrayList<ChannelPositionChangeListener>();
	
	ToggleButton _filterButton = new ToggleButton("Filter");
	Button _upButton = new Button("^");
	Button _downButton = new Button("V");
	GridPane _grid = new GridPane();
	Label _name = new Label();
	
	public ChannelCell(){
		GridPane.setHgrow(_name, Priority.ALWAYS);
		GridPane.setVgrow(_filterButton, Priority.ALWAYS);
		_filterButton.setMaxHeight(Double.MAX_VALUE);
		_grid.add(_name, 0, 0, 1, 2);
		_grid.add(_filterButton, 1, 0, 1, 2);
		_grid.add(_upButton, 2, 0);
		_grid.add(_downButton, 2, 1);
		_filterButton.setOnMousePressed(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent me) {
				getItem().setName("TEST");
		    }
		});
		_upButton.setOnMousePressed(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent me) {
				firePositionUpEvent();
		    }
		});
		_downButton.setOnMousePressed(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent me) {
				firePositionDownEvent();
		    }
		});
		setOnDragDetected(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				Dragboard board = startDragAndDrop(TransferMode.MOVE);
				
				ClipboardContent content = new ClipboardContent();
				content.put(CHANNEL, getItem());
				board.setContent(content);
				arg0.consume();
			}
		});
	}
	
	@Override
	protected void updateItem(Channel item, boolean empty) {
		super.updateItem(item, empty);
		setText(null);
		if(item != null){
			_name.setText(item.getName());
			setGraphic(_grid);
		} else {
			setGraphic(null);
		}
	}
	
	/**
	 * aua
	 */
	public void addChannelPositionChangeListener(ChannelPositionChangeListener listener){
		_listeners.add(listener);
	}
	
	private void firePositionUpEvent(){
		for(ChannelPositionChangeListener listener : _listeners){
			listener.moveUp(this);
		}
	}
	
	private void firePositionDownEvent(){
		for(ChannelPositionChangeListener listener : _listeners){
			listener.moveDown(this);
		}
	}
}