package arduinoLight.util;

import java.io.IOException;

import arduinoLight.gui.ErrorDialog;

public class ErrorHandler {

	public static void showErrorDialog(Throwable throwable) {
		try {
			ErrorDialog e = new ErrorDialog(throwable);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
