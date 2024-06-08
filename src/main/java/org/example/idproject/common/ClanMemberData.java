package org.example.idproject.common;

public record ClanMemberData(String dateFrom, int playerID, String playerNickname, String role, String dateTo,
                             Integer whoKicked, String whoKickedNickname) implements HasID {

    @Override
    public int getID() {
        return playerID;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public String getPlayerNickname() {
        return playerNickname;
    }

    public String getRole() {
        return role;
    }

    public String getDateTo() {
        return dateTo;
    }

    public Integer getWhoKickedNickname() {
        return whoKicked == 0 ? null : whoKicked;
    }
}
