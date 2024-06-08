package org.example.idproject.view.infoPanes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import org.example.idproject.common.HasID;
import org.example.idproject.view.ScreenManager;
import org.example.idproject.view.dataTables.CustomDataTable;
import org.example.idproject.viewmodel.DatabaseService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

public abstract class AbstractInfoController {
    protected final DatabaseService databaseService;
    protected final ScreenManager screenManager;

    @FXML
    protected AnchorPane tablePane;

    AbstractInfoController(DatabaseService databaseService, ScreenManager screenManager) {
        this.databaseService = databaseService;
        this.screenManager = screenManager;
    }

    @FunctionalInterface
    protected interface CheckedSupplier<T>{
        T get() throws SQLException;
    }

    protected  <T extends HasID> EventHandler<ActionEvent> getEventHandler(CheckedSupplier<Collection<T>> supplier, CustomDataTable<T> dataTableCreator) {
        return event -> {
//            if (fullPlayerData != null) {
            tablePane.getChildren().clear();

            try {
                TableView<T> table = dataTableCreator.getTableView();
                ObservableList<T> tableData = FXCollections.observableArrayList();

                table.setItems(tableData);
                tableData.addAll(supplier.get());

                tablePane.getChildren().add(table);
            } catch (SQLException e) {
                screenManager.displayAlert(e);
            }
//            }
        };
    }

    @FXML
    protected abstract void initialize() throws IOException;

    public abstract void update(int id);
}
