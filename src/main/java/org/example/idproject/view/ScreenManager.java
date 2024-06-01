package org.example.idproject.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.idproject.App;
import org.example.idproject.view.browsingScreens.BrowseClansController;
import org.example.idproject.view.browsingScreens.BrowsePlayersController;
import org.example.idproject.viewmodel.ViewModel;

import java.io.IOException;

public class ScreenManager {
    private final Stage primaryStage;
    private final ViewModel viewModel;

    public final Scene browsePlayers;
    public final Scene mainScene;
    public final Scene browseClans;

    public ScreenManager(ViewModel viewModel, Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.viewModel = viewModel;

        browsePlayers = loadScene("browsing-stage.fxml", new BrowsePlayersController(viewModel, this));
        browseClans = loadScene("browsing-stage.fxml", new BrowseClansController(viewModel, this));
        mainScene = loadScene("main-scene.fxml", new MainSceneController(this));
    }

    private static Scene loadScene(String resourceName, Object controller) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(resourceName));
        fxmlLoader.setController(controller);

        return new Scene(fxmlLoader.load());
    }

    public void showBrowsePlayersScreen() {
        primaryStage.setTitle("Browse Players");
        primaryStage.setScene(browsePlayers);
        primaryStage.show();
    }

    public void showBrowseClansScreen() {
        primaryStage.setTitle("Browse Clans");
        primaryStage.setScene(browseClans);
        primaryStage.show();
    }

    public void showMainScene() {
        primaryStage.setTitle("Welcome to IDProject");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }
}
