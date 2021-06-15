package com.chqiuu.proxy.common.enums;

import lombok.Getter;

/**
 * @author chqiu
 */

@Getter
public enum UserRoleEnum {
    /**
     * 系统管理员权限
     */
    SYSTEM_ADMIN(1, "ROLE_SYSTEM_ADMIN"),
    /**
     * OTT成员权限
     */
    OTT(3, "ROLE_OTT"),
    /**
     * 一般用户
     */
    USER(6, "ROLE_USER");

    private Integer userType;
    private String role;

    UserRoleEnum(int userType, String role) {
        this.userType = userType;
        this.role = role;
    }

    public static UserRoleEnum getByUserType(Integer userType) {
        for (UserRoleEnum e : UserRoleEnum.values()) {
            if (e.getUserType().equals(userType)) {
                return e;
            }
        }
        return null;
    }
}
