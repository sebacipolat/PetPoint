package com.cipolat.petpoint.Data.Model;

import android.support.annotation.NonNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;


public class Pet implements Comparable<Pet>, Serializable {

    public static final String AVAILABLE = "available";

    private long id;
    private String name;
    private String status;
    private ArrayList<String> photoUrls;

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

    public ArrayList<String> getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(ArrayList<String> photoUrls) {
        this.photoUrls = photoUrls;
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

    @Override
    public int compareTo(@NonNull Pet pet) {
        return Long.compare(this.id, pet.getId());

    }

    public static Comparator<Pet> PetComparatorAscending = new Comparator<Pet>() {
        public int compare(Pet pet1, Pet pet2) {
            return pet1.compareTo(pet2);
        }

    };
    public static Comparator<Pet> PetComparatorDescending = new Comparator<Pet>() {
         public int compare(Pet pet1, Pet pet2) {
            return pet2.compareTo(pet1);
        }

    };
}
