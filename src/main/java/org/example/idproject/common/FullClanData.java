package org.example.idproject.common;

public record FullClanData(String imageAddress, BasicClanData basicClanData) implements HasID {

    @Override
    public int getID() {
        return basicClanData.getID();
    }
}
