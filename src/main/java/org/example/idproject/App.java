package org.example.idproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.idproject.view.ScreenManager;
import org.example.idproject.viewmodel.SimpleViewModel;

import java.io.IOException;

public class App extends Application {

    @Override
    public void init() throws Exception {
    }

    @Override
    public void start(Stage stage) throws IOException {
        ScreenManager screenManager = new ScreenManager(new SimpleViewModel(), stage);

        FXMLLoader managerLoader = new FXMLLoader(getClass().getResource("screen-manager.fxml"));
        managerLoader.setController(screenManager);

        stage.setScene(new Scene(managerLoader.load()));
        stage.show();

//        screenManager.showMainScene();
    }

    public static void main(String[] args) {
        launch();
    }
}