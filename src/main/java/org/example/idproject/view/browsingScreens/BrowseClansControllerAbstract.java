package org.example.idproject.view.browsingScreens;

import org.example.idproject.common.BasicClanData;
import org.example.idproject.view.ScreenManager;
import org.example.idproject.viewmodel.ViewModel;

public class BrowseClansControllerAbstract extends AbstractBrowsingScreenController<BasicClanData> {
    public BrowseClansControllerAbstract(ViewModel viewModel, ScreenManager screenManager) {
        super(viewModel, screenManager);
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
        dataArray.addAll(
                new BasicClanData(5, "Test")
        );
    }

    @Override
    protected void displayAll() {
        dataArray.clear();
        dataArray.addAll(viewModel.getAllClans());
    }

    @Override
    protected void handleClickOnDataTable(int id) {
        screenManager.showClanInfo(id);
    }
}
