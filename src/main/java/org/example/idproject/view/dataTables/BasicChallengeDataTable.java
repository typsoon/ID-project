package org.example.idproject.view.dataTables;

import org.example.idproject.common.BasicChallengeData;
import org.example.idproject.view.FXMLAddresses;

import java.io.IOException;

public class BasicChallengeDataTable extends CustomDataTable<BasicChallengeData> {
    public BasicChallengeDataTable() throws IOException {
        super(FXMLAddresses.CHALLENGES_DATA_TABLE);
    }

    @Override
    protected void initialize() {
        addDataColumn("ID", "ID");
        addDataColumn("Date from", "dateFrom");
        addDataColumn("Date to", "dateTo");
        addDataColumn("Objective", "objective");
        addDataColumn("Description", "description");
    }
}
