package org.example.idproject.view.browsingScreens;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.example.idproject.common.BasicDuelData;
import org.example.idproject.view.ScreenManager;
import org.example.idproject.viewmodel.DatabaseService;


public class BrowseDuelsController extends AbstractBrowsingScreenController<BasicDuelData> {
    public BrowseDuelsController(DatabaseService databaseService, ScreenManager screenManager) {
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
        addDataColumn("First player ID", "firstPlayerID");
        addDataColumn("Second player ID", "secondPlayerID");
        addDataColumn("Date from", "dateFrom");
        addDataColumn("Date to", "dateTo");
        addDataColumn("Outcome", "outcome");
    }

    @Override
    protected void handleSearch() {
        dataArray.clear();
        dataArray.addAll(databaseService.browseDuels(tookPart.getText(), laterThan.getValue(), earlierThan.getValue()));
    }

    @Override
    protected void displayAll() {
        dataArray.clear();
        dataArray.addAll(databaseService.getAllDuels());
    }

    @Override
    protected void handleClickOnDataTable(int id) {

    }
}
