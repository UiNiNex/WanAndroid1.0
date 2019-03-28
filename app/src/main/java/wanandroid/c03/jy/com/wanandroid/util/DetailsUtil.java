package wanandroid.c03.jy.com.wanandroid.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import wanandroid.c03.jy.com.wanandroid.base.BaseActivity;
import wanandroid.c03.jy.com.wanandroid.home.MassageActivity;


public class DetailsUtil {

    private static DetailsUtil detailsUtil;


    public static synchronized DetailsUtil getInstance() {
        if (detailsUtil == null) {
            synchronized (DetailsUtil.class) {
                if (detailsUtil == null) {
                    detailsUtil = new DetailsUtil();
                }
            }
        }
        return detailsUtil;
    }

    public void setIntent(Context context, String details_web, String details_text) {
        Intent intent = new Intent(context, MassageActivity.class);
        intent.putExtra("details_web", details_web);
        intent.putExtra("details_text", details_text);
        context.startActivity(intent);
    }


}
