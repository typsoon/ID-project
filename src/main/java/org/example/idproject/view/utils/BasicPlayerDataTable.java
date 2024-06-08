package org.example.idproject.view.utils;

import org.example.idproject.common.BasicPlayerData;

public class BasicPlayerDataTable extends CustomDataTable<BasicPlayerData> {

    @Override
    protected void initialize() {
        addDataColumn("ID", "ID");
        addDataColumn("Nickname", "currentNickname");
    }
}
