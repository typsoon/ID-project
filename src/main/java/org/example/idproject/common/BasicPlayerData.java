package org.example.idproject.common;

@SuppressWarnings("unused")
public record BasicPlayerData(int ID, String currentNickname) implements HasID {

    public int getID() {
        return ID();
    }

    public String getCurrentNickname() {
        return currentNickname();
    }
}
