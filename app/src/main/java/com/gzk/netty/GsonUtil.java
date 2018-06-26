package com.gzk.netty;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class GsonUtil {

    public static <T> T fromJson(String jsonStr, Type type) {
        return new Gson().fromJson(jsonStr, type);
    }


}
