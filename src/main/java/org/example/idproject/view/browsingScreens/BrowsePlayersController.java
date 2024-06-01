package org.example.idproject.view.browsingScreens;

import org.example.idproject.common.BasicPlayerData;
import org.example.idproject.view.ScreenManager;
import org.example.idproject.viewmodel.ViewModel;

public class BrowsePlayersController extends BrowsingScreenController<BasicPlayerData> {
    public BrowsePlayersController(ViewModel viewModel, ScreenManager screenManager) {
        super(viewModel, screenManager);
    }

    @Override
    protected void initialize() {
        super.initialize();

        addDataColumn("ID", "ID");
        addDataColumn("Nickname", "currentNickname");

        dataArray.addAll(
                new BasicPlayerData(1, "typsoon"),
                new BasicPlayerData(2, "RIPer"),
                new BasicPlayerData(3, "mkdusia")
        );

        searchField.setPromptText("Browse Players by nickname");
    }

    @Override
    protected void handleSearch() {
        dataArray.addAll(
            new BasicPlayerData(5, "Test")
        );
    }
}