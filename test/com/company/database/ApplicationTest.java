package com.company.database;

import com.company.database.annotations.UnitTest;
import com.company.database.annotations.UnitTester;
import com.company.database.utils.ColorUtil;
import com.company.database.utils.LoggerUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static java.util.Arrays.stream;

/**
 * This is the main class to test all the test cases.
 */

public class ApplicationTest {
    private static Map<String, String> loadClasses(){
        Map<String, String> map = new HashMap<>();
        map.put("DBUtil", "com.company.database.config.DBUtilTest");
        map.put("GenericDao","com.company.database.dao.GenericDaoImplTest");
        return map;
    }

    public static void main(String[] args) {
        loadClasses().forEach((key, value) -> {
            Logger logger = LoggerUtil.getLogger(value);
            try {
                Class<?> clazz = Class.forName(value);
                logger.info(ColorUtil.CYAN + value + ColorUtil.WHITE);
                Object object = null;
                if (clazz.isAnnotationPresent(UnitTester.class)) {
                    Constructor<?> constructor = clazz.getConstructor();
                    object = constructor.newInstance();
                }
                executeAllMethods(clazz, object, logger);
            } catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
                logger.severe(ColorUtil.RED + e.getMessage() + ColorUtil.WHITE);
            }
        });
    }

    private static void executeAllMethods(Class<?> clazz, Object object, Logger logger) {
        stream(clazz.getDeclaredMethods()).filter(method -> method.isAnnotationPresent(UnitTest.class)).forEach(method -> {
            method.setAccessible(true);
            try {
                method.invoke(object);
            } catch (IllegalAccessException e) {
                logger.severe(ColorUtil.RED + e.getMessage() + ColorUtil.WHITE);
                return;
            }catch (InvocationTargetException e){
               AssertionError assertionError = (AssertionError) e.getTargetException();
                logger.info("Method : " + ColorUtil.WHITE_BOLD_BRIGHT + method.getName() + ColorUtil.RED_BOLD_BRIGHT + " : FAILED" + ColorUtil.WHITE);
                logger.severe(ColorUtil.RED_BOLD_BRIGHT + assertionError.getMessage() + ColorUtil.WHITE);
                return;
            }
            logger.info("Method : " + ColorUtil.WHITE_BOLD_BRIGHT + method.getName() + ColorUtil.GREEN_BOLD_BRIGHT + " : PASSED" + ColorUtil.WHITE);
        });
    }
}
