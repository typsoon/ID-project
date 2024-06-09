package org.example.idproject.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.idproject.App;
import org.example.idproject.view.browsingScreens.*;
import org.example.idproject.view.infoPanes.AbstractInfoController;
import org.example.idproject.view.infoPanes.ClanInfoController;
import org.example.idproject.view.infoPanes.PlayerInfoController;
import org.example.idproject.view.infoPanes.TournamentInfoController;
import org.example.idproject.view.insertDataScreens.InsertClansController;
import org.example.idproject.view.insertDataScreens.InsertPlayersController;
import org.example.idproject.view.insertDataScreens.InsertTournamentController;
import org.example.idproject.viewmodel.DatabaseService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class ScreenManager {
    private final Stage primaryStage;
    private final DatabaseService databaseService;

    private final Collection<MyItemWrapper> myItemWrappers = new ArrayList<>();

    private final AbstractInfoController playerInfoController;
    private final AbstractInfoController clanInfoController;
    private final AbstractInfoController tournamentInfoController;

    private final VBox playerInfoVBox;
    private final VBox clanInfoVBox;
    private VBox tournamentInfoVBox;

    @FXML AnchorPane leftAnchorPane;
    @FXML AnchorPane rightAnchorPane;

    @FXML Button browseClansButton;
    @FXML Button browsePlayersButton;
    @FXML Button browseDuelsButton;
    @FXML Button browseChallengesButton;
    @FXML Button browseTournamentsButton;

    @FXML Button addPlayersButton;
    @FXML Button addClansButton;
    @FXML Button insertTournamentsButton;

    public ScreenManager(DatabaseService databaseService, Stage primaryStage) throws IOException {
        this.databaseService = databaseService;

        this.primaryStage = primaryStage;

        playerInfoController = new PlayerInfoController(databaseService, this);
        playerInfoVBox = loadVBox(FXMLAddresses.PLAYER_INFO, playerInfoController);

        clanInfoController = new ClanInfoController(databaseService, this);
        clanInfoVBox = loadVBox(FXMLAddresses.CLAN_INFO, clanInfoController);

        tournamentInfoController = new TournamentInfoController(databaseService, this);
        tournamentInfoVBox = loadVBox(FXMLAddresses.TOURNAMENT_INFO, tournamentInfoController);
    }

    private static VBox loadVBox(String resourceName, Object controller) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(resourceName));
        fxmlLoader.setController(controller);

        return fxmlLoader.load();
    }


    @SuppressWarnings("unused")
    @FXML
    private void initialize() throws IOException {
        myItemWrappers.add(new MyItemWrapper(FXMLAddresses.BROWSING_HBOX, browsePlayersButton, new BrowsePlayersController(databaseService, this),
                "Browse Players", leftAnchorPane));

        myItemWrappers.add(new MyItemWrapper(FXMLAddresses.BROWSING_HBOX, browseClansButton, new BrowseClansController(databaseService, this),
                "Browse Clans", leftAnchorPane));

        myItemWrappers.add(new MyItemWrapper(FXMLAddresses.BROWSING_WITH_DATE, browseDuelsButton, new BrowseDuelsController(databaseService, this),
                "Browse Duels", leftAnchorPane));

        myItemWrappers.add(new MyItemWrapper(FXMLAddresses.BROWSING_WITH_DATE, browseChallengesButton, new BrowseChallengesController(databaseService, this),
                "Browse Challenges", leftAnchorPane));

        myItemWrappers.add(new MyItemWrapper(FXMLAddresses.BROWSING_HBOX, browseTournamentsButton, new BrowseTournamentsController(databaseService, this),
                "Browse Tournaments", leftAnchorPane));

        myItemWrappers.add(new MyItemWrapper(FXMLAddresses.PLAYER_INSERT_VBOX, addPlayersButton, new InsertPlayersController(databaseService, this),
                "Add Players", leftAnchorPane));

        myItemWrappers.add(new MyItemWrapper(FXMLAddresses.CLAN_INSERT_VBOX, addClansButton, new InsertClansController(databaseService, this),
                "Add Clans", leftAnchorPane));

        myItemWrappers.add(new MyItemWrapper(FXMLAddresses.TOURNAMENT_INSERT_DATA_TABLE, insertTournamentsButton, new InsertTournamentController(databaseService, this),
                "Add Tournaments", leftAnchorPane));
    }

    public void displayAlert(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        // Set the title of the alert
        alert.setTitle("Unsuccessful Operation");

        // Set the header text (null if no header text)
        alert.setHeaderText(null);

        // Set the content text
        alert.setContentText(e.getMessage());

        // Show the alert and wait for the user to respond
        alert.showAndWait();
    }

    public void showPlayerInfo(int playerId) {
        playerInfoController.update(playerId);

        rightAnchorPane.getChildren().clear();
        rightAnchorPane.getChildren().add(playerInfoVBox);
    }

    public void showClanInfo(int clanId) {
        clanInfoController.update(clanId);
        rightAnchorPane.getChildren().clear();
        rightAnchorPane.getChildren().add(clanInfoVBox);
    }

    public void showTournamentInfo(int tournamentID) {
        tournamentInfoController.update(tournamentID);
        rightAnchorPane.getChildren().clear();
        rightAnchorPane.getChildren().add(tournamentInfoVBox);
    }

    private class MyItemWrapper {
        private final ItemData itemData;

        void initialize() {
            if (itemData.button != null)
                itemData.button.setOnAction(event -> handleClick());
            else throw new RuntimeException("itemData.button should not be null");
        }

        private void handleClick() {
            if (itemData.title != null)
                primaryStage.setTitle(itemData.title);

            itemData.anchorPane.getChildren().clear();
            itemData.anchorPane.getChildren().add(itemData.vBox);
//            AnchorPane.setBottomAnchor(itemData.vBox, 0.0);
//            AnchorPane.setLeftAnchor(itemData.vBox, 0.0);
//            AnchorPane.setRightAnchor(itemData.vBox, 0.0);
//            AnchorPane.setTopAnchor(itemData.vBox, 0.0);
        }

        private record ItemData(VBox vBox, Button button, String title, AnchorPane anchorPane) {}

        MyItemWrapper(String vboxAddress, Button button, Object controller, String title, AnchorPane anchorPane) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(vboxAddress));
            fxmlLoader.setController(controller);

            VBox vBox = fxmlLoader.load();
            itemData = new ItemData(vBox, button, title, anchorPane);

            initialize();
        }
    }
}