package org.example.idproject.view.browsingScreens;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.idproject.viewmodel.ViewModel;

public abstract class BrowsingScreenController<T> {
    public TextField searchField;

    public TableView<T> dataTable = new TableView<>();

    protected final ViewModel viewModel;

    ObservableList<T> dataArray = FXCollections.observableArrayList();

    @FXML
    protected abstract void handleSearch();

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

    BrowsingScreenController(ViewModel viewModel) {
        this.viewModel = viewModel;

        dataTable.setItems(dataArray);
    }
}
