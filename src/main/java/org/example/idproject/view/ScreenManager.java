package org.example.idproject.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.idproject.App;
import org.example.idproject.view.browsingScreens.BrowseChallengesController;
import org.example.idproject.view.browsingScreens.BrowseClansController;
import org.example.idproject.view.browsingScreens.BrowseDuelsController;
import org.example.idproject.view.browsingScreens.BrowsePlayersController;
import org.example.idproject.view.infoPanes.AbstractInfoController;
import org.example.idproject.view.infoPanes.ClanInfoController;
import org.example.idproject.view.infoPanes.PlayerInfoController;
import org.example.idproject.view.insertDataScreens.InsertClansController;
import org.example.idproject.view.insertDataScreens.InsertPlayersController;
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

    private final VBox playerInfoVBox;
    private final VBox clanInfoVBox;

    @FXML AnchorPane leftAnchorPane;
    @FXML AnchorPane rightAnchorPane;

    @FXML Button browseClansButton;
    @FXML Button browsePlayersButton;
    @FXML Button browseDuelsButton;
    @FXML Button browseChallengesButton;

    @FXML Button addPlayersButton;
    @FXML Button addClansButton;

    public ScreenManager(DatabaseService databaseService, Stage primaryStage) throws IOException {
        this.databaseService = databaseService;

        this.primaryStage = primaryStage;

        playerInfoController = new PlayerInfoController(databaseService, this);
        playerInfoVBox = loadVBox(FXMLAddresses.PLAYER_INFO, playerInfoController);

        clanInfoController = new ClanInfoController(databaseService, this);
        clanInfoVBox = loadVBox(FXMLAddresses.CLAN_INFO, clanInfoController);
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

        myItemWrappers.add(new MyItemWrapper(FXMLAddresses.PLAYER_INSERT_VBOX, addPlayersButton, new InsertPlayersController(databaseService, this),
                "Add Players", leftAnchorPane));

        myItemWrappers.add(new MyItemWrapper(FXMLAddresses.CLAN_INSERT_VBOX, addClansButton, new InsertClansController(databaseService, this),
                "Add Clans", leftAnchorPane));
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

    private class MyItemWrapper {
        private final ItemData itemData;

        void initialize() {
            if (itemData.button != null)
                itemData.button.setOnAction(event -> handleClick());
        }

        private void handleClick() {
            if (itemData.title != null)
                primaryStage.setTitle(itemData.title);

            itemData.pane.getChildren().clear();
            itemData.pane.getChildren().add(itemData.vBox);
        }

        private record ItemData(VBox vBox, Button button, String title, Pane pane) {}

        MyItemWrapper(String vboxAddress, Button button, Object controller, String title, Pane pane) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(vboxAddress));
            fxmlLoader.setController(controller);

            VBox vBox = fxmlLoader.load();
            itemData = new ItemData(vBox, button, title, pane);

            initialize();
        }
    }
}