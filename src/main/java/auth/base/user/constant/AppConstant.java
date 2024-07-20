package auth.base.user.constant;

import auth.base.user.utils.ConfigurationService;

import java.util.AbstractMap;
import java.util.Map;

public interface AppConstant {
    String APP_CONTROLLER_PACKAGE = "auth.base.user.controller";
    String APP_THREAD_POOL_EXECUTOR = "threadPoolExecutor";
    String APP_USER_SERVICE = "userService";

    String LOGIN_TYPE_INTERNAL = "LOGIN_TYPE_INTERNAL";
    String ROOT_DIRECTORY = ConfigurationService.getInstance().getString("file.upload-dir", "/tmp/upload");

    String GRANT_TYPE_PASSWORD = "password";
    String GRANT_TYPE_USER = "user";

    String ID_GENERATOR_NAME = "idGenerator";
    String ID_GENERATOR_STRATEGY = "auth.base.user.service.id.IdGenerator";

    String DATE_FORMAT = "dd/MM/yyyy";
    String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";

    Map<String, String> TIMEZONE = Map.ofEntries(
            new AbstractMap.SimpleEntry<>("Asia/Ho_Chi_Minh", "+07:00"),
            new AbstractMap.SimpleEntry<>("Asia/Bangkok", "+07:00")
    );
}
