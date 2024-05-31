package org.example.idproject.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import org.example.idproject.App;
import org.example.idproject.view.browsingScreens.BrowseClansController;
import org.example.idproject.view.browsingScreens.BrowsePlayersController;
import org.example.idproject.viewmodel.ViewModel;

import java.io.IOException;

public class ScenesHolder {
    public final Scene browsePlayers;
    public final Scene mainScene;
    public final Scene browseClans;
//    public static final Scene playerInfo;
    public ScenesHolder(ViewModel viewModel) throws IOException {
        browsePlayers = loadScene("browsing-stage.fxml", new BrowsePlayersController(viewModel));
        browseClans = loadScene("browsing-stage.fxml", new BrowseClansController(viewModel));

        mainScene = loadScene("main-scene.fxml", new MainSceneController());
    }

    private static Scene loadScene(String resourceName, Object controller) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(resourceName));
        fxmlLoader.setController(controller);

        return new Scene(fxmlLoader.load());
    }
}
