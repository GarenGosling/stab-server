package org.garen.stab.util;

public class MessUtil {

    public static String PPP = "-u--s--r-l--o-c-a----l-s-t--a-b-l-o--g----";
    public static String SSS = "/";

    public static String unMess(int a, int b) {
        return getP().substring(a, b);
    }

    public static void main(String[] args) {
        System.out.println(unMess(0, 3));
        System.out.println(unMess(3, 8));
        System.out.println(unMess(8, 12));
        System.out.println(unMess(12, 15));
    }

    private static String getP() {
        return PPP.replace("-", "");
    }


}
