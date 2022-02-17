package com.yemin.twitter.domain;

public enum AuthType {
    ADMIN("ROLE_ADMIN"), USER("ROLE_USER");

    final private String name;

    public String getName() {
        return name;
    }
    private AuthType(String name){
        this.name = name;
    }
}
