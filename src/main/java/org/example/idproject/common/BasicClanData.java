package org.example.idproject.common;

public record BasicClanData(int ID, String currentName) {
    public int getID() {
        return ID;
    }

    public String getCurrentName() {
        return currentName;
    }
}
