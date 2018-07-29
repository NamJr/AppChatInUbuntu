package com.example.win81version2.todolist.model;

import android.content.Intent;

import java.io.Serializable;

public class TaskInfo implements Serializable {
    private String id;
    private String name;
    private Boolean isDone;
    private Integer v;

    public TaskInfo() {
    }

    public TaskInfo(String name, Boolean isDone, String id, Integer v) {
        this.name = name;
        this.isDone = isDone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }
}
