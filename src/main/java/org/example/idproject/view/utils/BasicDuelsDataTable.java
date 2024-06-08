package org.example.idproject.view.utils;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import org.example.idproject.common.BasicDuelData;
import org.example.idproject.view.FXMLAddresses;
import org.example.idproject.view.ScreenManager;

import java.io.IOException;

public class BasicDuelsDataTable extends CustomDataTable<BasicDuelData> {
    private final ScreenManager screenManager;

    public BasicDuelsDataTable(ScreenManager screenManager) throws IOException {
        super(FXMLAddresses.DUEL_DATA_TABLE);

        this.screenManager = screenManager;
    }

    @Override
    protected void initialize() {
        addDataColumn("ID", "ID");
        var firstPlayerColumn = addDataColumn("First player ID", "firstPlayerID");
        var secondPlayerColumn = addDataColumn("Second player ID", "secondPlayerID");
        addDataColumn("Date from", "dateFrom");
        addDataColumn("Date to", "dateTo");
        addDataColumn("Outcome", "outcome");

//        firstPlayerColumn.setCellFactory(new PlayerIDColumnCellFactory(screenManager));
//        secondPlayerColumn.setCellFactory(new PlayerIDColumnCellFactory(screenManager));
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
//                String lastName = cell.getItem();
                System.out.println("Last Name clicked: " + cell.getItem());
                // Add your handling logic here
            }
        });

        return cell;
    }
}