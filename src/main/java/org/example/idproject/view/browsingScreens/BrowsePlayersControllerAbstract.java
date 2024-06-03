package org.example.idproject.view.browsingScreens;

import org.example.idproject.common.BasicPlayerData;
import org.example.idproject.view.ScreenManager;
import org.example.idproject.viewmodel.ViewModel;

public class BrowsePlayersControllerAbstract extends AbstractBrowsingScreenController<BasicPlayerData> {
    public BrowsePlayersControllerAbstract(ViewModel viewModel, ScreenManager screenManager) {
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
                new BasicPlayerData(3, "mkdusia"),
                new BasicPlayerData(4, "luftwaffel (i love waffles)")
        );

        searchField.setPromptText("Browse Players by nickname");
    }

    @Override
    protected void handleSearch() {
        dataArray.addAll(
            new BasicPlayerData(5, "Test")
        );
    }

    @Override
    protected void handleClickOnDataTable(int id) {
        screenManager.showPlayerInfo(id);
    }
}