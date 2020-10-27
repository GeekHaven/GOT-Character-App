package com.example.gotcharacterapp;

import java.io.Serializable;
import java.util.List;

public class CharacterItem implements Serializable {


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

    public String getPeople_killed() {
        String str = "";
        for(String s : people_killed){
            str += s + "/n";
        }
        return str;
    }

    public String getKilled_by() {
        String str = "";
        for(String s : killed_by){
            str += s + "/n";
        }
        return str;
    }

    public String getParents() {
        String str = "";
        for(String s : parents){
            str += s + "/n";
        }
        return str;
    }

    public String getSiblings() {
        String str = "";
        for(String s : siblings){
            str += s + "/n";
        }
        return str;
    }

    public String getSpouse() {
        String str = "";
        for(String s : spouse){
            str += s + "/n";
        }
        return str;
    }

    public String getChildren() {
        String str = "";
        for(String s : children){
            str += s + "/n";
        }
        return str;
    }

    public boolean getFavourite() { return favourite; }

    public void setFavourite(boolean favourite){ this.favourite = favourite; }
}
