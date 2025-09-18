package com.example.my_blog.Entity;

public enum Role {
    USER,
    AUTHOR;

    public String getAuthority(){
        return "ROLE_" + this.name();
    }
}
