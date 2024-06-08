package org.example.idproject.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@SuppressWarnings("unused")
public record BasicDuelData(int ID, int player1ID, int player2ID, String dateFrom, String dateTo, boolean outcome) implements HasID {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("EEEE, d MMMM yyyy", Locale.of("pl", "PL"));

    @Override
    public int getID() {
        return ID();
    }

    public int getFirstPlayerID() {
        return player1ID();
    }

    public int getSecondPlayerID() {
        return player2ID();
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public String getOutcome() {
        return outcome() ? "first won" : "second won";
    }
}
