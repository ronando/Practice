package com.hkjc.jessepractice.xml;

import java.util.List;

/**
 * Created by jesse on 5/11/15.
 */
public class AAA {

    private List<BBB> bb;
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<BBB> getBb() {

        return bb;
    }

    public void setBb(List<BBB> bb) {
        this.bb = bb;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
