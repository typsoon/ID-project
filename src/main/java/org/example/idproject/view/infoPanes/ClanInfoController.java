package org.example.idproject.view.infoPanes;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.example.idproject.App;
import org.example.idproject.common.FullClanData;
import org.example.idproject.view.ScreenManager;
import org.example.idproject.view.dataTables.ClanMessageDataTable;
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

    private FullClanData fullClanData;

    @Override
    protected void initialize() {
        try {
            showClanChatButton.setOnAction(getEventHandler(()->databaseService.getClanMessages(fullClanData.getID()), new ClanMessageDataTable()));

            currentMembers.setOnAction(getEventHandler(()->databaseService.getCurrentMembers(fullClanData.getID()), new MemberDataTable()));

            currentAndPastMembers.setOnAction(getEventHandler(()->databaseService.getCurrentAndPastMembers(fullClanData.getID()), new MemberDataTable()));

            pastNames.setOnAction(getEventHandler(() -> databaseService.getClanNames(fullClanData.getID()), new ClanNameDataTable()));
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
