package org.example.idproject.view.browsingScreens;

import org.example.idproject.common.BasicPlayerData;
import org.example.idproject.view.ScreenManager;
import org.example.idproject.view.utils.BasicPlayerDataTable;
import org.example.idproject.viewmodel.DatabaseService;

import java.io.IOException;

public class BrowsePlayersController extends AbstractBrowsingScreenController<BasicPlayerData> {
    public BrowsePlayersController(DatabaseService databaseService, ScreenManager screenManager) throws IOException {
        super(databaseService, screenManager);

        dataTable = new BasicPlayerDataTable().getTableView();
    }

    @Override
    protected void initialize() throws IOException {
        displayAll();

        searchField.setPromptText("Browse Players by nickname");

        super.initialize();
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