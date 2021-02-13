package com.company.ecommerce.app.utils;

public class ConvertersUtil {

    public static Long converterStringToLong(String value) {
        try {
            return Long.parseLong(value.trim());
        }catch (Exception e){
            return null;
        }
    }

    public static Boolean converterStringToBoolean(String value) {
        try {
            return Boolean.parseBoolean(value.trim());
        }catch (Exception e){
            return null;
        }
    }

    public static Integer converterStringToInteger(String value) {
        try {
            return Integer.parseInt(value.trim());
        }catch (Exception e){
            return null;
        }
    }

    public static String converterLongToString(Long value) {
        try {
            return String.valueOf(value);
        }catch (Exception e){
            return "";
        }
    }
}
