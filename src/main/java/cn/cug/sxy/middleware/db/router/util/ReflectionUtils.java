package cn.cug.sxy.middleware.db.router.util;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @version 1.0
 * @Date 2025/5/20 16:07
 * @Description 反射工具类
 * @Author jerryhotton
 */

public class ReflectionUtils {

    public static String getFirstNonBlankFieldValue(Object[] args, String fieldName) {
        if (null == args || 0 == args.length || StringUtils.isBlank(fieldName)) {
            return null;
        }
        for (Object arg : args) {
            if (null == arg) {
                continue;
            }
            // 若为基础类型直接获取
            if (args.length == 1 && isPrimitiveOrString(arg)) {
                return arg.toString();
            }
            // 若为对象字段通过反射获取
            Object val = getFieldValue(arg, fieldName);
            if (null != val) {
                String strVal = val.toString();
                if (StringUtils.isNotBlank(strVal)) {
                    return strVal;
                }
            }
        }
        return null;
    }

    public static Object getFieldValue(Object object, String fieldName) {
        if (null == object || StringUtils.isBlank(fieldName)) {
            return null;
        }
        try {
            Field field = findField(object.getClass(), fieldName);
            if (null == field) {
                return null;
            }
            field.setAccessible(true);
            return field.get(object);
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    public static Field findField(Class<?> clazz, String fieldName) {
        while (null != clazz && clazz != Object.class) {
            try {
                return clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        return null;
    }

    private static boolean isPrimitiveOrString(Object arg) {
        return arg instanceof String ||
                arg instanceof Integer ||
                arg instanceof Long ||
                arg instanceof Short ||
                arg instanceof Double ||
                arg instanceof Float ||
                arg instanceof Boolean;
    }

}
