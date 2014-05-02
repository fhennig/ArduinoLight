package arduinoLight.util;

import java.lang.Thread.UncaughtExceptionHandler;

public class CustomUncaughtExceptionHandler implements UncaughtExceptionHandler {

	@Override
	public void uncaughtException(Thread arg0, Throwable arg1) {
		ErrorHandler.showErrorDialog(arg1);
	}
}
