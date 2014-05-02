package arduinoLight.gui;

import javafx.event.EventHandler;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.util.Callback;
import arduinoLight.channel.Channel;

public class ChannelListView extends ListView<Channel> {

	public ChannelListView() {
		super();
		setCellFactory(new Callback<ListView<Channel>, ListCell<Channel>>() {
			@Override
			public ListCell<Channel> call(ListView<Channel> arg0) {
				ChannelCell cell = new ChannelCell();
				cell.addChannelPositionChangeListener(new PositionChangeListener());
				return cell;
			}
		});
		setOnDragOver(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				if (event.getDragboard().hasContent(ChannelCell.CHANNEL_DATA)) {
					event.acceptTransferModes(TransferMode.MOVE);
				}
				event.consume();
			}
		});
		setOnDragDropped(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				Dragboard board = event.getDragboard();
				if (board.hasContent(ChannelCell.CHANNEL_DATA)) {
					Channel channel = (Channel) board
							.getContent(ChannelCell.CHANNEL_DATA);
					if (!getItems().contains(channel)) {
						getItems().add(channel);
						event.setDropCompleted(true);
					} else {
						event.setDropCompleted(false);
					}
				}
			
				event.consume();
			}
		});
		setOnDragEntered(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				if (event.getDragboard().hasContent(ChannelCell.CHANNEL_DATA)) {
					Channel channel = (Channel) event.getDragboard()
							.getContent(ChannelCell.CHANNEL_DATA);
					if (!getItems().contains(channel)) {
						// TODO visual feedback for possible drop
					}
				}
			}
		});
		setOnDragExited(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent arg0) {
				// TODO remove visual feedback for possible drop
			}
		});
	}

	class PositionChangeListener implements ChannelCellPositionChangeListener {

		public void moveUp(ChannelCell source) {
			int index = source.getIndex();
			if (index - 1 >= 0) {
				Channel channel = source.getItem();
				getItems().remove(channel);
				getItems().add(index - 1, channel);
			}
		}

		public void moveDown(ChannelCell source) {
			int index = source.getIndex();
			if (index + 1 < getItems().size()) {
				Channel channel = source.getItem();
				getItems().remove(channel);
				getItems().add(index + 1, channel);
			}
		}
	}
}
