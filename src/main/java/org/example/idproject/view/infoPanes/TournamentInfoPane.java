package org.example.idproject.view.infoPanes;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.example.idproject.common.TournamentMatch;
import org.example.idproject.view.ScreenManager;
import org.example.idproject.viewmodel.DatabaseService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

public class TournamentInfoPane extends AbstractInfoController {
    TournamentInfoPane(DatabaseService databaseService, ScreenManager screenManager) {
        super(databaseService, screenManager);
    }

    @FXML private AnchorPane tournamentInfoPane;

    Collection<TournamentMatch> fullTournamentData;

    @Override
    protected void initialize() throws IOException {

    }

    private void updateHBoxes() {
        int evaluated = 0;
        int actLevel = tournamentInfoPane.getChildren().size()-1;
        HBox hBox = (HBox) tournamentInfoPane.getChildren().get(actLevel);

        fullTournamentData.stream().filter(tournamentMatch -> {
            return tournamentMatch.rightChild() == null && tournamentMatch.leftChild() == null;
        }).forEach(tournamentMatch -> {

        });
    }

    @Override
    public void update(int id) {
        try {
            fullTournamentData = databaseService.getTournamentMatches(id);

            updateHBoxes();
        } catch (SQLException e) {
            screenManager.displayAlert(e);
        }
    }
}
