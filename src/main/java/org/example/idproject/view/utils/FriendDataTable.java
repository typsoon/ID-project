package org.example.idproject.view.utils;

import org.example.idproject.common.FriendData;
import org.example.idproject.view.FXMLAddresses;

import java.io.IOException;

public class FriendDataTable extends CustomDataTable<FriendData> {
    public FriendDataTable() throws IOException {
        super(FXMLAddresses.FRIEND_DATA_TABLE);
    }

    @Override
    protected void initialize() {

    }
}
