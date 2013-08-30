package com.efsf.sf.collection;

import java.io.Serializable;

public class DictionaryItem implements Serializable{

private int id;
private String name;
private Integer isActive;


public DictionaryItem(int id, String name, Integer isActive){
    this.id=id;
    this.name=name;
    this.isActive=isActive;
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

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }
 
}