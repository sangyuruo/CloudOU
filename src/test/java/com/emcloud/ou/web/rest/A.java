package com.emcloud.ou.web.rest;

import java.util.regex.Pattern;

public class A {
    public static void main(String[] args) {
        System.out.println(checked("Az","Ag"));
    }

    public static boolean checked(String fzz,String zz){
        boolean bool = Pattern.matches("^"+fzz+".*", zz);
        return bool;
    }

}
