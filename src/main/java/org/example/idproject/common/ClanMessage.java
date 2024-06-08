package org.example.idproject.common;

public record ClanMessage(String sentDate, int senderID, String senderNickName, String msgText) implements HasID {
    @Override
    public int getID() {
        return senderID;
    }
}
