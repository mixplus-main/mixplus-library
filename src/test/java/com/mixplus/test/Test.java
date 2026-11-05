package com.mixplus.test;



import com.mixplus.library.mysql.Column;
import com.mixplus.library.mysql.DataType;
import com.mixplus.library.mysql.MySQL;
import com.mixplus.library.network.Https;
import com.mixplus.library.network.Response;
import com.mixplus.library.util.StringUtil;

import java.util.Map;

public class Test {
    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            System.out.println(StringUtil.generateCode(i));
        }


    }

}
