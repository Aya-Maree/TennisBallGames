package se2203b.lab6.tennisballgames;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddMatchController implements Initializable {
    @FXML
    Button cancelBtn;
    @FXML
    Button saveBtn;
    @FXML
    ComboBox homeComboBox;
    @FXML
    ComboBox visitorComboBox;

    //creating a data ObservableList to populate the ComboBoxes
    final ObservableList<String> data = FXCollections.observableArrayList();

    // referencing models inside the controller
    private MatchesAdapter matchesAdapter;
    private TeamsAdapter teamsAdapter;
    public void setModel(MatchesAdapter matchesAdapter, TeamsAdapter teamsAdapter){
        this.matchesAdapter = matchesAdapter;
        this.teamsAdapter = teamsAdapter;
        buildComboBoxData();
    }
    @FXML
    public void cancel(){
        Stage stage = (Stage)cancelBtn.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void save() {
        //get the names selected from the ComboBoxes
        String homeTeamName = (String) homeComboBox.getValue();
        String visitorTeamName = (String) visitorComboBox.getValue();

        //set an if statement in case save is clicked while either of the combo boxes are null
        if (homeTeamName == null || visitorTeamName == null) {
            displayAlert("You must select both a home team and a visitor team!");
            return;
        }
        try{ //int num, String home, String visitor
            matchesAdapter.insertMatch(matchesAdapter.getMax()+1,homeTeamName,visitorTeamName);
        }catch(SQLException ex){
            displayAlert("ERROR: "+ ex.getMessage());
        }
        Stage stage = (Stage)cancelBtn.getScene().getWindow();
        stage.close();


    }
    private void displayAlert(String msg) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Alert.fxml"));
            Parent ERROR = loader.load();
            AlertController controller = (AlertController) loader.getController();

            Scene scene = new Scene(ERROR);
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.getIcons().add(new Image("file:src/main/resources/se2203b/lab5/tennisballgames/WesternLogo.png"));
            controller.setAlertText(msg);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException ex1) {

        }
    }
    public void buildComboBoxData(){
        try{
            data.addAll(teamsAdapter.getTeamsNames());
        } catch (SQLException ex){
            displayAlert("ERROR: " + ex.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        homeComboBox.setItems(data);
        visitorComboBox.setItems(data);
    }
}
