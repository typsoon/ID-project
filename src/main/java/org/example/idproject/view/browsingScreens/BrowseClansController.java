package org.example.idproject.view.browsingScreens;

import org.example.idproject.common.BasicClanData;
import org.example.idproject.view.ScreenManager;
import org.example.idproject.viewmodel.DatabaseService;

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
        dataArray.addAll(
                databaseService.browseClans(searchField.getText())
        );
    }

    @Override
    protected void displayAll() {
        dataArray.clear();
        dataArray.addAll(databaseService.getAllClans());
    }

    @Override
    protected void handleClickOnDataTable(int id) {
        screenManager.showClanInfo(id);
    }
}
