package org.example.idproject.view.browsingScreens;

import org.example.idproject.common.BasicClanData;
import org.example.idproject.viewmodel.ViewModel;

public class BrowseClansController extends BrowsingScreenController<BasicClanData> {
    public BrowseClansController(ViewModel viewModel) {
        super(viewModel);
    }

    @Override
    protected void handleSearch() {
        addDataColumn("ID", "ID");
        addDataColumn("Nickname", "currentName");

        dataArray.addAll(
                new BasicClanData(1, "typsoonClan"),
                new BasicClanData(2, "RIPerClan"),
                new BasicClanData(3, "mkdusiaClan")
        );

        dataTable.setItems(dataArray);
    }
}
