package org.example.idproject.view.dataTables;

import org.example.idproject.common.ClanMemberData;
import org.example.idproject.view.FXMLAddresses;

import java.io.IOException;

public class MemberDataTable extends CustomDataTable<ClanMemberData> {
    public MemberDataTable() throws IOException {
        super(FXMLAddresses.MEMBER_DATA_TABLE);
    }

    @Override
    protected void initialize() {
        addDataColumn("Date From", "dateFrom");
        addDataColumn("Player Nickname", "playerNickname");
        addDataColumn("Role", "role");
        addDataColumn("Date To", "dateTo");
        addDataColumn("Who Kicked", "whoKickedNickname");
    }
}
