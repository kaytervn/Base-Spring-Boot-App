package com.app.utils;

import org.apache.commons.lang.RandomStringUtils;

public final class StringUtils {
    public static String generateRandomString(int length) {
        return RandomStringUtils.randomAlphanumeric(length).toLowerCase();
    }
}