package org.example.idproject.view.dataTables;

import org.example.idproject.common.BasicPlayerData;
import org.example.idproject.view.FXMLAddresses;

import java.io.IOException;

public class BasicPlayerDataTable extends CustomDataTable<BasicPlayerData> {
    public BasicPlayerDataTable() throws IOException {
        super(FXMLAddresses.BASIC_PLAYER_DATA_TABLE);
    }

    @Override
    protected void initialize() {
        addDataColumn("ID", "ID");
        addDataColumn("Nickname", "currentNickname");
    }
}
