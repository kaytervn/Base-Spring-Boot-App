package com.app.utils;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class TenantUtils {
    static String JDBC_PREFIX = "jdbc:mysql://";
    static char QUERY_SEPARATOR = '?';
    static char PATH_SEPARATOR = '/';

    public static String parseDatabaseNameFromConnectionString(String url) {
        int startIndex = url.indexOf(PATH_SEPARATOR, JDBC_PREFIX.length()) + 1;
        int endIndex = url.indexOf(QUERY_SEPARATOR);
        return url.substring(startIndex, endIndex);
    }
}