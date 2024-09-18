package com.app.jwt;

import com.app.utils.ZipUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@Data
public class AppJwt {
    private static final String DELIM = "\\|";
    private static final String EMPTY_STRING = "<>";

    private Long tokenId;
    private Long accountId = -1L;
    private Long storeId = -1L;
    private String kind = EMPTY_STRING;
    private String permission = EMPTY_STRING;
    private Long deviceId = -1L;
    private Integer userKind = -1;
    private String username = EMPTY_STRING;
    private Integer tabletKind = -1;
    private Long orderId = -1L;
    private Boolean isSuperAdmin = false;
    private String tenantId = EMPTY_STRING;

    public String toClaim() {
        return ZipUtils.zipString(String.join(DELIM,
                accountId.toString(), storeId.toString(), kind, permission, deviceId.toString(),
                userKind.toString(), username, tabletKind.toString(), orderId.toString(),
                isSuperAdmin.toString(), tenantId));
    }

    public static AppJwt decode(String input) {
        try {
            String[] items = Objects.requireNonNull(ZipUtils.unzipString(input)).split(DELIM, 11);
            if (items.length >= 10) {
                AppJwt result = new AppJwt();
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

