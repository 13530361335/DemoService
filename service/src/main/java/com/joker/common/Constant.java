package com.joker.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Joker Jing
 * @date: 2019/7/29
 * @description: 统一常量存放
 */
public interface Constant {

    String SERVICE_START_TIME = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());

    Boolean SERVICE_OS_ISWINDOWS = System.getProperty("os.name").startsWith("Win");

    String APPLICATION_ACTIVE_DEV = "dev";

    String PACKAGE_PATH_CONTROLLER = "com.joker.controller";

    String PACKAGE_PATH_DAO = "com.joker.sql.dao";

    String REDIS_KEY_IMAGE = "image";

    String REDIS_KEY_VERIFICATION_CODE = "verification_code";

    String REDIS_KEY_TASK_THREAD = "task_thread";

}
