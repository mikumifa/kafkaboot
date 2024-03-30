package com.SEIII.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.Map;

class JsonUtilTest {
    @Test
    public void test1() throws IllegalAccessException {
        String message = "{\"docId\":\"aomos:nakqmws1484870\",\"title\":\"测试\",\"date\":\"2024-03-29T14:42:46.664Z\",\"source\":\"TEST\"}";
        Gson gson = new Gson();
        // 使用 Gson 将 JSON 字符串解析成 Map<String, String>
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        Map<String, String> map = new Gson().fromJson(message, type);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }
    }

}