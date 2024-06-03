package org.example.idproject.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.idproject.App;
import org.example.idproject.view.browsingScreens.BrowseClansController;
import org.example.idproject.view.browsingScreens.BrowsePlayersController;
import org.example.idproject.viewmodel.ViewModel;

import java.io.IOException;

public class ScreenManager {
    private final Stage primaryStage;

//    public final Scene browsePlayers;
    public final Scene mainScene;
    public final Scene browseClans;

    public final VBox browsePlayersVBox;
    public final VBox browseClansVBox;
//    public final VBox playerInfoVBox;

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

//        browsePlayers = loadScene("browsing-stage.fxml", new BrowsePlayersController(viewModel, this));
        browseClans = loadScene("browsing-stage.fxml", new BrowseClansController(viewModel, this));
        mainScene = loadScene("main-scene.fxml", new MainSceneController(this));

        browsePlayersVBox = loadVBox("browsing-hbox.fxml", new BrowsePlayersController(viewModel, this));
        browseClansVBox = loadVBox("browsing-hbox.fxml", new BrowseClansController(viewModel, this));
        //        playerInfoVBox = loadVBox("player-info.fxml", new MainSceneController(this));
    }

    @FXML
    void initialize() {
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

    public void showBrowsePlayersScreen() {
        primaryStage.setTitle("Browse Players");

        leftAnchorPane.getChildren().clear();
        leftAnchorPane.getChildren().add(browsePlayersVBox);
//        leftVBox = browsePlayersVBox;

        //        primaryStage.setScene(browsePlayers);
        primaryStage.show();
    }

    public void showBrowseClansScreen() {
        primaryStage.setTitle("Browse Clans");

        leftAnchorPane.getChildren().clear();
        leftAnchorPane.getChildren().add(browseClansVBox);

//        primaryStage.setScene(browseClans);
        primaryStage.show();
    }

    public void showMainScene() {
        primaryStage.setTitle("Welcome to IDProject");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }
}
