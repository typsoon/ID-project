package org.example.idproject.common;

public record NicknameData(int playerID, String nickname, String dateFrom) implements HasID {
    @Override
    public int getID() {
        return playerID;
    }
}
