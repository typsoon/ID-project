package org.example.idproject.common;

public record Message(String sentDate, int senderID, String senderNickName, String msgText) implements HasID {
    @Override
    public int getID() {
        return senderID;
    }

    public String getSentDate() {
        return sentDate;
    }

    public String getSenderNickname() {
        return senderNickName;
    }

    public String getMsgText() {
        return msgText;
    }
}
