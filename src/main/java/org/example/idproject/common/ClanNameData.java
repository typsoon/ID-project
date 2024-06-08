package org.example.idproject.common;

public record ClanNameData(String dateFrom, int clanID, String name) implements HasID {
    @Override
    public int getID() {
        return clanID;
    }
}
