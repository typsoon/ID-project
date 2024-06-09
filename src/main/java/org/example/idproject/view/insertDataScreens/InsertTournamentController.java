package org.example.idproject.view.insertDataScreens;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.idproject.common.MyIntegerWrapper;
import org.example.idproject.view.ScreenManager;
import org.example.idproject.viewmodel.DatabaseService;

public class InsertTournamentController extends AbstractInsertDataController {
    public InsertTournamentController(DatabaseService databaseService, ScreenManager screenManager) {
        super(databaseService, screenManager);
    }

    @FXML private Button addContestantButton;

    @FXML private TextField tournamentName;

    @FXML private TextField contestantID;

    @FXML private TableView<MyIntegerWrapper> contestantIDTable;

    private final ObservableList<MyIntegerWrapper> contestants = FXCollections.observableArrayList();

    @Override
    void initialize() {
        addSimpleDataButton.setOnAction(event -> insertSimpleData());

        contestantIDTable.setItems(contestants);

        addContestantButton.setOnAction(event -> {
            try {
                MyIntegerWrapper added = new MyIntegerWrapper(contestantID.getText());

                if (!contestants.contains(added)) {
                    contestants.add(added);
                }
            }
            catch (Exception e) {
                screenManager.displayAlert(e);
            }
        });

        TableColumn<MyIntegerWrapper, String> nameColumn = (TableColumn<MyIntegerWrapper, String>) contestantIDTable.getColumns().getFirst();
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
    }

    @Override
    void insertSimpleData() {
        try {
            databaseService.createTournament(tournamentName.getText(),
                    contestants.stream().map(MyIntegerWrapper::getValue).toList());
        }
        catch (Exception e) {
            screenManager.displayAlert(e);
        }
        contestants.clear();
    }
}

