package se2203b.lab6.tennisballgames;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AboutController  { // about controller helps

    @FXML
    Button okBtn;


    public void exit() { //this method is used to exit the current stage (the about stage)
        Stage stage = (Stage) okBtn.getScene().getWindow();
        stage.close();
        //okBtn.getScene().getWindow(); gets a reference
    }

}
