package wanandroid.c03.jy.com.wanandroid.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SharePreferenceUtils {

    private static final String GLOBAL_NAME = "wan_android";


    // 获取一个全局或者默认的 sp
    public static SharedPreferences getGlobalSP(Context context) {

        return context.getSharedPreferences(GLOBAL_NAME, Context.MODE_PRIVATE);
    }

    // 保存 多个 key value 信息到 全局的 sp 中。
    public static void saveToGlobalSp(Context context, HashMap<String, String> infos) {

        synchronized (GLOBAL_NAME) {
            SharedPreferences.Editor editor = getGlobalSP(context).edit();

            Iterator<Map.Entry<String, String>> iterator = infos.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                editor.putString(entry.getKey(), entry.getValue());
            }

            editor.commit();
        }

    }

    /**
     * 保存 一个 key ,value  到 默认的 sp 中
     */
    public static void saveToGlobalSp(Context context,  String key, String value) {

        synchronized (GLOBAL_NAME) {
            SharedPreferences.Editor editor = getGlobalSP(context).edit();
            editor.putString(key, value);
            editor.commit();
        }

    }

    public static void saveToGlobalSp(Context context,  String key, boolean value) {

        synchronized (GLOBAL_NAME) {
            SharedPreferences.Editor editor = getGlobalSP(context).edit();
            editor.putBoolean(key, value);
            editor.commit();
        }

    }

    public static String getFormGobalSp(Context context,String key,String defaultValue){

        synchronized (GLOBAL_NAME){
            SharedPreferences sp  = getGlobalSP(context);
            return sp.getString(key, defaultValue);
        }

    }
}
