package org.example.idproject.view.browsingScreens;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import org.example.idproject.common.HasID;
import org.example.idproject.view.ScreenManager;
import org.example.idproject.viewmodel.DatabaseService;

import java.io.IOException;

public abstract class AbstractBrowsingScreenController<T extends HasID> {
    protected final ScreenManager screenManager;
    protected final DatabaseService databaseService;

    @FXML protected TextField searchField;

    @FXML protected AnchorPane tablePane;

    protected TableView<T> dataTable;

    @FXML protected Button searchButton;
    ObservableList<T> dataArray = FXCollections.observableArrayList();

    @FXML protected Button showAllButton;

//    protected CustomDataTable<?> customDataTable;

    protected abstract void handleSearch();

    protected abstract void displayAll();

    protected abstract void handleClickOnDataTable(int id);

    @FXML
    protected void initialize() throws IOException {
        assert dataTable != null : "this can't be null";

        tablePane.getChildren().clear();
        tablePane.getChildren().add(dataTable);

        dataTable.setItems(dataArray);
        searchButton.setOnAction(event -> handleSearch());

        dataTable.setRowFactory(tv -> {
            TableRow<T> tableRow = new TableRow<>();

            tableRow.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2 && !tableRow.isEmpty()) {
                    T selectedData = tableRow.getItem();
                    handleClickOnDataTable(selectedData.getID());
                }
            });

            return tableRow;
        });

        dataTable.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER && dataTable.getSelectionModel().getSelectedItem() != null) {
                T selectedData = dataTable.getSelectionModel().getSelectedItem();
                handleClickOnDataTable(selectedData.getID());
            }
        });

        showAllButton.setOnAction(event -> displayAll());
    }

    AbstractBrowsingScreenController(DatabaseService databaseService, ScreenManager screenManager) {
        this.screenManager = screenManager;
        this.databaseService = databaseService;
    }
}
