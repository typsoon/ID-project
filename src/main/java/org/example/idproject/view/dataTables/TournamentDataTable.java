package org.example.idproject.view.dataTables;

import org.example.idproject.common.TournamentData;
import org.example.idproject.view.FXMLAddresses;

import java.io.IOException;

public class TournamentDataTable extends CustomDataTable<TournamentData> {
    public TournamentDataTable() throws IOException {
        super(FXMLAddresses.BASIC_PLAYER_DATA_TABLE);
    }

    @Override
    protected void initialize() {
        addDataColumn("ID", "ID");
        addDataColumn("Tournament Name", "tournamentName");
    }
}
