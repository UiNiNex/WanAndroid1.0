package wanandroid.c03.jy.com.wanandroid.data.remota.rteofit.intercept;

import android.text.TextUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import wanandroid.c03.jy.com.wanandroid.AppConstant;
import wanandroid.c03.jy.com.wanandroid.MyApp;
import wanandroid.c03.jy.com.wanandroid.util.SharePreferenceUtils;

/**
 * User:lenovo
 * Date:2019/1/14
 * Time:19:33
 * author:lzy
 */
public class SaveCookieIntercept implements Interceptor{
    @Override
    public Response intercept(Chain chain) throws IOException {

        //获取request
        Request request = chain.request();
        //获取response
        Response response = chain.proceed(request);
        //获取域名
        String domain = request.url().host();
        //获取网络请求的地址
        String requestUrl = request.url().toString();

        //如果response的header中包含了cookie信息。并且此次请求登录请求或注册
        if(!TextUtils.isEmpty(response.header(AppConstant.SET_COOKIE_KEY)) && requestUrl.contains
                (AppConstant.SAVE_USER_LOGIN_KEY) || requestUrl.contains(AppConstant.SAVE_USER_REGISTER_KEY)){

            //从Header中获取Cookie信息，取出的是list
            List<String> cookies = response.headers(AppConstant.SET_COOKIE_KEY);

            if(cookies.size() > 0){
                HashSet<String> cookieSets = new HashSet<>();

                for (String cookieSet : cookies) {
                    for (String value : cookieSet.split(";")) {
                        if(!TextUtils.isEmpty(value)){
                            cookieSets.add(value);
                        }
                    }
                }

                StringBuilder stringBuilder = new StringBuilder();
                for (String cookieSet : cookieSets) {
                    stringBuilder.append(cookieSet);
                    stringBuilder.append(";");
                }

                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                SharePreferenceUtils.saveToGlobalSp(MyApp.context,domain,stringBuilder.toString());
            }

        }

        return response;
    }
}
