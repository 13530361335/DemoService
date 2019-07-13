package com.joker.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 统一常量存放
 */
public interface Constant {

    String SERVICE_START_TIME = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());

    Boolean SERVICE_OS_ISWINDOWS = System.getProperty("os.name").startsWith("Win");

    String PACKAGE_PATH_CONTROLLER = "com.joker.sql.controller";

    String PACKAGE_PATH_DAO = "com.joker.sql.dao";

    String REDIS_KEY_IMAGE = "image";

}
