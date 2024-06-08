package org.example.idproject.view.infoPanes;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.example.idproject.common.FullPlayerData;
import org.example.idproject.utils.MyDateParser;
import org.example.idproject.view.ScreenManager;
import org.example.idproject.view.dataTables.FriendDataTable;
import org.example.idproject.viewmodel.DatabaseService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

@SuppressWarnings("unused")
public class PlayerInfoController extends AbstractInfoController {
    public PlayerInfoController(DatabaseService databaseService, ScreenManager screenManager) {
        super(databaseService, screenManager);
    }

    @FXML private Text currentClanName;

    @FXML private Text currentNickname;

    @FXML private Button seeClan;

    @FXML private Button addDuel;

//    @FXML private AnchorPane tablePane;

    @FXML private Button pastNicknames;

    @FXML private Button showFriends;

    @FXML private Button showAllFriends;

    @FXML private TextField addedID;

    @FXML private DatePicker dateFrom;
    @FXML private TextField dateFromTimestamp;

    @FXML private DatePicker dateTo;
    @FXML private TextField dateToTimestamp;

    private FullPlayerData fullPlayerData;

//    protected  <T extends HasID> EventHandler<ActionEvent> getEventHandler(CheckedSupplier<Collection<T>> supplier, CustomDataTable<T> dataTableCreator) {
//        return event -> {
////            if (fullPlayerData != null) {
//                tablePane.getChildren().clear();
//
//                try {
//                    TableView<T> table = dataTableCreator.getTableView();
//                    ObservableList<T> tableData = FXCollections.observableArrayList();
//
//                    tableData.addAll(supplier.get());
//                    table.setItems(tableData);
//                } catch (SQLException e) {
//                    screenManager.displayAlert(e);
//                }
////            }
//        };
//    };

    @Override
    protected void initialize() throws IOException {
        seeClan.setOnAction(event -> {
            if (fullPlayerData != null && fullPlayerData.currentClanId() != null) {
                screenManager.showClanInfo(fullPlayerData.currentClanId());
            }
        });

        showFriends.setOnAction(getEventHandler(() -> databaseService.getCurrentFriends(fullPlayerData.getID()), new FriendDataTable()));

        showAllFriends.setOnAction(getEventHandler(()->databaseService.getAllFriends(fullPlayerData.getID()), new FriendDataTable()));

        addDuel.setOnAction(actionEvent -> {
            Date resultDateFrom = MyDateParser.parseFrom(dateFrom.getValue(), dateFromTimestamp.getText());

            Date resultDateTo = MyDateParser.parseFrom(dateTo.getValue(), dateToTimestamp.getText());

            int opponentID = Integer.parseInt(addedID.getText());

            try {
                databaseService.insertDuel(fullPlayerData.getID(), opponentID, resultDateFrom, resultDateTo);
            } catch (SQLException e) {
                screenManager.displayAlert(e);
            }
        });
    }

    @Override
    public void update(int id) {
        try {
            fullPlayerData = databaseService.getFullPlayerData(id);

            currentClanName.setText(fullPlayerData.currentClanName() == null ? "" : fullPlayerData.currentClanName());
            currentNickname.setText(fullPlayerData.basicPlayerData().currentNickname());
        }
        catch (Exception e) {
            screenManager.displayAlert(e);
        }
    }
}
