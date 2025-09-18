package org.example.projectcdr;

import javafx.beans.property.LongProperty;
import javafx.beans.property.StringProperty;

public class TableDetail {
    private final StringProperty name;
    private final StringProperty startTime;
    private final LongProperty records;
    private final LongProperty minutes;

    public TableDetail(StringProperty name, StringProperty startTime, LongProperty records, LongProperty minutes) {
        this.name = name;
        this.startTime = startTime;
        this.records = records;
        this.minutes = minutes;
    }

    public StringProperty nameProperty() {
        return this.name;
    }

    public StringProperty startTimeProperty() {
        return this.startTime;
    }

    public LongProperty recordsProperty() {
        return this.records;
    }

    public LongProperty minutesProperty() {
        return this.minutes;
    }
}
