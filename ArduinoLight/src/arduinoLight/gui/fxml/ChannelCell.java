package arduinoLight.gui.fxml;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import arduinoLight.channel.Channel;

public class ChannelCell extends ListCell<Channel>{
	
	ToggleButton _filterButton = new ToggleButton("Filter");
	Button _upButton = new Button("^");
	Button _downButton = new Button("V");
	GridPane _grid = new GridPane();
	Label _name = new Label();
	ChannelContainerComponentController _parent;
	
	public ChannelCell(ChannelContainerComponentController parent){
		_parent = parent;
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
				_parent.moveUp(getIndex());
		    }
		});
		_downButton.setOnMousePressed(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent me) {
				_parent.moveDown(getIndex());
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
		}
	}
}