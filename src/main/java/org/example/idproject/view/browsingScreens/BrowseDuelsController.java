package org.example.idproject.view.browsingScreens;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.util.Callback;
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
        var firstPlayerColumn = addDataColumn("First player ID", "firstPlayerID");
        var secondPlayerColumn = addDataColumn("Second player ID", "secondPlayerID");
        addDataColumn("Date from", "dateFrom");
        addDataColumn("Date to", "dateTo");
        addDataColumn("Outcome", "outcome");

        firstPlayerColumn.setCellFactory(new PlayerIDColumnCellFactory(screenManager));
        secondPlayerColumn.setCellFactory(new PlayerIDColumnCellFactory(screenManager));
    }

    @Override
    protected void handleSearch() {
        dataArray.clear();
        //TODO commented
        //dataArray.addAll(databaseService.browseDuels(tookPart.getText(), laterThan.getValue(), earlierThan.getValue()));
    }

    @Override
    protected void displayAll() {
        dataArray.clear();
       //TODO commented
        // dataArray.addAll(databaseService.getAllDuels());
    }

    @Override
    protected void handleClickOnDataTable(int id) {

    }
}

class PlayerIDColumnCellFactory implements Callback<TableColumn<BasicDuelData, String>, TableCell<BasicDuelData, String>> {
    private final ScreenManager screenManager;

    PlayerIDColumnCellFactory(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }

    @Override
    public TableCell<BasicDuelData, String> call(TableColumn<BasicDuelData, String> basicDuelDataStringTableColumn) {
        TableCell<BasicDuelData, String> cell = new TableCell<BasicDuelData, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item);
                }
            }
        };

        cell.setOnMouseClicked(event -> {
            if (!cell.isEmpty()) {
                String lastName = cell.getItem();
                System.out.println("Last Name clicked: " + lastName);
                // Add your handling logic here
            }
        });

        return cell;
    }
}