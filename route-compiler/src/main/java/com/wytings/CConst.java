package com.wytings;

/**
 * Created by rex on 06/10/2017.
 *
 * @author wytings@gmail.com
 */


public final class CConst {
    // support Java type
    static final String BYTE_PRIMITIVE = "byte";
    static final String BYTE = "java.lang.Byte";
    static final String SHORT_PRIMITIVE = "short";
    static final String SHORT = "java.lang.Short";
    static final String INTEGER_PRIMITIVE = "int";
    static final String INTEGER = "java.lang.Integer";
    static final String LONG_PRIMITIVE = "long";
    static final String LONG = "java.lang.Long";
    static final String FLOAT_PRIMITIVE = "float";
    static final String FLOAT = "java.lang.Float";
    static final String DOUBLE_PRIMITIVE = "double";
    static final String DOUBLE = "java.lang.Double";
    static final String BOOLEAN_PRIMITIVE = "boolean";
    static final String BOOLEAN = "java.lang.Boolean";
    static final String STRING = "java.lang.String";
    static final String SERIALIZABLE = "java.io.Serializable";
    static final String PARCELABLE = "android.os.Parcelable";

    // suffix
    static final String ROUTE_MAP_LOADER = "_$RouteMapLoader";
    static final String AUTO_VALUE_HELPER = "_$AutoValueHelper";

    // class full name
    private static final String DEPENDENCY_PACKAGE_NAME = "com.wytings.route.api";
    static final String ROUTE_LOADER_INTERFACE = DEPENDENCY_PACKAGE_NAME + ".IRouteMapLoader";
    static final String AUTO_VALUE_INTERFACE = DEPENDENCY_PACKAGE_NAME + ".IAutoValueHelper";
    static final String ACTIVITY_CLASS = "android.app.Activity";

    static final String PACKAGE_NAME_GENERATE = "com.wytings.route";

    // text
    static final String WARN_GENERATE = "THIS FILE WAS GENERATED!!! DO NOT EDIT IT";

    private CConst() throws IllegalAccessException {
        throw new IllegalAccessException("no instance");
    }

}
