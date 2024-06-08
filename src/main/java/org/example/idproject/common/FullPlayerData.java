package org.example.idproject.common;

//TODO: maybe it should be ClanId instead of ClanName
public record FullPlayerData(long passwordHash, String login, String currentClanName, Integer currentClanId, BasicPlayerData basicPlayerData) implements HasID {
    @Override
    public int getID() {
        return basicPlayerData.ID();
    }
}
