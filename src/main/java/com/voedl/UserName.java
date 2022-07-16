package com.voedl;

public class UserName {
    public String get() {
        if(new DetectOS().OS().equals("WINDOWS")) {
            return System.getenv("username");
        }else{
            return System.getenv("USER");
        }
    }
}
