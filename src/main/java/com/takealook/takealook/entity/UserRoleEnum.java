package com.takealook.takealook.entity;

public enum UserRoleEnum {
    /**
     * 사용하지 않을 객체이긴 한데 Spring Security를 사용하려면 뺄 수 없는 것 같음
     */
    USER(Authority.USER),  // 사용자 권한
    ADMIN(Authority.ADMIN);  // 관리자 권한

    private final String authority;

    UserRoleEnum(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

    public static class Authority {
        public static final String USER = "ROLE_USER";
        public static final String ADMIN = "ROLE_ADMIN";
    }
}