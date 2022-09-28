package com.voedl.neu.Instances;

import com.voedl.neu.utils.Logger;

public class InstanceManager {
    Logger logger = null;
    public void setLogger(Logger l) {
        logger = l;
    }
    public Logger getLogger() {
        if(logger==null) {
            logger = new Logger();
        }
        return logger;
    }
}
