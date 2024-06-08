package org.example.idproject.view.browsingScreens;

import org.example.idproject.common.BasicClanData;
import org.example.idproject.view.ScreenManager;
import org.example.idproject.view.dataTables.BasicClanDataTable;
import org.example.idproject.viewmodel.DatabaseService;

import java.io.IOException;
import java.sql.SQLException;

public class BrowseClansController extends AbstractBrowsingScreenController<BasicClanData> {
    public BrowseClansController(DatabaseService databaseService, ScreenManager screenManager) throws IOException {
        super(databaseService, screenManager);

        dataTable = new BasicClanDataTable().getTableView();
    }

    @Override
    protected void initialize() throws IOException {
        super.initialize();

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
