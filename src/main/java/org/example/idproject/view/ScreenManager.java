package org.example.idproject.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.idproject.App;
import org.example.idproject.view.browsingScreens.BrowseClansControllerAbstract;
import org.example.idproject.view.browsingScreens.BrowsePlayersControllerAbstract;
import org.example.idproject.view.infoPanes.AbstractInfoController;
import org.example.idproject.view.infoPanes.ClanInfoController;
import org.example.idproject.view.infoPanes.PlayerInfoController;
import org.example.idproject.viewmodel.ViewModel;

import java.io.IOException;

public class ScreenManager {
    private final Stage primaryStage;

//    public final Scene browsePlayers;
    private final AbstractInfoController playerInfoController;
    private final AbstractInfoController clanInfoController;

    private final Scene mainScene;
    private final Scene browseClans;

    private final VBox browsePlayersVBox;
    private final VBox browseClansVBox;
    private final VBox playerInfoVBox;
    private final VBox clanInfoVBox;

    @FXML
    AnchorPane leftAnchorPane;

    @FXML
    AnchorPane rightAnchorPane;

    @FXML
    Button browseClansButton;

    @FXML
    Button browsePlayersButton;


    public ScreenManager(ViewModel viewModel, Stage primaryStage) throws IOException {
//        FXMLLoader managerLoader = new FXMLLoader(getClass().getResource("screen-manager.fxml"));
//        managerLoader.setController(this);

//        primaryStage.setScene(new Scene(managerLoader.load()));
//        primaryStage.show();

        this.primaryStage = primaryStage;

//        browsePlayers = loadScene("browsing-stage.fxml", new BrowsePlayersControllerAbstract(viewModel, this));
        browseClans = loadScene("browsing-stage.fxml", new BrowseClansControllerAbstract(viewModel, this));
        mainScene = loadScene("main-scene.fxml", new MainSceneController(this));

        browsePlayersVBox = loadVBox("browsing-hbox.fxml", new BrowsePlayersControllerAbstract(viewModel, this));
        browseClansVBox = loadVBox("browsing-hbox.fxml", new BrowseClansControllerAbstract(viewModel, this));

        playerInfoController = new PlayerInfoController(viewModel, this);
        playerInfoVBox = loadVBox("player-info.fxml", playerInfoController);

        clanInfoController = new ClanInfoController(viewModel, this);
        clanInfoVBox = loadVBox("clan-info.fxml", clanInfoController);
    }

    @SuppressWarnings("unused")
    @FXML
    private void initialize() {
        setControllers();
    }

    private void setControllers() {
        browsePlayersButton.setOnAction(event -> showBrowsePlayersScreen());
        browseClansButton.setOnAction(event -> showBrowseClansScreen());
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

    void showMainScene() {
        primaryStage.setTitle("Welcome to IDProject");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }
}
