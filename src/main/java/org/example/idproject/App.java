package org.example.idproject;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.idproject.view.ScreenManager;
import org.example.idproject.viewmodel.SimpleViewModel;

import java.io.IOException;

public class App extends Application {
    private ScreenManager screenManager;


    @Override
    public void init() throws Exception {
    }

    @Override
    public void start(Stage stage) throws IOException {
        screenManager = new ScreenManager(new SimpleViewModel(), stage);

        screenManager.showMainScene();
    }

    public static void main(String[] args) {
        launch();
    }
}