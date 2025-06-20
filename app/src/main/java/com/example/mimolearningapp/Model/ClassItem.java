package com.example.mimolearningapp.Model;

public class ClassItem {
    private int id;
    private String name;
    private int CreatorId;

    public ClassItem() {
    }

    public ClassItem(int id, String name, int creatorId) {
        this.id = id;
        this.name = name;
        CreatorId = creatorId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCreatorId() {
        return CreatorId;
    }

    public void setCreatorId(int creatorId) {
        CreatorId = creatorId;
    }
}
