package org.example.idproject.view.browsingScreens;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.idproject.view.ScreenManager;
import org.example.idproject.viewmodel.ViewModel;

public abstract class BrowsingScreenController<T> {
    public TextField searchField;
    protected final ScreenManager screenManager;

    public TableView<T> dataTable = new TableView<>();
    public Button searchButton;
    ObservableList<T> dataArray = FXCollections.observableArrayList();

    protected final ViewModel viewModel;

    protected abstract void handleSearch();

    @FXML
    protected void initialize() {
        dataTable.setItems(dataArray);
        searchButton.setOnAction(event -> handleSearch());
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

    BrowsingScreenController(ViewModel viewModel, ScreenManager screenManager) {
        this.screenManager = screenManager;
        this.viewModel = viewModel;
    }
}
