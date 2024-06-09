package org.example.idproject.common;

public record TournamentMatch(int ID, String tournamentName, int duelID, Integer leftChild, Integer rightChild) implements HasID {
    @Override
    public int getID() {
        return ID;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public int getDuelID() {
        return duelID;
    }

    public int getLeftChild() {
        return leftChild;
    }

    public int getRightChild() {
        return rightChild;
    }
}

