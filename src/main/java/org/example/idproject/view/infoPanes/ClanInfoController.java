package org.example.idproject.view.infoPanes;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.example.idproject.App;
import org.example.idproject.common.FullClanData;
import org.example.idproject.view.ScreenManager;
import org.example.idproject.view.dataTables.MessageDataTable;
import org.example.idproject.view.dataTables.ClanNameDataTable;
import org.example.idproject.view.dataTables.MemberDataTable;
import org.example.idproject.viewmodel.DatabaseService;

import java.util.Objects;

public class ClanInfoController extends AbstractInfoController {

    public ClanInfoController(DatabaseService databaseService, ScreenManager screenManager) {
        super(databaseService, screenManager);
    }

    @SuppressWarnings("unused")
    @FXML
    private ImageView clanLogo;


    @FXML private Text currentClanName;

    @FXML private Button showClanChatButton;

    @FXML private Button currentMembers;

    @FXML private Button currentAndPastMembers;

    @FXML private Button clanWars;

    @FXML private Button activeApplications;

    @FXML private Button pastNames;

    @FXML private Button passLeaderButton;

    @FXML private Button removeMemberButton;

    @FXML private TextField newLeader;

    @FXML private TextField removedID;

    @FXML private TextField whoKicked;

    private FullClanData fullClanData;

    @Override
    protected void initialize() {
        try {
            showClanChatButton.setOnAction(getEventHandler(()->databaseService.getClanMessages(fullClanData.getID()), new MessageDataTable()));

            currentMembers.setOnAction(getEventHandler(()->databaseService.getCurrentMembers(fullClanData.getID()), new MemberDataTable()));

            currentAndPastMembers.setOnAction(getEventHandler(()->databaseService.getCurrentAndPastMembers(fullClanData.getID()), new MemberDataTable()));

            pastNames.setOnAction(getEventHandler(() -> databaseService.getClanNames(fullClanData.getID()), new ClanNameDataTable()));

            passLeaderButton.setOnAction(actionEvent -> {
                try {
                    databaseService.passLeader(fullClanData.getID(), Integer.parseInt(newLeader.getText()));
                }
            catch (Exception e) {
                    screenManager.displayAlert(e);
            }
            });

            removeMemberButton.setOnAction(actionEvent -> {
                try {
                    Integer whoKickedID;
                    try {
                        whoKickedID = Integer.getInteger(whoKicked.getText());
                    }
                    catch (Exception ignored) {
                        whoKickedID = null;
                    }

                    databaseService.removeMember(fullClanData.getID(), Integer.parseInt(removedID.getText()), whoKickedID);
//                    if (!databaseService.removeMember(fullClanData.getID(), Integer.parseInt(removedID.getText()), whoKickedID))
//                        throw new Exception("No delete happened");
                }
            catch (Exception e) {
                    screenManager.displayAlert(e);
            }
            });
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void update(int id) {
        try {
            fullClanData = databaseService.getFullClanData(id);
            clanLogo.setImage(new Image(Objects.requireNonNull(App.class.getResourceAsStream(fullClanData.imageAddress()))));

            currentClanName.setText(fullClanData.basicClanData().currentName());
        }
        catch (Exception e) {screenManager.displayAlert(e);}
    }
}
