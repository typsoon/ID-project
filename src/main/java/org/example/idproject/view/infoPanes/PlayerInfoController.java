package org.example.idproject.view.infoPanes;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.example.idproject.common.FullPlayerData;
import org.example.idproject.utils.MyDateParser;
import org.example.idproject.view.ScreenManager;
import org.example.idproject.view.dataTables.MessageDataTable;
import org.example.idproject.view.dataTables.FriendDataTable;
import org.example.idproject.view.dataTables.FriendInviteDataTable;
import org.example.idproject.view.dataTables.NicknameNameDataTable;
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

    @FXML private Button showInvites;

    @FXML private Button showAllFriends;

    @FXML private Button endDuelButton;
    @FXML private TextField endDuelDuelID;
    @FXML private TextField endDuelWonBoolean;

    @FXML private Button applyToClanButton;
    @FXML private TextField applyToClanField;

    @FXML private Button acceptMemberButton;
    @FXML private ChoiceBox<Integer> acceptMemberChoiceBox;

    @FXML private Button sendInviteButton;
    @FXML private TextField sendInviteField;

    @FXML private Button acceptFriendButton;
    @FXML private ChoiceBox<Integer> acceptFriendChoiceBox;

    @FXML private Button seeFriendMessage;
    @FXML private ChoiceBox<Integer> friendChatFriendID;

    @FXML private TextField clanName;

    @FXML private TextField addedID;

    @FXML private DatePicker dateFrom;
    @FXML private TextField dateFromTimestamp;

    @FXML private DatePicker dateTo;
    @FXML private TextField dateToTimestamp;

    @FXML private Button changeNicknameButton;
    @FXML private TextField newNicknameField;

    @Override
    protected void initialize() throws IOException {
        seeClan.setOnAction(event -> {
            if (fullPlayerData != null && fullPlayerData.currentClanId() != null) {
                screenManager.showClanInfo(fullPlayerData.currentClanId());
            }
        });

        showFriends.setOnAction(getEventHandler(() -> databaseService.getCurrentFriends(fullPlayerData.getID()), new FriendDataTable()));

        showInvites.setOnAction(getEventHandler(()->databaseService.getActiveFriendInvites(fullPlayerData.getID()),
                new FriendInviteDataTable()));

        showAllFriends.setOnAction(getEventHandler(()->databaseService.getAllFriends(fullPlayerData.getID()), new FriendDataTable()));

        seeClan.setOnAction(actionEvent -> {
            try {
                if (fullPlayerData.currentClanId() == null)
                    return;

                System.out.println(fullPlayerData.currentClanId());
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

        sendFriendMessage.setOnAction(getEventHandler(()->databaseService.getFriendChatMessages(fullPlayerData.getID(), friendChatFriendID.getValue()),
                new MessageDataTable()));

        endDuelButton.setOnAction(actionEvent -> {
            try {
                databaseService.endDuel(Integer.parseInt(endDuelDuelID.getText()),
                        Boolean.parseBoolean(endDuelWonBoolean.getText()));
            }
            catch (Exception e) {
                screenManager.displayAlert(e);
            }
        });

        acceptMemberButton.setOnAction(actionEvent -> {
            try {
                databaseService.acceptMember(fullPlayerData.getID(), acceptMemberChoiceBox.getValue());
            }
            catch (Exception e) {
                screenManager.displayAlert(e);
            }
        });

        pastNicknames.setOnAction(getEventHandler(()->databaseService.getNicknames(fullPlayerData.getID()),
                new NicknameNameDataTable()));

        changeNicknameButton.setOnAction(actionEvent -> {
            try {
                databaseService.changeNickname(fullPlayerData.getID(), newNicknameField.getText());
            }
            catch (Exception e) {
                screenManager.displayAlert(e);
            }
        });


        sendInviteButton.setOnAction(actionEvent -> {
            try {
                databaseService.sendFriendInvite(fullPlayerData.getID(), Integer.parseInt(sendInviteField.getText()), MyDateParser.parseFrom(null, ""));
            }
            catch (Exception e) {
                screenManager.displayAlert(e);
            }
        });

        applyToClanButton.setOnAction(actionEvent -> {
            try {
                databaseService.applyToClan(fullPlayerData.getID(), Integer.parseInt(applyToClanField.getText()));
            } catch (Exception e) {
                screenManager.displayAlert(e);
            }
        });
    }

    private FullPlayerData fullPlayerData;

    @Override
    public void update(int id) {
        try {
            fullPlayerData = databaseService.getFullPlayerData(id);

            currentClanName.setText(fullPlayerData.currentClanName() == null ? "" : fullPlayerData.currentClanName());
            currentNickname.setText(fullPlayerData.basicPlayerData().currentNickname());

            acceptMemberChoiceBox.getItems().clear();
            if (fullPlayerData.currentClanId() != null) {
                for (var item : databaseService.getClanApplications(fullPlayerData.currentClanId())) {
                    acceptMemberChoiceBox.getItems().add(item.playerID());
                }
            }

            acceptFriendChoiceBox.getItems().clear();
            if (fullPlayerData.currentClanId() != null) {
                for (var item : databaseService.getActiveFriendInvites(fullPlayerData.getID())) {
                    acceptFriendChoiceBox.getItems().add(item.senderId() == fullPlayerData.getID() ? item.receiverID() : item.senderId());
                }
            }

            friendChatFriendID.getItems().clear();
            for (var item : databaseService.getAllFriends(fullPlayerData.getID())) {
                friendChatFriendID.getItems().add(item.getID());
            }
        }
        catch (Exception e) {
//            throw new RuntimeException(e);
            screenManager.displayAlert(e);
        }
    }
}
