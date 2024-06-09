package org.example.idproject.view.dataTables;

import org.example.idproject.common.FriendInvite;
import org.example.idproject.view.FXMLAddresses;

import java.io.IOException;

public class FriendInviteDataTable extends CustomDataTable<FriendInvite> {
    public FriendInviteDataTable() throws IOException {
        super(FXMLAddresses.FRIEND_INVITE_DATA_TABLE);
    }

    @Override
    protected void initialize() {

    }
}
