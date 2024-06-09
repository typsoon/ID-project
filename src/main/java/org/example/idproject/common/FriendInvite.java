package org.example.idproject.common;

public record FriendInvite(int senderId, int receiverID, String dateFrom, String dateTo) implements HasID{
    @Override
    public int getID() {
        return senderId;
    }

    public int getSenderID() {
        return senderId;
    }

    public int getReceiverID() {
        return receiverID;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }
}
