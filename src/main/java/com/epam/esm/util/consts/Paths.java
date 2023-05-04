package com.epam.esm.util.consts;

public class Paths {
    public static final String VERSION = "/api/";
    public static final String ALL_SUFFIX = "/**";

    public static final String AUTH_PATH = VERSION + "auth";
    public static final String ORDERS_PATH = VERSION + "orders";
    public static final String TAGS_PATH = VERSION + "tags";
    public static final String USERS_PATH = VERSION + "users";
    public static final String CERTIFICATES_PATH = VERSION + "certificates";

    public static final String AUTH_PATH_ALL = AUTH_PATH + ALL_SUFFIX;
    public static final String ORDERS_PATH_ALL = ORDERS_PATH + ALL_SUFFIX;
    public static final String TAGS_PATH_ALL = TAGS_PATH + ALL_SUFFIX;
    public static final String USERS_PATH_ALL = USERS_PATH + ALL_SUFFIX;
    public static final String CERTIFICATES_PATH_ALL = CERTIFICATES_PATH + ALL_SUFFIX;
}
