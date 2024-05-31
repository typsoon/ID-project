package org.example.idproject.view.browsingScreens;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.example.idproject.common.BasicPlayerData;
import org.example.idproject.viewmodel.ViewModel;

public class BrowsePlayersController extends BrowsingScreenController<BasicPlayerData> {
    public BrowsePlayersController(ViewModel viewModel) {
        super(viewModel);
    }

    @Override
    protected void handleSearch() {
        addDataColumn("ID", "ID");
        addDataColumn("Nickname", "currentNickname");

        dataArray.addAll(
                new BasicPlayerData(1, "typsoon"),
                new BasicPlayerData(2, "RIPer"),
                new BasicPlayerData(3, "mkdusia")
        );

        dataTable.setItems(dataArray);
    }
}