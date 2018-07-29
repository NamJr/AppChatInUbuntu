
package com.example.win81version2.todolist.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class POJO {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("auth")
    @Expose
    private Object auth;
    @SerializedName("events")
    @Expose
    private Object events;
    @SerializedName("variables")
    @Expose
    private List<Object> variables = null;
    @SerializedName("order")
    @Expose
    private List<String> order = null;
    @SerializedName("folders_order")
    @Expose
    private List<Object> foldersOrder = null;
    @SerializedName("folders")
    @Expose
    private List<Object> folders = null;
    @SerializedName("requests")
    @Expose
    private List<Request> requests = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getAuth() {
        return auth;
    }

    public void setAuth(Object auth) {
        this.auth = auth;
    }

    public Object getEvents() {
        return events;
    }

    public void setEvents(Object events) {
        this.events = events;
    }

    public List<Object> getVariables() {
        return variables;
    }

    public void setVariables(List<Object> variables) {
        this.variables = variables;
    }

    public List<String> getOrder() {
        return order;
    }

    public void setOrder(List<String> order) {
        this.order = order;
    }

    public List<Object> getFoldersOrder() {
        return foldersOrder;
    }

    public void setFoldersOrder(List<Object> foldersOrder) {
        this.foldersOrder = foldersOrder;
    }

    public List<Object> getFolders() {
        return folders;
    }

    public void setFolders(List<Object> folders) {
        this.folders = folders;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

}
