package org.example.idproject.view.dataTables;

import org.example.idproject.common.FriendData;
import org.example.idproject.view.FXMLAddresses;

import java.io.IOException;

public class FriendDataTable extends CustomDataTable<FriendData> {
    public FriendDataTable() throws IOException {
        super(FXMLAddresses.FRIEND_DATA_TABLE);
    }

    @Override
    protected void initialize() {
        FriendData friendData;

        addDataColumn("Date From", "timestampFromAsStr");
        addDataColumn("Date From", "timestampToAsStr");
        addDataColumn("ID", "ID");
        addDataColumn("Nickname", "nickname");
    }
}
