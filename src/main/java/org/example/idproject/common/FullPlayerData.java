package org.example.idproject.common;

//TODO: maybe it should be ClanId instead of ClanName
public record FullPlayerData(long passwordHash, String login, String currentClanName, int currentClanId, BasicPlayerData basicPlayerData) {
}
