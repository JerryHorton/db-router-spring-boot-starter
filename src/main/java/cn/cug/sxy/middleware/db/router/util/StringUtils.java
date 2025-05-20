package cn.cug.sxy.middleware.db.router.util;

/**
 * @version 1.0
 * @Date 2025/5/20 13:54
 * @Description 字符串工具类
 * @Author jerryhotton
 */

public class StringUtils {

    public StringUtils() {
    }

    public static String middleScoreToCamelCase(String input) {
        StringBuilder result = new StringBuilder();
        boolean nextUpperCase = false;

        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);
            if (currentChar == '-') {
                nextUpperCase = true;
            } else if (nextUpperCase) {
                result.append(Character.toUpperCase(currentChar));
                nextUpperCase = false;
            } else {
                result.append(currentChar);
            }
        }

        return result.toString();
    }

    public static boolean isBlank(final CharSequence str) {
        if (str == null || str.length() == 0) {
            return true;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(final CharSequence str) {
        return !isBlank(str);
    }

}
