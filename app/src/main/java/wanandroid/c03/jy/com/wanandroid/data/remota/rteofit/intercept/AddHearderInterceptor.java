package wanandroid.c03.jy.com.wanandroid.data.remota.rteofit.intercept;

import android.text.TextUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import wanandroid.c03.jy.com.wanandroid.AppConstant;
import wanandroid.c03.jy.com.wanandroid.MyApp;
import wanandroid.c03.jy.com.wanandroid.util.SharePreferenceUtils;

/**
 * User:lenovo
 * Date:2019/1/14
 * Time:18:29
 * author:lzy
 */
public class AddHearderInterceptor implements Interceptor{

    @Override
    public Response intercept(Chain chain) throws IOException {
        //获取Request对象
        Request request = chain.request();
        //获取域名
        String host = request.url().host();
        //获取网络请求的路径
        String requestUrl = request.url().toString();

        //获取builder对象
        Request.Builder builder = request.newBuilder();


        if(requestUrl.contains(AppConstant.COLLECTIONS_WEBSITE) || requestUrl.contains(AppConstant.ARTICLE_WEBSITE)
                ||requestUrl.contains(AppConstant.TODO_WEBSITE) || requestUrl.contains(AppConstant.UNCOLLECTIONS_WEBSITE)){

            //获取保存的cookie
            String cookie = SharePreferenceUtils.getFormGobalSp(MyApp.context, host, "");
            //判断是否有cookie
            if(!TextUtils.isEmpty(cookie)){
                builder.addHeader(AppConstant.COOKIE_NAME,cookie);
            }

        }

        //重新builde
        return chain.proceed(builder.build());
    }
}
