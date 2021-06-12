package me.hutcwp.util;

import android.content.res.Resources;

import java.util.IllegalFormatException;

import androidx.annotation.ArrayRes;
import androidx.annotation.StringRes;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/16
 *     desc  : utils about string
 * </pre>
 * 字符串工具类
 */
public final class StringUtils {

    private StringUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Return whether the string is null or 0-length.
     * 返回字符串是空还是0长度
     * @param s The string. 字符串
     * @return {@code true}: yes<br> {@code false}: no  {@code true}:是
     * {@code false}:否
     */
    public static boolean isEmpty(final CharSequence s) {
        return s == null || s.length() == 0;
    }

    /**
     * Return whether the string is null or whitespace.
     * 返回字符串是空还是空格
     * @param s The string.
     * @return {@code true}: yes<br> {@code false}: no {@code true}:是
     * {@code false}:否
     */
    public static boolean isTrimEmpty(final String s) {
        return (s == null || s.trim().length() == 0);
    }

    public static boolean isSpace(final String s) {
        if (s == null) {
            return true;
        }
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Return whether string1 is equals to string2.
     * 返回string1是否等于string2
     * @param s1 The first string. 第一个方案
     * @param s2 The second string. 第二个字符串
     * @return {@code true}: yes<br>{@code false}: no {@code true}:是
     * {@code false}:否
     */
    public static boolean equals(final CharSequence s1, final CharSequence s2) {
        if (s1 == s2) {
            return true;
        }
        int length;
        if (s1 != null && s2 != null && (length = s1.length()) == s2.length()) {
            if (s1 instanceof String && s2 instanceof String) {
                return s1.equals(s2);
            } else {
                for (int i = 0; i < length; i++) {
                    if (s1.charAt(i) != s2.charAt(i)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Return whether string1 is equals to string2, ignoring case considerations..
     * 返回string1是否等于string2，忽略大小写因素
     * @param s1 The first string. 第一个字符串
     * @param s2 The second string. 第二个字符串
     * @return {@code true}: yes<br>{@code false}: no {@code true}:是
     * {@code false}:否
     */
    public static boolean equalsIgnoreCase(final String s1, final String s2) {
        return s1 == null ? s2 == null : s1.equalsIgnoreCase(s2);
    }

    public static String null2Length0(final String s) {
        return s == null ? "" : s;
    }

    public static int length(final CharSequence s) {
        return s == null ? 0 : s.length();
    }

    public static String upperFirstLetter(final String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        if (!Character.isLowerCase(s.charAt(0))) {
            return s;
        }
        return (char) (s.charAt(0) - 32) + s.substring(1);
    }

    /**
     * Set the first letter of string lower.
     * 把绳子的第一个字母放低一点
     * @param s The string.
     * @return the string with first letter lower. 首字母较低的字符串
     */
    public static String lowerFirstLetter(final String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        if (!Character.isUpperCase(s.charAt(0))) {
            return s;
        }
        return String.valueOf((char) (s.charAt(0) + 32)) + s.substring(1);
    }

    /**
     * Reverse the string.
     * 反向的字符串
     * @param s The string.
     * @return the reverse string. 相反的字符串
     */
    public static String reverse(final String s) {
        if (s == null) {
            return "";
        }
        int len = s.length();
        if (len <= 1) {
            return s;
        }
        int mid = len >> 1;
        char[] chars = s.toCharArray();
        char c;
        for (int i = 0; i < mid; ++i) {
            c = chars[i];
            chars[i] = chars[len - i - 1];
            chars[len - i - 1] = c;
        }
        return new String(chars);
    }

    /**
     * Convert string to DBC.
     * 将字符串转换为DBC
     * @param s The string.
     * @return the DBC string DBC字符串
     */
    public static String toDBC(final String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == 12288) {
                chars[i] = ' ';
            } else if (65281 <= chars[i] && chars[i] <= 65374) {
                chars[i] = (char) (chars[i] - 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * Convert string to SBC.
     * 转换字符串到SBC
     * @param s The string.
     * @return the SBC string
     */
    public static String toSBC(final String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == ' ') {
                chars[i] = (char) 12288;
            } else if (33 <= chars[i] && chars[i] <= 126) {
                chars[i] = (char) (chars[i] + 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    public static String getString(@StringRes int id) {
        return getString(id, (Object[]) null);
    }

    public static String getString(@StringRes int id, Object... formatArgs) {
        try {
            return format(Utils.getApp().getString(id), formatArgs);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return String.valueOf(id);
        }
    }

    public static String[] getStringArray(@ArrayRes int id) {
        try {
            return Utils.getApp().getResources().getStringArray(id);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return new String[]{String.valueOf(id)};
        }
    }

    /**
     * Format the string.
     * 格式的字符串
     * @param str  The string.
     * @param args The args.
     * @return a formatted string. 一个格式化的字符串
     */
    public static String format(String str, Object... args) {
        String text = str;
        if (text != null) {
            if (args != null && args.length > 0) {
                try {
                    text = String.format(str, args);
                } catch (IllegalFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        return text;
    }
}
