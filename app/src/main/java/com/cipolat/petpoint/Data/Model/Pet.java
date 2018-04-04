package com.cipolat.petpoint.Data.Model;

/**
 * Created by sebastian on 04/04/18.
 */

public class Pet {

    public static final String AVAILABLE = "available";

    private long id;
    private String name;
    private String status;

    public Pet(long id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public static String getAVAILABLE() {
        return AVAILABLE;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
