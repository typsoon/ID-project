package org.example.idproject.common;

public record FullPlayerData(long passwordHash, String login, BasicPlayerData basicPlayerData) {
}
