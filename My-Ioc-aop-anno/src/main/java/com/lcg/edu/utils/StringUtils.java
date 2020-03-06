package com.lcg.edu.utils;

/**
 * @author lichenggang
 * @date 2020/3/5 2:40 下午
 * @description
 */
public class StringUtils {






    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }

    public static boolean isEmpty(String value) {
        return value == null || value.length() == 0;
    }

    //首字母小写
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }


}