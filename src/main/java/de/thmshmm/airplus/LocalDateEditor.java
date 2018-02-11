package de.thmshmm.airplus;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateEditor extends PropertyEditorSupport {

    private String pattern = null;

    public LocalDateEditor() {
    }

    public LocalDateEditor setPattern(String pattern) {
        this.pattern = pattern;
        return this;
    }

    public void setAsText(String text) throws IllegalArgumentException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDate date = LocalDate.parse(text, formatter);
        this.setValue(date == null ? null : date);
    }
}
