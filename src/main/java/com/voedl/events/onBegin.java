package com.voedl.events;

import com.voedl.GlobalValues;

public class onBegin {
    public onBegin() {
        if(System.getProperty("os.name").contains("win")) {
            GlobalValues.isLinux = false;
        }
    }
}
