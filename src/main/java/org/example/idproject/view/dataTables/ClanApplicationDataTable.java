package org.example.idproject.view.dataTables;

import org.example.idproject.common.ClanApplication;
import org.example.idproject.view.FXMLAddresses;

import java.io.IOException;

public class ClanApplicationDataTable extends CustomDataTable<ClanApplication> {
    public ClanApplicationDataTable() throws IOException {
        super(FXMLAddresses.FRIEND_INVITE_DATA_TABLE);
    }

    @Override
    protected void initialize() {
        addDataColumn("Player ID", "playerID");
        addDataColumn("Date From", "dateFrom");
        addDataColumn("Date To", "dateTo");
    }
}
