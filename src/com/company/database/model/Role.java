package com.company.database.model;

public enum Role {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN"),
    ANONYMOUS("ROLE_ANONYMOUS");

    private final String type;

    Role(String type) {
        this.type  = type ;
    }
    public  String getType(){
        return type;
    }
}
