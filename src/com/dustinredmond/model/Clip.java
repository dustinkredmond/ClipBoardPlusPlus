package com.dustinredmond.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class Clip implements Serializable {
    private final String created;
    private String clip;
    private String notes;

    public Clip() {
        this.created = SDF.format(Date.from(Instant.now()));
        this.clip = "";
        this.notes = "";
    }

    public String getCreated() {
        return this.created;
    }

    public String getClip() {
        return this.clip;
    }

    public void setClip(String text) {
        this.clip = text;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String text) {
        this.notes = text;
    }

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
}
