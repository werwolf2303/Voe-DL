package com.voedl.Instances;

import com.voedl.utils.Logger;

public class InstanceManager {
    private static Logger logger = null;
    public static void setLogger(Logger l) {
        logger = l;
    }
    public static Logger getLogger() {
        if(logger==null) {
            logger = new Logger();
        }
        return logger;
    }
}
