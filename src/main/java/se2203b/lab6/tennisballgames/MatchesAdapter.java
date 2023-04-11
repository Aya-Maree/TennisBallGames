package se2203b.lab6.tennisballgames;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MatchesAdapter {

    Connection connection;

    public MatchesAdapter(Connection conn, Boolean reset) throws SQLException {
        connection = conn;
        if (reset) {
            Statement stmt = connection.createStatement();
            try {
                // Remove tables if database tables have been created.
            // This will throw an exception if the tables do not exist
            stmt.execute("DROP TABLE Matches");
                // then do finally
            } catch (SQLException ex) {
                // No need to report an error.
                // The table simply did not exist.
                // do finally to create it
            } finally {
                // Create the table of Matches
                stmt.execute("CREATE TABLE Matches ("
                        + "MatchNumber INT NOT NULL PRIMARY KEY, "
                        + "HomeTeam CHAR(15) NOT NULL REFERENCES Teams (TeamName), "
                        + "VisitorTeam CHAR(15) NOT NULL REFERENCES Teams (TeamName), "
                        + "HomeTeamScore INT, "
                        + "VisitorTeamScore INT "
                        + ")");
                populateSamples();
            }
        }
    }
    
    private void populateSamples() throws SQLException{
            // Create a listing of the matches to be played
            this.insertMatch(1, "Astros", "Brewers");
            this.insertMatch(2, "Brewers", "Cubs");
            this.insertMatch(3, "Cubs", "Astros");
    }
        
    
    public int getMax() throws SQLException {
        int num = 0;
        Statement stmt = connection.createStatement();
        try{
            ResultSet rs = stmt.executeQuery("SELECT MAX(MatchNumber) FROM Matches");
            while(rs.next()) {
                num = rs.getInt(1);
            }
        }catch (SQLException ex) {
            //no need to print error.
        }
        return num;
    }
    
    public void insertMatch(int num, String home, String visitor) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("INSERT INTO Matches (MatchNumber, HomeTeam, VisitorTeam, HomeTeamScore, VisitorTeamScore) "
                + "VALUES (" + num + " , '" + home + "' , '" + visitor + "', 0, 0)");
    }
    
    // Get all Matches
    public ObservableList<Matches> getMatchesList() throws SQLException {
        ObservableList<Matches> matchesList = FXCollections.observableArrayList();
        Statement stmt = connection.createStatement();
        try {
            ResultSet rs = stmt.executeQuery("SELECT * From Matches");
            while (rs.next()) {
                int matchNum = rs.getInt("MatchNumber");
                String homeTeam = rs.getString("HomeTeam");
                int homeScore = rs.getInt("HomeTeamScore");
                String visitorTeam = rs.getString("VisitorTeam");
                int visitorScore = rs.getInt("VisitorTeamScore");
                // Add the column value to the matches list
                matchesList.add(new Matches(matchNum, homeTeam, visitorTeam, homeScore, visitorScore));
            }
        }catch(SQLException ex){

            }
        return matchesList;
    }

    // Get a String list of matches to populate the ComboBox used in Task #4.
    public ObservableList<String> getMatchesNamesList() throws SQLException {
        ObservableList<String> list = FXCollections.observableArrayList();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * From Matches");
        while (rs.next()) {
            int matchNum = rs.getInt("MatchNumber");
            String homeTeam = rs.getString("HomeTeam");
            String visitorTeam = rs.getString("VisitorTeam");
            String ss = String.format("%d-%-15s-%s", matchNum, homeTeam, visitorTeam);
            list.add(ss);
        }
        return list;
    }
    
    
    public void setTeamsScore(int matchNumber, int hScore, int vScore) throws SQLException
   {
       Statement stmt = connection.createStatement();
       try {
           ResultSet rs = stmt.executeQuery("SELECT * From Matches");
           while (rs.next()) {
               String updateQuery = "UPDATE Matches SET HomeTeamScore = " + hScore + ", VisitorTeamScore = " + vScore + " WHERE MatchNumber = " + matchNumber;
               stmt.executeUpdate(updateQuery);
           }
       }catch(SQLException ex){

       }
        // Add your code here for Task #4
   }  
}
