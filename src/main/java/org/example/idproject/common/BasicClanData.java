package org.example.idproject.common;

@SuppressWarnings("unused")
public record BasicClanData(int ID, String currentName) implements HasID {
    public int getID() {
        return ID;
    }

    public String getCurrentName() {
        return currentName;
    }
}
