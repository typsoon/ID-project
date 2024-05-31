package org.example.idproject.view.browsingScreens;

import org.example.idproject.common.BasicClanData;
import org.example.idproject.common.BasicPlayerData;
import org.example.idproject.viewmodel.ViewModel;

public class BrowseClansController extends BrowsingScreenController<BasicClanData> {
    public BrowseClansController(ViewModel viewModel, String promptText) {
        super(viewModel);
    }

    @Override
    protected void initialize() {
        addDataColumn("ID", "ID");
        addDataColumn("Nickname", "currentName");

        dataArray.addAll(
                new BasicClanData(1, "typsoonClan"),
                new BasicClanData(2, "RIPerClan"),
                new BasicClanData(3, "mkdusiaClan")
        );
        dataTable.setItems(dataArray);

        searchField.setPromptText("Browse Clans by name");
    }

    @Override
    protected void handleSearch() {
        dataArray.addAll(
                new BasicClanData(5, "Test")
        );
    }
}
