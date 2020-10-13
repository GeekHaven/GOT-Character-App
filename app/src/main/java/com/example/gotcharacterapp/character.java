package com.example.gotcharacterapp;

import java.util.List;

public class character {


    private String name;
    private  String image_url;
    private String house;
    private List<String> people_killed;
    private List<String> killed_by;
    private String parents;
    private String siblings;
    private String spouse;


    public character(String name, String image_url, String house, List<String> people_killed, List<String> killed_by, String parents, String siblings, String spouse) {
        this.name = name;
        this.image_url = image_url;
        this.house = house;
        this.people_killed = people_killed;
        this.killed_by = killed_by;
        this.parents = parents;
        this.siblings = siblings;
        this.spouse = spouse;
    }

    public String getName() {
        return name;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getHouse() {
        return house;
    }

    public List<String> getPeople_killed() {
        return people_killed;
    }

    public List<String> getKilled_by() {
        return killed_by;
    }

    public String getParents() {
        return parents;
    }

    public String getSiblings() {
        return siblings;
    }

    public String getSpouse() {
        return spouse;
    }
}
