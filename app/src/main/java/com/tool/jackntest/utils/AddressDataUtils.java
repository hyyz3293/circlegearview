package com.tool.jackntest.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.blankj.utilcode.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tool.jackntest.ui.model.TestEntity;

import net.sourceforge.pinyin4j.PinyinHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AddressDataUtils {

    /** 地址选择-top数据 */
    public static List<TestEntity.TopEntity> getAddressTopList() {
        List<TestEntity.TopEntity> list = new ArrayList<>();
        list.add(new TestEntity.TopEntity("中国大陆", true));
        list.add(new TestEntity.TopEntity("海外国家/地区", false));
        return list;
    }

    /** 地址选择-城市數據 */
    public static List<TestEntity.ContetEntity> getAddressCityList(Context mContext) {
        String cityStr = getJson(mContext, "provinces.json");
        List<TestEntity.ContetEntity> list = JsonToObject(cityStr, new TypeToken<List<TestEntity.ContetEntity>>() {}.getType());
        if (null != list && list.size() > 0) {
            for (TestEntity.ContetEntity c: list) {
                if (!StringUtils.isEmpty(c.name)) {
                    String[] pys = PinyinHelper.toHanyuPinyinStringArray(c.name.charAt(0));
                    if (pys != null && pys.length > 0) {
                        if (!StringUtils.isEmpty(pys[0])) {
                            c.py = pys[0].substring(0, 1).toUpperCase();
                        }
                    }
                }
            }
            Collections.sort(list, new Comparator<TestEntity.ContetEntity>() {
                @Override
                public int compare(TestEntity.ContetEntity contetEntity, TestEntity.ContetEntity t1) {
                    return contetEntity.py.compareTo(t1.py);
                }
            });
            String oldPy = "";
            for (TestEntity.ContetEntity c: list) {
                String py = c.py;
                if (!py.equals(oldPy)) {
                    c.py = py;
                    oldPy = py;
                } else
                    c.py = "";
            }
        }
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
    public static <T> T JsonToObject(String json, Type type) {
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }

}
