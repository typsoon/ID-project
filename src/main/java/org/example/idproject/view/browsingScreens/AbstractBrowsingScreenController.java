package org.example.idproject.view.browsingScreens;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import org.example.idproject.common.HasID;
import org.example.idproject.view.ScreenManager;
import org.example.idproject.viewmodel.ViewModel;

public abstract class AbstractBrowsingScreenController<T extends HasID> {
    protected final ScreenManager screenManager;

    @FXML
    protected TextField searchField;

    @FXML
    protected TableView<T> dataTable = new TableView<>();

    @FXML
    protected Button searchButton;
    ObservableList<T> dataArray = FXCollections.observableArrayList();

    @FXML
    protected Button showAllButton;

    protected final ViewModel viewModel;

    protected abstract void handleSearch();

    protected abstract void displayAll();

    protected abstract void handleClickOnDataTable(int id);

    @FXML
    protected void initialize() {
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

    private int addedCount = 0;

    protected void addDataColumn(String columnName, String dataVariableName) {
        TableColumn<T, ?> dataColumn;

        if (addedCount < dataTable.getColumns().size()) {
            dataColumn = dataTable.getColumns().get(addedCount);
        }
        else {
            dataColumn = new TableColumn<>();
            dataTable.getColumns().add(dataColumn);
        }

        dataColumn.setText(columnName);
        dataColumn.setCellValueFactory(new PropertyValueFactory<>(dataVariableName));

        addedCount++;
    }

    AbstractBrowsingScreenController(ViewModel viewModel, ScreenManager screenManager) {
        this.screenManager = screenManager;
        this.viewModel = viewModel;
    }
}
