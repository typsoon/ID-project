package org.example.idproject.common;

public record ClanMemberData(String dateFrom, int playerID, String playerNickname, String role, String dateTo, int whoKicked, String whoKickedNickname) implements HasID {

    @Override
    public int getID() {
        return playerID;
    }
}
