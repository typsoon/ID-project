package org.example.idproject.view.browsingScreens;

import org.example.idproject.common.BasicPlayerData;
import org.example.idproject.view.ScreenManager;
import org.example.idproject.viewmodel.DatabaseService;

public class BrowsePlayersControllerAbstract extends AbstractBrowsingScreenController<BasicPlayerData> {
    public BrowsePlayersControllerAbstract(DatabaseService databaseService, ScreenManager screenManager) {
        super(databaseService, screenManager);
    }

    @Override
    protected void initialize() {
        super.initialize();

        addDataColumn("ID", "ID");
        addDataColumn("Nickname", "currentNickname");

        displayAll();

        searchField.setPromptText("Browse Players by nickname");
    }

    @Override
    protected void handleSearch() {
        dataArray.clear();
        dataArray.addAll(
           databaseService.browsePlayers(searchField.getText())
        );
    }

    @Override
    protected void displayAll() {
        dataArray.clear();
        dataArray.addAll(databaseService.getAllPlayers());
    }

    @Override
    protected void handleClickOnDataTable(int id) {
        screenManager.showPlayerInfo(id);
    }
}