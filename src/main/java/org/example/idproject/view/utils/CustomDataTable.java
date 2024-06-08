package org.example.idproject.view.utils;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.idproject.App;
import org.example.idproject.common.HasID;

import java.io.IOException;

public abstract class CustomDataTable<T extends HasID> {
    private int addedCount = 0;

    @FXML
    protected TableView<T> dataTable = new TableView<>();

    @FXML
    protected abstract void initialize();

    protected TableColumn<T, String> addDataColumn(String columnName, String dataVariableName) {
        TableColumn<T, ?> dataColumn;

        if (addedCount < dataTable.getColumns().size()) {
            dataColumn = dataTable.getColumns().get(addedCount);
//            dataColumn = dataTable.getColumns().get(addedCount);
        }
        else {
            dataColumn = new TableColumn<>();
            dataTable.getColumns().add(dataColumn);
        }

        dataColumn.setText(columnName);
        dataColumn.setCellValueFactory(new PropertyValueFactory<>(dataVariableName));

        addedCount++;

        return (TableColumn<T, String>) dataColumn;
    }

    public TableView<T> getTableView() {
        return dataTable;
    }

    public CustomDataTable() throws IOException {}

    protected CustomDataTable(String resourcePath) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource(resourcePath));
        loader.setController(this);

        dataTable = loader.load();
    }
}
