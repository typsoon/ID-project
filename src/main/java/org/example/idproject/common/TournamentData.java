package org.example.idproject.common;

public record TournamentData(int ID, String tournamentName) implements HasID {
    @Override
    public int getID() {
        return ID;
    }

    public String getTournamentName() {
        return tournamentName;
    }
}
