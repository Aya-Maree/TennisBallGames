package se2203b.lab6.tennisballgames;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.sql.SQLException;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddTeamController implements Initializable {

    @FXML
    Button cancelBtn;

    @FXML
    Button saveBtn;
    
    @FXML
    TextField teamName;

    private TeamsAdapter teamsAdapter;

    public void setModel(TeamsAdapter team) {
        teamsAdapter = team;
    }

    @FXML
    public void cancel() { // closes the pop-up
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void save() { //this will save the new team name
        try {
            teamsAdapter.insertTeam(teamName.getText()); // gets the text from the Textfeild and adds it to teamsAdaptar object
        } catch (SQLException ex) {
            displayAlert("ERROR: " + ex.getMessage());
        }
            
        Stage stage = (Stage) cancelBtn.getScene().getWindow(); // close the popup when cancel button is clicked
        stage.close();
    }

     private void displayAlert(String msg) { // method to display the alert
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Alert.fxml"));
            Parent ERROR = loader.load();
            AlertController controller = (AlertController) loader.getController();

            Scene scene = new Scene(ERROR);
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.getIcons().add(new Image("file:src/main/resources/se2203b/lab6/tennisballgames/WesternLogo.png"));
            controller.setAlertText(msg);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException ex1) {

        }
    }
     
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
