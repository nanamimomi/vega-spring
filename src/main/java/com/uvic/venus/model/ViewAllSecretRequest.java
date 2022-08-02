package com.uvic.venus.model;
import java.io.*;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties.View;

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
