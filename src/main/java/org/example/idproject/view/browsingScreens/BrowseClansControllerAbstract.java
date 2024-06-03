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

        dataArray.addAll(
                new BasicClanData(1, "typsoonClan"),
                new BasicClanData(2, "RIPerClan"),
                new BasicClanData(3, "mkdusiaClan")
        );

        searchField.setPromptText("Browse Clans by name");
    }

    @Override
    protected void handleSearch() {
        dataArray.addAll(
                new BasicClanData(5, "Test")
        );
    }

    @Override
    protected void handleClickOnDataTable(int id) {

    }
}
