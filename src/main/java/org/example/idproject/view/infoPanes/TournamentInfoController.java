package org.example.idproject.view.infoPanes;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.example.idproject.common.TournamentMatch;
import org.example.idproject.view.ScreenManager;
import org.example.idproject.view.dataTables.BasicDuelsDataTable;
import org.example.idproject.viewmodel.DatabaseService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class TournamentInfoController extends AbstractInfoController {
    public TournamentInfoController(DatabaseService databaseService, ScreenManager screenManager) {
        super(databaseService, screenManager);
    }

    @FXML private AnchorPane tournamentInfoPane;

    private VBox hboxesVbox;

    Collection<TournamentMatch> fullTournamentData;

    @Override
    protected void initialize() throws IOException {
        hboxesVbox = (VBox) tournamentInfoPane.getChildren().getFirst();
    }

    private void updateHBoxes() {
        int actLevel = hboxesVbox.getChildren().size()-1;
        HBox currentHBox = (HBox) hboxesVbox.getChildren().get(actLevel);
        Set<Integer> evaluated = new HashSet<>();
        Set<TournamentMatch> toEvaluate = new HashSet<>();

        for (Node node : hboxesVbox.getChildren()) {
            if (node instanceof HBox hbox) {
                hbox.getChildren().clear();
            }
            else throw new RuntimeException("node in tournamentInfoPane should be HBox");
        }

        Consumer<TournamentMatch> evaluateMatch = tournamentMatch -> {
            VBox vbox = new VBox();
            currentHBox.getChildren().add(vbox);
            vbox.getChildren().add(new Label("DuelID"));

            Text textNode = new Text(String.valueOf(tournamentMatch.duelID()));
            vbox.getChildren().add(textNode);

            try {
                textNode.setOnMouseClicked(
                        getEventHandler(() -> databaseService.getSingleDuelData(Integer.parseInt(textNode.getText())),
                                new BasicDuelsDataTable(screenManager)));
            }
            catch (IOException ioException) {
                throw new RuntimeException(ioException);
            }
        };

        Predicate<TournamentMatch> canBeEvaluated = tournamentMatch -> (tournamentMatch.leftChild() == 0 || evaluated.contains(tournamentMatch.leftChild()))
                && (tournamentMatch.rightChild() == 0 || evaluated.contains(tournamentMatch.rightChild()));

        while (evaluated.size() < fullTournamentData.size()) {
            toEvaluate.addAll(fullTournamentData.stream().filter(canBeEvaluated).toList());

            toEvaluate.forEach(evaluateMatch);

            for (var temp : toEvaluate) {
                evaluated.add(temp.ID());
            }
            toEvaluate.clear();

            actLevel--;
        }
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
