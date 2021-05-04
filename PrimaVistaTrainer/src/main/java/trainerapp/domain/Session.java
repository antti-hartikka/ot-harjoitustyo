package trainerapp.domain;

import java.time.LocalDateTime;

public class Session {
    private final LocalDateTime date;
    private final double averageMiss;
    private final String user;

    /**
     * This class is used to model single session and its data.
     * @param user Username as a String
     * @param date Date and time of the session
     * @param averageMiss Average miss of training session, units are semitones.
     */
    public Session(String user, LocalDateTime date, double averageMiss) {
        this.date = date;
        this.averageMiss = averageMiss;
        this.user = user;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public double getAverageMiss() {
        return averageMiss;
    }

    public String getUser() {
        return user;
    }
}
