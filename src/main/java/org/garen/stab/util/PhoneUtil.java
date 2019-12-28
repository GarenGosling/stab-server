package org.garen.stab.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneUtil
{
    public static boolean isPhone(String phone)
    {
        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
        if (phone.length() != 11) {
            return false;
        }
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(phone);
        boolean isMatch = m.matches();
        if (isMatch) {
            return true;
        }
        return false;
    }
}
