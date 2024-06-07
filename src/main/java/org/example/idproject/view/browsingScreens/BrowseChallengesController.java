package org.example.idproject.view.browsingScreens;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.example.idproject.common.BasicChallengeData;
import org.example.idproject.view.ScreenManager;
import org.example.idproject.viewmodel.DatabaseService;
import org.postgresql.util.PSQLException;

import java.sql.SQLException;

public class BrowseChallengesController extends AbstractBrowsingScreenController<BasicChallengeData> {
    public BrowseChallengesController(DatabaseService databaseService, ScreenManager screenManager) {
        super(databaseService, screenManager);
    }

    private final TextField tookPart = searchField;

    @SuppressWarnings("unused")
    @FXML
    private DatePicker laterThan;

    @SuppressWarnings("unused")
    @FXML
    private DatePicker earlierThan;

    @Override
    protected void initialize() {
        super.initialize();

        addDataColumn("ID", "ID");
        addDataColumn("Date from", "dateFrom");
        addDataColumn("Date to", "dateTo");
        addDataColumn("Objective", "objective");
    }

    @Override
    protected void handleSearch() {
        dataArray.clear();
        dataArray.addAll(databaseService.browseChallenges(tookPart.getText(), laterThan.getValue(), earlierThan.getValue()));
    }

    @Override
    protected void displayAll() {
        dataArray.clear();

        try {
            dataArray.addAll(databaseService.getAllChallenges());
        }
        catch (SQLException e) {
            screenManager.displayAllert(e);
        }
    }

    @Override
    protected void handleClickOnDataTable(int id) {

    }


}
