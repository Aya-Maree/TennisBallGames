package se2203b.lab6.tennisballgames;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class AlertController implements Initializable {

    
    
    @FXML public Label error;
   
    public void setAlertText(String text) { // this is used to set the error text inside a label, used in other classes
        // set text from another class
        error.setText(text);
    } 

    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }    
    
}
