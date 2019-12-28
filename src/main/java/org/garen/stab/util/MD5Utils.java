package org.garen.stab.util;

import java.security.MessageDigest;

public class MD5Utils
{
    private static final String[] strs = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    public static String MD5(String value)
    {
        String md5 = "";
        try
        {
            if (value == null) {
                return null;
            }
            byte[] btInput = value.getBytes("UTF-8");
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();

            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < md.length; i++)
            {
                int num = md[i];
                if (num < 0) {
                    num += 256;
                }
                int index1 = num / 16;
                int index2 = num % 16;
                buffer.append(strs[index1] + strs[index2]);
            }
            md5 = buffer.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return md5;
    }
}
