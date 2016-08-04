package com.smoothframe.util;


import android.content.Context;
import android.content.SharedPreferences;

/**
 * 配置文件缓存类
 * 使用者根据应用需求仿照示例增加相应的方法即可。
 */
public class SharePreference {
    public SharedPreferences sp;
    public SharedPreferences.Editor editor;

    public SharePreference(Context context, String file) {
        sp = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public void clear() {
        editor.clear().commit();
    }

    public void putString(String key, String values) {
        editor.putString(key, values);
        editor.commit();
    }

    public String getString(String key) {
        return sp.getString(key, "");
    }
}
