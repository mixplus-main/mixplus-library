package com.mixplus.test;



import com.mixplus.library.util.StringUtil;

public class Test {
    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            System.out.println(StringUtil.generateCode(i));
        }


    }

}
