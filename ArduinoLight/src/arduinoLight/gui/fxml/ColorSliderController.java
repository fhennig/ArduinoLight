package arduinoLight.gui.fxml;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;


public class ColorSliderController {

    @FXML
    private Slider AlphaSlider;

    @FXML
    private Label AlphaValue;

    @FXML
    private Slider BlueSlider;

    @FXML
    private Label BlueValue;

    @FXML
    private Slider GreenSlider;

    @FXML
    private Label GreenValue;

    @FXML
    private Slider RedSlider;

    @FXML
    private Label RedValue;


    @FXML
    void initialize() {
        assert AlphaSlider != null : "fx:id=\"AlphaSlider\" was not injected: check your FXML file 'ColorSlider.fxml'.";
        assert AlphaValue != null : "fx:id=\"AlphaValue\" was not injected: check your FXML file 'ColorSlider.fxml'.";
        assert BlueSlider != null : "fx:id=\"BlueSlider\" was not injected: check your FXML file 'ColorSlider.fxml'.";
        assert BlueValue != null : "fx:id=\"BlueValue\" was not injected: check your FXML file 'ColorSlider.fxml'.";
        assert GreenSlider != null : "fx:id=\"GreenSlider\" was not injected: check your FXML file 'ColorSlider.fxml'.";
        assert GreenValue != null : "fx:id=\"GreenValue\" was not injected: check your FXML file 'ColorSlider.fxml'.";
        assert RedSlider != null : "fx:id=\"RedSlider\" was not injected: check your FXML file 'ColorSlider.fxml'.";
        assert RedValue != null : "fx:id=\"RedValue\" was not injected: check your FXML file 'ColorSlider.fxml'.";


    }

}

