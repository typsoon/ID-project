package org.example.idproject.common;

public record ClanApplication(int clanID, int playerID, String dateFrom, String dateTo) implements HasID {
    @Override
    public int getID() {
        return clanID;
    }


}
