package arduinoLight.channelhandler;

import arduinoLight.util.Color;

public class ChannelChangedEventArgs
{
	private Color _newColor;

	public Color getNewColor() {
		return _newColor;
	}

	public void setNewColor(Color _newColor) {
		this._newColor = _newColor;
	}
}
