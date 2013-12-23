package arduinoLight.gui;

import gnu.io.CommPortIdentifier;

public class ComboBoxPortItem {

	CommPortIdentifier _port;
	
	public ComboBoxPortItem(CommPortIdentifier port){
		_port = port;
	}
	
	@Override
	public String toString() {
		return _port.getName();
	}
	
	public CommPortIdentifier getPort(){
		return _port;
	}
	
}
