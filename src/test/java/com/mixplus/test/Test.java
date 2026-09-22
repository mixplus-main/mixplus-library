package com.mixplus.test;



import com.mixplus.library.network.Https;
import com.mixplus.library.network.Response;
import com.mixplus.library.util.StringUtil;

import java.util.Map;

public class Test {
    public static void main(String[] args) {
        Response re =  Https.request("https://api.mojang.com/users/profiles/minecraft/MIxPlus_crystal_");
        System.out.println(StringUtil.toUUID(re.body().get("id").toString()));
    }

}
