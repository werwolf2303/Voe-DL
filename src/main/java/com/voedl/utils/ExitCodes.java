package com.voedl.utils;

public enum ExitCodes {
    SUCCESSFULL(1), USER(3), PANIC(4), UNHANDLED(5);
    private final int code;
    ExitCodes(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }
}
