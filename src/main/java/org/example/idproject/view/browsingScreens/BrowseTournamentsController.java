package org.example.idproject.view.browsingScreens;

import org.example.idproject.common.TournamentData;
import org.example.idproject.view.ScreenManager;
import org.example.idproject.view.dataTables.TournamentDataTable;
import org.example.idproject.viewmodel.DatabaseService;

import java.io.IOException;

public class BrowseTournamentsController extends AbstractBrowsingScreenController<TournamentData> {
    public BrowseTournamentsController(DatabaseService databaseService, ScreenManager screenManager) throws IOException {
        super(databaseService, screenManager);

        dataTable = new TournamentDataTable().getTableView();
    }

    @Override
    protected void initialize() throws IOException {
        displayAll();

        searchField.setPromptText("Browse Tournaments");

        super.initialize();
    }

    @Override
    protected void handleSearch() {
        dataArray.clear();

        try {
            dataArray.addAll(
                    databaseService.browseTournaments(searchField.getText())
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
            dataArray.addAll(databaseService.getAllTournaments());
        }
        catch (Exception e) {screenManager.displayAlert(e);}
    }

    @Override
    protected void handleClickOnDataTable(int id) {
        screenManager.showTournamentInfo(id);
    }
}
