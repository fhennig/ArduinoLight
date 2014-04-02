package arduinoLight.gui.fxml;

import java.util.Enumeration;
import java.util.List;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import arduinoLight.arduino.SerialConnection;
import arduinoLight.arduino.amblone.AmbloneTransmission;
import arduinoLight.channel.Channel;
import arduinoLight.channelholder.Channelholder;
import arduinoLight.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.util.Callback;


public class ChannelPanelController {

	private AmbloneTransmission _ambloneTransmission;
	private SerialConnection _serialConnection;
	
    @FXML
    private ComboBox<Channel> ChannelBox;

    @FXML
    private ComboBox<Channelholder> ChannelHolderBox;

    @FXML
    private ComboBox<CommPortIdentifier> ComPortBox;

    @FXML
    private Button ConnectButton;

    @FXML
    private Slider FrequencySlider;

    @FXML
    private ComboBox<?> PortBox;

    
    public ChannelPanelController()
    {
    	_ambloneTransmission = new AmbloneTransmission();
    	_serialConnection = new SerialConnection();
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
        assert ChannelBox != null : "fx:id=\"ChannelBox\" was not injected: check your FXML file 'ChannelPane.fxml'.";
        assert ChannelHolderBox != null : "fx:id=\"ChannelHolderBox\" was not injected: check your FXML file 'ChannelPane.fxml'.";
        assert ComPortBox != null : "fx:id=\"ComPortBox\" was not injected: check your FXML file 'ChannelPane.fxml'.";
        assert ConnectButton != null : "fx:id=\"ConnectButton\" was not injected: check your FXML file 'ChannelPane.fxml'.";
        assert FrequencySlider != null : "fx:id=\"FrequencySlider\" was not injected: check your FXML file 'ChannelPane.fxml'.";
        assert PortBox != null : "fx:id=\"PortBox\" was not injected: check your FXML file 'ChannelPane.fxml'.";
        
        initCommPortBox();
        initChannelholderBox();
        
        try {
			_serialConnection.open((CommPortIdentifier)ComPortBox.getValue(), 100);
			_ambloneTransmission.start(_serialConnection, (int) FrequencySlider.getValue());
		} catch (PortInUseException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch(NullPointerException e2)
		{
			System.err.println("No Ports found"); //TODO better error handling
		}
		

    }
    
    private void initCommPortBox(){
    	Enumeration<CommPortIdentifier> ports = SerialConnection.getAvailablePorts();
    	while(ports.hasMoreElements())
    	{
    		ComPortBox.getItems().add(ports.nextElement());
    	}
    	
    	Callback<ListView<CommPortIdentifier>,ListCell<CommPortIdentifier>> cellFactory = new Callback<ListView<CommPortIdentifier>,ListCell<CommPortIdentifier>>(){
			@Override
			public ListCell<CommPortIdentifier> call(ListView<CommPortIdentifier> arg0) {
				final ListCell<CommPortIdentifier> cell = new ListCell<CommPortIdentifier>(){
                    @Override
                    protected void updateItem(CommPortIdentifier port, boolean bln) {
                        super.updateItem(port, bln);
                        if(port != null){
                            setText(port.getName());
                        }else{
                            setText(null);
                        }
                    }
                };
                return cell;
			}
    	};
    	ComPortBox.setCellFactory(cellFactory);
    	ComPortBox.setButtonCell(cellFactory.call(null));
    	ComPortBox.getSelectionModel().select(0);
    }
    
    private void initChannelholderBox(){
    	List<Channelholder> channelholders = Model.getInstance().getChannelholders();
    	for(Channelholder holder : channelholders)
    	{
    		ChannelHolderBox.getItems().add(holder);
    	}
    	ChannelHolderBox.getSelectionModel().select(0);
    }

}

