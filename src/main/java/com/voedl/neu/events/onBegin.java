package com.voedl.neu.events;

import com.voedl.neu.GlobalValues;

public class onBegin {
    public onBegin() {
        if(System.getProperty("os.name").contains("win")) {
            GlobalValues.isLinux = false;
        }
    }
}
