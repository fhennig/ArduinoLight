package arduinoLight.gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ErrorDialog {

	@FXML
	private TextArea _errorTextField;

	@FXML
	private TextArea _messageTextField;

	@FXML
	private Button _okButton;

	Throwable _throwable;

	public ErrorDialog(Throwable throwable) throws IOException {
		_throwable = throwable;
		FXMLLoader loader = new FXMLLoader(getClass().getResource(
				"ErrorDialog.fxml"));
		loader.setController(this);
		Parent root;
		root = loader.load();
		Stage stage = new Stage();
		stage.setTitle("An Error Ocurred!");
		Scene scene = new Scene(root, 420, 260);
		stage.setScene(scene);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				System.exit(0);
			}
		});
		_okButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				System.exit(0);
			}

		});
		stage.show();
	}

	@FXML
	void initialize() {
		assert _errorTextField != null : "fx:id=\"_errorTextField\" was not injected: check your FXML file 'ErrorDialog.fxml'.";
		assert _messageTextField != null : "fx:id=\"_messageTextField\" was not injected: check your FXML file 'ErrorDialog.fxml'.";
		assert _okButton != null : "fx:id=\"_okButton\" was not injected: check your FXML file 'ErrorDialog.fxml'.";

		_errorTextField.setText(getStackTraceAsString(_throwable));
		_messageTextField.setText("Something went wrong! Sorry!");
	}

	private String getStackTraceAsString(Throwable throwable) {
		StringBuilder builder = new StringBuilder();
		builder.append(throwable.getClass());
		builder.append("\n");
		for (StackTraceElement element : throwable.getStackTrace()) {
			builder.append(element);
			builder.append("\n");
		}
		return builder.toString();
	}

}
