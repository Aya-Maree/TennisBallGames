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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddScoreController implements Initializable {
    @FXML
    ComboBox matchComboBox;
    @FXML
    TextField homeScoreText;
    @FXML
    TextField visitorScoreText;
    @FXML
    Button cancelBtn;
    @FXML
    Button saveBtn;
    private TeamsAdapter teamsAdapter;
    private MatchesAdapter matchesAdapter;

    private Matches match;
    //creating a data ObservableList to populate the ComboBoxes
    final ObservableList<String> data = FXCollections.observableArrayList();

    public void setModel(MatchesAdapter matchesAdapter, TeamsAdapter teamsAdapter){
        this.matchesAdapter = matchesAdapter;
        this.teamsAdapter = teamsAdapter;
        buildComboBoxData();
    }
    private void displayAlert(String msg) {
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
    @FXML
    public void cancel() { // closes the pop-up
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void save() {
        try {
            // Parse the selected value from the matchComboBox
            String[] selectedValue = matchComboBox.getValue().toString().split("-");
            int matchNumber = Integer.parseInt(selectedValue[0]);
            String homeTeamName = selectedValue[1];
            System.out.println(homeTeamName+"--");
            String visitorTeamName = selectedValue[2];
            System.out.println(visitorTeamName+"--");
            int homeScore = Integer.parseInt(homeScoreText.getText());
            int visitorScore = Integer.parseInt(visitorScoreText.getText());

            // Update the score of the selected match
            matchesAdapter.setTeamsScore(matchNumber, homeScore, visitorScore);

            // Update the status of both teams
            teamsAdapter.setStatus(homeTeamName, visitorTeamName, homeScore, visitorScore);

        } catch (SQLException ex) {
            displayAlert("ERROR: " + ex.getMessage());
        }

        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }


    public void buildComboBoxData(){
        try{
            ObservableList<String> list = matchesAdapter.getMatchesNamesList();
            data.addAll(list);

        } catch (SQLException ex){
            displayAlert("ERROR: " + ex.getMessage());
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        matchComboBox.setItems(data);
    }
}
