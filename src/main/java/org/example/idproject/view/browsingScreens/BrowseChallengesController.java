package org.example.idproject.view.browsingScreens;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.example.idproject.common.BasicChallengeData;
import org.example.idproject.view.ScreenManager;
import org.example.idproject.view.dataTables.BasicChallengeDataTable;
import org.example.idproject.viewmodel.DatabaseService;

import java.io.IOException;
import java.sql.SQLException;

public class BrowseChallengesController extends AbstractBrowsingScreenController<BasicChallengeData> {
    public BrowseChallengesController(DatabaseService databaseService, ScreenManager screenManager) throws IOException {
        super(databaseService, screenManager);

        dataTable = new BasicChallengeDataTable().getTableView();
    }

    private final TextField tookPart = searchField;

    @SuppressWarnings("unused")
    @FXML
    private DatePicker laterThan;

    @SuppressWarnings("unused")
    @FXML
    private DatePicker earlierThan;

    @Override
    protected void initialize() throws IOException {
        super.initialize();

    }

    @Override
    protected void handleSearch() {
        dataArray.clear();
        //TODO commented
        try {
            dataArray.addAll(databaseService.browseChallenges(tookPart.getText(), laterThan.getValue(), earlierThan.getValue()));
        }
        catch (SQLException e) {
            screenManager.displayAlert(e);
        }

    }

    @Override
    protected void displayAll() {
        dataArray.clear();

        try {
//            System.out.println(databaseService.getAllChallenges());
            dataArray.addAll(databaseService.getAllChallenges());
        }
        catch (SQLException e) {
            screenManager.displayAlert(e);
        }
    }

    @Override
    protected void handleClickOnDataTable(int id) {

    }
}
