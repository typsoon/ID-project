package org.example.idproject.view.browsingScreens;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import org.example.idproject.common.BasicDuelData;
import org.example.idproject.view.ScreenManager;
import org.example.idproject.view.utils.BasicDuelsDataTable;
import org.example.idproject.viewmodel.DatabaseService;

import java.io.IOException;


public class BrowseDuelsController extends AbstractBrowsingScreenController<BasicDuelData> {
    public BrowseDuelsController(DatabaseService databaseService, ScreenManager screenManager) throws IOException {
        super(databaseService, screenManager);

        dataTable = new BasicDuelsDataTable(screenManager).getTableView();
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

//        firstPlayerColumn.setCellFactory(new PlayerIDColumnCellFactory(screenManager));
//        secondPlayerColumn.setCellFactory(new PlayerIDColumnCellFactory(screenManager));
    }

    @Override
    protected void handleSearch() {
        dataArray.clear();
        //TODO commented
        try {
            dataArray.addAll(databaseService.browseDuels(tookPart.getText(), laterThan.getValue(), earlierThan.getValue()));
        }
        catch (Exception e) {
            screenManager.displayAlert(e);
        }
    }

    @Override
    protected void displayAll() {
        dataArray.clear();
       //TODO commented
        try {
             dataArray.addAll(databaseService.getAllDuels());
        }
        catch (Exception e) {
            screenManager.displayAlert(e);
        }
    }

    @Override
    protected void handleClickOnDataTable(int id) {

    }
}