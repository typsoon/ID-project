package org.example.idproject.view.dataTables;

import org.example.idproject.common.ClanNameData;
import org.example.idproject.view.FXMLAddresses;

import java.io.IOException;

public class ClanNameDataTable extends CustomDataTable<ClanNameData> {
    public ClanNameDataTable() throws IOException {
        super(FXMLAddresses.CLAN_NAME_DATA_TABLE);
    }

    @Override
    protected void initialize() {
        addDataColumn("Name", "name");
    }
}
