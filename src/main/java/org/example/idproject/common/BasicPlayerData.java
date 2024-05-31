package org.example.idproject.common;

public record BasicPlayerData(int ID, String currentNickname) {

    public int getID() {
        return ID();
    }

    public String getCurrentNickname() {
        return currentNickname();
    }
}
