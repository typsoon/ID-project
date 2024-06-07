package org.example.idproject.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@SuppressWarnings("unused")
public record BasicChallengeData(int ID, Date dateFrom, Date dateTo, String objective) implements HasID {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("EEEE, d MMMM yyyy", Locale.of("pl", "PL"));

    @Override
    public int getID() {
        return ID();
    }

    public String getDateFrom() {
        return DATE_FORMAT.format(dateFrom());
    }

    public String getDateTo() {
        return DATE_FORMAT.format(dateTo());
    }

    public String getObjective() {
        return objective();
    }
}
