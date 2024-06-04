package org.example.idproject.view.browsingScreens;

import org.example.idproject.common.BasicClanData;
import org.example.idproject.view.ScreenManager;
import org.example.idproject.viewmodel.DataProvider;

public class BrowseClansControllerAbstract extends AbstractBrowsingScreenController<BasicClanData> {
    public BrowseClansControllerAbstract(DataProvider dataProvider, ScreenManager screenManager) {
        super(dataProvider, screenManager);
    }

    @Override
    protected void initialize() {
        super.initialize();

        addDataColumn("ID", "ID");
        addDataColumn("Nickname", "currentName");

        displayAll();

        searchField.setPromptText("Browse Clans by name");
    }

    @Override
    protected void handleSearch() {
        dataArray.clear();
        dataArray.addAll(
                dataProvider.browseClans(searchField.getText())
        );
    }

    @Override
    protected void displayAll() {
        dataArray.clear();
        dataArray.addAll(dataProvider.getAllClans());
    }

    @Override
    protected void handleClickOnDataTable(int id) {
        screenManager.showClanInfo(id);
    }
}
