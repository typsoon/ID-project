package org.example.idproject.common;

public record FriendData(String timestampFromAsStr, String timestampToAsStr, BasicPlayerData basicPlayerData) implements HasID {
    @Override
    public int getID() {
        return basicPlayerData.getID();
    }
}
