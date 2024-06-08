package org.example.idproject.view.utils;

import org.example.idproject.common.BasicClanData;
import org.example.idproject.view.FXMLAddresses;

import java.io.IOException;

public class BasicClanDataTable extends CustomDataTable<BasicClanData> {
    public BasicClanDataTable() throws IOException {
        super(FXMLAddresses.BASIC_CLAN_DATA_TABLE);
    }

    @Override
    protected void initialize() {
        addDataColumn("ID", "ID");
        addDataColumn("Name", "currentName");
    }
}
