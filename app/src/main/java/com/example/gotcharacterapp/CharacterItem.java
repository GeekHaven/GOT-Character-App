package com.example.gotcharacterapp;

import java.util.List;

public class CharacterItem {


    private String name;
    private  String image_url;
    private String house;
    private List<String> people_killed;
    private List<String> killed_by;
    private List<String> parents;
    private List<String> siblings;
    private List<String> spouse;
    private List<String> children;
    private boolean favourite=false;              // favourite attribute


    public CharacterItem(String name, String image_url, String house, List<String> people_killed, List<String> killed_by, List<String> parents, List<String> siblings, List<String> spouse, List<String> children) {
        this.name = name;
        this.image_url = image_url;
        this.house = house;
        this.people_killed = people_killed;
        this.killed_by = killed_by;
        this.parents = parents;
        this.siblings = siblings;
        this.spouse = spouse;
        this.children=children;         
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

    public List<String> getParents() {
        return parents;
    }

    public List<String> getSiblings() {
        return siblings;
    }

    public List<String> getSpouse() {
        return spouse;
    }

    public List<String> getChildren() {
        return children;
    }

    public boolean getFavourite() { return favourite; }

    public void setFavourite(boolean favourite){ this.favourite = favourite; }
}
