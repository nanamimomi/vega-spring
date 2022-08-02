package com.uvic.venus.model;

import java.util.UUID;

public class UpdateSecretRequest {
    private String name;
    private String text;
    private UUID uuid;

    public UpdateSecretRequest(String name, String text, UUID uuid) {
        this.name = name;
        this.text = text;
        this.uuid = uuid;
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

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
