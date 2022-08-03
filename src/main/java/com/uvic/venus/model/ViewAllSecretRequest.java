package com.uvic.venus.model;

public class ViewAllSecretRequest {
    private String owner;
    public ViewAllSecretRequest() {}
    public ViewAllSecretRequest(String owner) {
        this.owner = owner;
    }
    public String getOwner() {
        return owner;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }
}
