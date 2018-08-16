package me.black.library;

public class RegularUtil {
    /**
     * 是否是全数字
     * @param s
     * @return
     */
    public static boolean isNumber(String s) {
        if (s != null && !"".equals(s.trim()))
            return s.matches("^[0-9]*$");
        else
            return false;
    }
}
