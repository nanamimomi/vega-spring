package com.uvic.venus.model;

import com.uvic.venus.model.SecretInfo;

public class EditSecretRequest {
    private String name;
    private String text;
    private SecretInfo secret;

    public EditSecretRequest(String name, String text, SecretInfo secret) {
        this.name = name;
        this.text = text;
        this.secret = secret;
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

    public SecretInfo getSecret() {
        return secret;
    }

    public void setSecret(SecretInfo secret) {
        this.secret = secret;
    }
}
