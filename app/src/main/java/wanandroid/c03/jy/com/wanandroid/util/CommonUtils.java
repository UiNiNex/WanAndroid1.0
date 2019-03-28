package wanandroid.c03.jy.com.wanandroid.util;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;

import java.util.HashMap;
import java.util.Random;


public class CommonUtils {


    public static<K,V> HashMap<K,V> buildHashMap(K [] keys,V [] values){

        HashMap<K,V> hashMap = new HashMap<>();

        for(int i =0 ;i < keys.length; i++){
            hashMap.put(keys[i],values[i] );
        }
        return hashMap;
    }

    public static int randomColor() {
        Random random = new Random();
        //0-190, 如果颜色值过大,就越接近白色,就看不清了,所以需要限定范围
        int red =random.nextInt(150);
        //0-190
        int green =random.nextInt(150);
        //0-190
        int blue =random.nextInt(150);
        //使用rgb混合生成一种新的颜色,Color.rgb生成的是一个int数
        return Color.rgb(red,green, blue);
    }
}
