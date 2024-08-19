package com.app.utils;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtUtils {
    static final String DELIM = "\\|";
    static final String EMPTY_STRING = "<>";

    Long tokenId;
    Long accountId = -1L;
    Long storeId = -1L;
    String kind = EMPTY_STRING;
    String permission = EMPTY_STRING;
    Long deviceId = -1L;
    Integer userKind = -1;
    String username = EMPTY_STRING;
    Integer tabletKind = -1;
    Long orderId = -1L;
    Boolean isSuperAdmin = false;
    String tenantId = EMPTY_STRING;

    public String toClaim() {
        return ZipUtils.zipString(String.join(DELIM,
                accountId.toString(), storeId.toString(), kind, permission, deviceId.toString(),
                userKind.toString(), username, tabletKind.toString(), orderId.toString(),
                isSuperAdmin.toString(), tenantId));
    }

    public static JwtUtils decode(String input) {
        try {
            String[] items = Objects.requireNonNull(ZipUtils.unzipString(input)).split(DELIM, 11);
            if (items.length >= 10) {
                JwtUtils result = new JwtUtils();
                result.setAccountId(parseLong(items[0]));
                result.setStoreId(parseLong(items[1]));
                result.setKind(checkString(items[2]));
                result.setPermission(checkString(items[3]));
                result.setDeviceId(parseLong(items[4]));
                result.setUserKind(parseInt(items[5]));
                result.setUsername(checkString(items[6]));
                result.setTabletKind(parseInt(items[7]));
                result.setOrderId(parseLong(items[8]));
                result.setIsSuperAdmin(Boolean.parseBoolean(items[9]));
                if (items.length > 10) {
                    result.setTenantId(checkString(items[10]));
                }
                return result;
            }
        } catch (Exception e) {
            log.error("Error decoding Jwt", e);
        }
        return null;
    }

    private static Long parseLong(String input) {
        try {
            long value = Long.parseLong(input);
            return value > 0 ? value : null;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    private static Integer parseInt(String input) {
        try {
            int value = Integer.parseInt(input);
            return value > 0 ? value : null;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    private static String checkString(String input) {
        return EMPTY_STRING.equals(input) ? null : input;
    }
}

