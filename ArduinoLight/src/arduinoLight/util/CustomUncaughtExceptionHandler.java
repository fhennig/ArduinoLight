package arduinoLight.util;

import java.lang.Thread.UncaughtExceptionHandler;

public class CustomUncaughtExceptionHandler implements UncaughtExceptionHandler {

	@Override
	public void uncaughtException(Thread arg0, Throwable arg1) {
		System.out.println("caught");
		ErrorHandler.showErrorDialog(arg1);
	}
}
