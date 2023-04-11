package se2203b.lab6.tennisballgames;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class TeamsAdapter {

    Connection connection;

    public TeamsAdapter(Connection conn, Boolean reset) throws SQLException {
        connection = conn;
        if (reset) {
            Statement stmt = connection.createStatement();
            try {
                // Remove tables if database tables have been created.
                // This will throw an exception if the tables do not exist
                // We drop Matches first because it references the table Teams
                stmt.execute("DROP TABLE Matches");
                stmt.execute("DROP TABLE Teams");
                // then do finally
            } catch (SQLException ex) {
                // No need to report an error.
                // The table simply did not exist.
                // do finally to create it
            } finally {
                // Create the table of teams
                stmt.execute("CREATE TABLE Teams ("
                        + "TeamName CHAR(15) NOT NULL PRIMARY KEY, "
                        + "Wins INT, Losses INT, Ties INT)");
                populateSampls();
            }
        }
    }

    private void populateSampls() throws SQLException {
        // Add some teams
        this.insertTeam("Astros");
        this.insertTeam("Marlins");
        this.insertTeam("Brewers");
        this.insertTeam("Cubs");
    }

    public void insertTeam(String name) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("INSERT INTO Teams (TeamName, Wins, Losses, Ties) VALUES ('" + name + "', 0, 0, 0)");
    }

    // Get all teams Data
    public ObservableList<Teams> getTeamsList() throws SQLException {
        ObservableList<Teams> list = FXCollections.observableArrayList();
        ResultSet rs;

        // Create a Statement object
        Statement stmt = connection.createStatement();

        // Create a string with a SELECT statement
        String sqlStatement = "SELECT * FROM Teams";

        // Execute the statement and return the result
        rs = stmt.executeQuery(sqlStatement);

        while (rs.next()) {

            list.add(new Teams(rs.getString("TeamName"),
                    rs.getInt("Wins"),
                    rs.getInt("Losses"),
                    rs.getInt("Ties")));
        }
        return list;
    }

    // Get all teams names to populate the ComboBoxes used in Task #3.
    public ObservableList<String> getTeamsNames() throws SQLException {
        ObservableList<String> list = FXCollections.observableArrayList();
        ResultSet rs;

        // Create a Statement object
        Statement stmt = connection.createStatement();

        // Create a string with a SELECT statement
        String sqlStatement = "SELECT * FROM Teams";

        // Execute the statement and return the result
        rs = stmt.executeQuery(sqlStatement);
        while (rs.next()) {
            list.add(rs.getString("TeamName"));
        }
        return list;
    }


    public void setStatus(String hTeam, String vTeam, int hScore, int vScore) throws SQLException {
        // Create a Statement object
        Statement stmt = connection.createStatement();
            ResultSet rs;
        int win;
        int loss;
        int tie;
        if(hScore>vScore) {
            rs = stmt.executeQuery("SELECT Wins FROM Teams WHERE TeamName = " + "'" + hTeam + "'");
            if (rs.next()) {
                win = rs.getInt("Wins");
                stmt.executeUpdate("Update Teams SET Wins = " + (win + 1) + "Where TeamName = " + "'" + hTeam + "'");
                rs = stmt.executeQuery("SELECT Losses FROM Teams WHERE TeamName = " + "'" + vTeam + "'");
                if (rs.next()) {
                    loss = rs.getInt("Losses");
                    stmt.executeUpdate("Update Teams SET Losses = " + (loss + 1) + "Where TeamName = " + "'" + vTeam + "'");
                }
            }
        } else if (vScore>hScore) {
            rs = stmt.executeQuery("SELECT Wins FROM Teams WHERE TeamName = " + "'" + vTeam + "'");
            if (rs.next()) {
                win = rs.getInt("Wins");
                stmt.executeUpdate("Update Teams SET Wins = " + (win + 1) + "Where TeamName = " + "'" + vTeam + "'");
                rs = stmt.executeQuery("SELECT Losses FROM Teams WHERE TeamName = " + "'" + hTeam + "'");
                if (rs.next()) {
                    loss = rs.getInt("Losses");
                    stmt.executeUpdate("Update Teams SET Losses = " + (loss + 1) + "Where TeamName = " + "'" + hTeam + "'");
                }
        }

        }else if(vScore==hScore){
            rs = stmt.executeQuery("SELECT Ties FROM Teams WHERE TeamName = " + "'" + vTeam + "'");
            if (rs.next()) {
                tie = rs.getInt("Ties");
                stmt.executeUpdate("Update Teams SET Ties = " + (tie + 1) + "Where TeamName = " + "'" + vTeam + "'");
            }
            rs = stmt.executeQuery("SELECT Ties FROM Teams WHERE TeamName = " + "'" + hTeam + "'");
                if (rs.next()) {
                    tie = rs.getInt("Ties");
                    stmt.executeUpdate("Update Teams SET Ties = " + (tie + 1) + "Where TeamName = " + "'" + hTeam + "'");
                }
            }
        }
    }

