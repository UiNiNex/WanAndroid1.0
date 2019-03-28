package wanandroid.c03.jy.com.wanandroid.data.entity;

import android.support.annotation.NonNull;
import android.text.TextUtils;


import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

/**
 * User:lenovo
 * Date:2018/12/4
 * Time:10:38
 * author:lzy
 */
public class HomeBanner implements Comparable<HomeBanner>{
    private String desc;
    private String id;
    private String imagePath;
    private String  title;
    private String  url;

    private int isVisible;
    private int order;
    private int type;



    public static ArrayList<String> getImageUrsl(List<HomeBanner> banners){

        ArrayList list = new ArrayList();
        for(HomeBanner banner : banners){
            if(!TextUtils.isEmpty(banner.imagePath))
                list.add(banner.imagePath);
        }
        return list;
    }

    public static ArrayList<String> getTitles(List<HomeBanner> banners){
        ArrayList list = new ArrayList();
        for(HomeBanner banner : banners){
            if(!TextUtils.isEmpty(banner.title))
                list.add(banner.title);
        }
        return list;

    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(int isVisible) {
        this.isVisible = isVisible;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }



    @Override
    public int compareTo(@NonNull HomeBanner o) {

        return o.order > order ? 1 : -1;
    }
}
