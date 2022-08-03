package com.uvic.venus.model;

public class DeleteSecretRequest {
    private String ID;
    private String owner;
    public DeleteSecretRequest(String ID, String owner) {
        this.ID = ID;
        this.owner = owner;
    }
    public String getOwner() {
        return owner;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }
    public String getID() {
        return ID;
    }
    public void setID(String ID) {
        this.ID = ID;
    }
}
