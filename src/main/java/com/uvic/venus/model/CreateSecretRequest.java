package com.uvic.venus.model;

public class CreateSecretRequest {
    private String name;
    private String text;
    private String owner;

    public CreateSecretRequest(String name, String text, String owner) {
        this.name = name;
        this.text = text;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }
}
