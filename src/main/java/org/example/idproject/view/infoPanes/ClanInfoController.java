package org.example.idproject.view.infoPanes;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.idproject.App;
import org.example.idproject.common.FullClanData;
import org.example.idproject.view.ScreenManager;
import org.example.idproject.viewmodel.DatabaseService;

import java.util.Objects;

public class ClanInfoController extends AbstractInfoController {

    public ClanInfoController(DatabaseService databaseService, ScreenManager screenManager) {
        super(databaseService, screenManager);
    }

    @SuppressWarnings("unused")
    @FXML
    private ImageView clanLogo;

    @Override
    protected void initialize() {

    }


    @Override
    public void update(int id) {
        try {
            FullClanData fullClanData = databaseService.getFullClanData(id);
            clanLogo.setImage(new Image(Objects.requireNonNull(App.class.getResourceAsStream(fullClanData.imageAddress()))));
        }
        catch (Exception e) {screenManager.displayAlert(e);}
    }
}
