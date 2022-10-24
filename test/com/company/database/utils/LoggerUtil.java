package com.company.database.utils;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Contains Utility methods for logger.
 */
public class LoggerUtil {

    public static Logger getLogger(String name){
        Logger LOGGER = Logger.getLogger(name);
        Handler handlerObj = new ConsoleHandler();
        handlerObj.setLevel(Level.ALL);
        LOGGER.addHandler(handlerObj);
        LOGGER.setLevel(Level.ALL);
        LOGGER.setUseParentHandlers(false);
        return LOGGER;
    }
}
