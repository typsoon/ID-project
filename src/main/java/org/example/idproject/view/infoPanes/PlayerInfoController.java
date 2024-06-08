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

    @FXML private Button sendClanMessage;
    @FXML private TextField message;

    @FXML private Button sendFriendMessage;
    @FXML private TextField receiverIDField;
    @FXML private TextField friendMessage;

    @FXML private Text currentClanName;

    @FXML private Text currentNickname;

    @FXML private Button seeClan;

    @FXML private Button addDuel;

    @FXML private Button pastNicknames;

    @FXML private Button showFriends;

    @FXML private Button showAllFriends;

    @FXML private TextField addedID;

    @FXML private DatePicker dateFrom;
    @FXML private TextField dateFromTimestamp;

    @FXML private DatePicker dateTo;
    @FXML private TextField dateToTimestamp;

    private FullPlayerData fullPlayerData;

    @Override
    protected void initialize() throws IOException {
        seeClan.setOnAction(event -> {
            if (fullPlayerData != null && fullPlayerData.currentClanId() != null) {
                screenManager.showClanInfo(fullPlayerData.currentClanId());
            }
        });

        showFriends.setOnAction(getEventHandler(() -> databaseService.getCurrentFriends(fullPlayerData.getID()), new FriendDataTable()));

        showAllFriends.setOnAction(getEventHandler(()->databaseService.getAllFriends(fullPlayerData.getID()), new FriendDataTable()));

        seeClan.setOnAction(actionEvent -> {
            try {
                if (fullPlayerData.currentClanId() == null)
                    return;

                screenManager.showClanInfo(fullPlayerData.currentClanId());
            }
            catch (Exception e) {
                screenManager.displayAlert(e);
            }
        });

        addDuel.setOnAction(actionEvent -> {
            Date resultDateFrom = MyDateParser.parseFrom(dateFrom.getValue(), dateFromTimestamp.getText());

            Date resultDateTo = MyDateParser.parseFrom(dateTo.getValue(), dateToTimestamp.getText());

            try {
                int opponentID = Integer.parseInt(addedID.getText());
                databaseService.insertDuel(fullPlayerData.getID(), opponentID, resultDateFrom, resultDateTo);
            } catch (Exception e) {
                screenManager.displayAlert(e);
            }
        });

        sendClanMessage.setOnAction(actionEvent -> {
            try {
                databaseService.sendClanMessage(fullPlayerData.getID(), message.getText());
            } catch (SQLException e) {
                screenManager.displayAlert(e);
            }
        });

        sendFriendMessage.setOnAction(actionEvent -> {
            try {
                int receiverID = Integer.parseInt(receiverIDField.getText());

                databaseService.sendFriendMessage(fullPlayerData.getID(), receiverID, friendMessage.getText());
            }
            catch (Exception e) {
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
