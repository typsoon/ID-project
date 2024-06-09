package org.example.idproject.view.dataTables;

import org.example.idproject.common.NicknameData;
import org.example.idproject.view.FXMLAddresses;

import java.io.IOException;

public class NicknameNameDataTable extends CustomDataTable<NicknameData> {
    public NicknameNameDataTable() throws IOException {
        super(FXMLAddresses.CLAN_NAME_DATA_TABLE);
    }

    @Override
    protected void initialize() {
        addDataColumn("Nickname", "nickname");
    }
}
