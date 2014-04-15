package arduinoLight.gui.fxml;

import arduinoLight.channel.Channel;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class ChannelListView extends ListView<Channel>
{

	public ChannelListView(){
		super();
		setCellFactory(new Callback<ListView<Channel>, ListCell<Channel>> () {
			@Override
			public ListCell<Channel> call(ListView<Channel> arg0) {
				ChannelCell cell = new ChannelCell();
				cell.addChannelPositionChangeListener(new PositionChangeListener());
				return cell;
			}
		});
	}
	
    class PositionChangeListener implements ChannelPositionChangeListener{

		public void moveUp(ChannelCell source)
		{
			int index = source.getIndex();
			if(index - 1 >= 0){
				Channel channel = source.getItem();
				getItems().remove(channel);
				getItems().add(index - 1, channel);
			}
		}

		public void moveDown(ChannelCell source)
		{
			int index = source.getIndex();
			if(index + 1 <  getItems().size()){
				Channel channel = source.getItem();
				getItems().remove(channel);
				getItems().add(index + 1, channel);
			}
		}
    }
}
