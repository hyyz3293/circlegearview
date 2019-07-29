package com.tool.jackntest.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.tool.jackntest.ui.model.TestEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AddressDataUtils {

    /** 地址选择-top数据 */
    public static List<TestEntity.TopEntity> getAddressTopList() {
        List<TestEntity.TopEntity> list = new ArrayList<>();
        list.add(new TestEntity.TopEntity("中国大陆", true));
        list.add(new TestEntity.TopEntity("海外国家/地区", false));
        return list;
    }




    /**
     * 得到json文件中的内容
     * @param context
     * @param fileName
     * @return
     */
    public static String getJson(Context context, String fileName){
        StringBuilder stringBuilder = new StringBuilder();
        //获得assets资源管理器
        AssetManager assetManager = context.getAssets();
        //使用IO流读取json文件内容
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName),"utf-8"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 将字符串转换为 对象
     * @param json
     * @param type
     * @return
     */
    public static <T> T JsonToObject(String json, Class<T> type) {
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }

}
