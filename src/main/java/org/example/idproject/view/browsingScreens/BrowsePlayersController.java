package org.example.idproject.view.browsingScreens;

import org.example.idproject.common.BasicPlayerData;
import org.example.idproject.view.ScreenManager;
import org.example.idproject.viewmodel.DatabaseService;

public class BrowsePlayersController extends AbstractBrowsingScreenController<BasicPlayerData> {
    public BrowsePlayersController(DatabaseService databaseService, ScreenManager screenManager) {
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

        try {
            dataArray.addAll(
                    databaseService.browsePlayers(searchField.getText())
            );
        }
        catch (Exception e) {
            screenManager.displayAlert(e);
        }
    }

    @Override
    protected void displayAll() {
        dataArray.clear();

        try {
            dataArray.addAll(databaseService.getAllPlayers());
        }
        catch (Exception e) {screenManager.displayAlert(e);}
    }

    @Override
    protected void handleClickOnDataTable(int id) {
        screenManager.showPlayerInfo(id);
    }
}