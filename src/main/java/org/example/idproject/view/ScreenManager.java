package org.example.idproject.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
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
import org.example.idproject.view.insertDataScreens.AbstractInsertDataController;
import org.example.idproject.view.insertDataScreens.InsertClansController;
import org.example.idproject.view.insertDataScreens.InsertPlayersController;
import org.example.idproject.viewmodel.DatabaseService;

import java.io.IOException;

public class ScreenManager {
    private final Stage primaryStage;

//    public final Scene browsePlayers;
    private final AbstractInfoController playerInfoController;
    private final AbstractInfoController clanInfoController;

    AbstractInsertDataController playerInsertDataController;
    AbstractInsertDataController clanInsertDataController;

//    private final Scene mainScene;
//    private final Scene browseClans;

    private final VBox browsePlayersVBox;
    private final VBox browseClansVBox;
    private final VBox browseDuelsVBox;
    private final VBox browseChallengesVBox;

    private final VBox playerInfoVBox;
    private final VBox clanInfoVBox;

    protected final VBox playerInsertVBox;
    protected final VBox clanInsertVBox;

    @FXML AnchorPane leftAnchorPane;
    @FXML AnchorPane rightAnchorPane;

    @FXML Button browseClansButton;
    @FXML Button browsePlayersButton;
    @FXML Button browseDuelsButton;
    @FXML Button browseChallengesButton;

    @FXML Button addPlayersButton;
    @FXML Button addClansButton;

    public ScreenManager(DatabaseService databaseService, Stage primaryStage) throws IOException {
//        FXMLLoader managerLoader = new FXMLLoader(getClass().getResource("screen-manager.fxml"));
//        managerLoader.setController(this);

//        primaryStage.setScene(new Scene(managerLoader.load()));
//        primaryStage.show();

        this.primaryStage = primaryStage;

//        browsePlayers = loadScene("browsing-stage.fxml", new BrowsePlayersController(databaseService, this));
//        browseClans = loadScene("browsing-stage.fxml", new BrowseClansController(databaseService, this));
//        mainScene = loadScene("main-scene.fxml", new MainSceneController(this));

        browsePlayersVBox = loadVBox("browsing-hbox.fxml", new BrowsePlayersController(databaseService, this));
        browseClansVBox = loadVBox("browsing-hbox.fxml", new BrowseClansController(databaseService, this));
        browseDuelsVBox = loadVBox("browsing-with-date.fxml", new BrowseDuelsController(databaseService, this));
        browseChallengesVBox = loadVBox("browsing-with-date.fxml", new BrowseChallengesController(databaseService, this));

        playerInfoController = new PlayerInfoController(databaseService, this);
        playerInfoVBox = loadVBox("player-info.fxml", playerInfoController);

        clanInfoController = new ClanInfoController(databaseService, this);
        clanInfoVBox = loadVBox("clan-info.fxml", clanInfoController);

        playerInsertDataController = new InsertPlayersController(databaseService, this);
        playerInsertVBox = loadVBox("player-insert-VBox.fxml", playerInsertDataController);

        clanInsertDataController = new InsertClansController(databaseService, this);
        clanInsertVBox = loadVBox("clan-insert-VBox.fxml", clanInsertDataController);
    }

    @SuppressWarnings("unused")
    @FXML
    private void initialize() {
        setControllers();
    }

    private void setControllers() {
        browsePlayersButton.setOnAction(event -> showBrowsePlayersScreen());
        browseClansButton.setOnAction(event -> showBrowseClansScreen());
        browseDuelsButton.setOnAction(event -> showBrowseDuelsScreen());
        browseChallengesButton.setOnAction(event -> showBrowseChallengesScreen());

        addPlayersButton.setOnAction(actionEvent -> showInsertPlayersScreen());
        addClansButton.setOnAction(actionEvent -> showInsertClansScreen());
    }

    private static Scene loadScene(String resourceName, Object controller) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(resourceName));
        fxmlLoader.setController(controller);

        return new Scene(fxmlLoader.load());
    }

    private static VBox loadVBox(String resourceName, Object controller) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(resourceName));
        fxmlLoader.setController(controller);

        return fxmlLoader.load();
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

    void showBrowsePlayersScreen() {
        primaryStage.setTitle("Browse Players");

        leftAnchorPane.getChildren().clear();
        leftAnchorPane.getChildren().add(browsePlayersVBox);
    }

    public void showPlayerInfo(int playerId) {
        playerInfoController.update(playerId);

        rightAnchorPane.getChildren().clear();
        rightAnchorPane.getChildren().add(playerInfoVBox);
    }

    void showBrowseClansScreen() {
        primaryStage.setTitle("Browse Clans");

        leftAnchorPane.getChildren().clear();
        leftAnchorPane.getChildren().add(browseClansVBox);
    }

    public void showClanInfo(int clanId) {
        clanInfoController.update(clanId);
        rightAnchorPane.getChildren().clear();
        rightAnchorPane.getChildren().add(clanInfoVBox);
    }

    public void showBrowseDuelsScreen() {
        primaryStage.setTitle("Browse Duels");
        leftAnchorPane.getChildren().clear();
        leftAnchorPane.getChildren().add(browseDuelsVBox);
    }

    public void showBrowseChallengesScreen() {
        primaryStage.setTitle("Browse Challenges");
        leftAnchorPane.getChildren().clear();
        leftAnchorPane.getChildren().add(browseChallengesVBox);
    }

    public void showInsertPlayersScreen() {
        primaryStage.setTitle("Insert Players");
        leftAnchorPane.getChildren().clear();
        leftAnchorPane.getChildren().add(playerInsertVBox);
    }

    public void showInsertClansScreen() {
        primaryStage.setTitle("Insert Clans");
        leftAnchorPane.getChildren().clear();
        leftAnchorPane.getChildren().add(clanInsertVBox);
    }
}
