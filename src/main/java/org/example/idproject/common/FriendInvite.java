package org.example.idproject.common;

public record FriendInvite(int senderId, int receiverId, String dateFrom, String dateTo) implements HasID{
    @Override
    public int getID() {
        return senderId;
    }
}
