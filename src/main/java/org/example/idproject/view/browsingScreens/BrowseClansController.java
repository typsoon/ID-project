package org.example.idproject.view.browsingScreens;

import org.example.idproject.common.BasicClanData;
import org.example.idproject.view.ScreenManager;
import org.example.idproject.viewmodel.DatabaseService;

import java.sql.SQLException;

public class BrowseClansController extends AbstractBrowsingScreenController<BasicClanData> {
    public BrowseClansController(DatabaseService databaseService, ScreenManager screenManager) {
        super(databaseService, screenManager);
    }

    @Override
    protected void initialize() {
        super.initialize();

        addDataColumn("ID", "ID");
        addDataColumn("Nickname", "currentName");

        displayAll();

        searchField.setPromptText("Browse Clans by name");
    }

    @Override
    protected void handleSearch() {
        dataArray.clear();

        try {
            dataArray.addAll(
                    databaseService.browseClans(searchField.getText())
            );
        }
        catch (SQLException e) {
            screenManager.displayAlert(e);
        }
    }

    @Override
    protected void displayAll() {
        dataArray.clear();
        try {
            dataArray.addAll(databaseService.getAllClans());
        }
        catch (SQLException e) {
            screenManager.displayAlert(e);
        }
    }

    @Override
    protected void handleClickOnDataTable(int id) {
        screenManager.showClanInfo(id);
    }
}
