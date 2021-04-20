package trainerapp.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Session {
    private final LocalDate date;
    private final double averageMiss;
    private final String user;

    public Session(String user, LocalDate date, double averageMiss) {
        this.date = date;
        this.averageMiss = averageMiss;
        this.user = user;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getAverageMiss() {
        return averageMiss;
    }

    public String getUser() {
        return user;
    }
}
