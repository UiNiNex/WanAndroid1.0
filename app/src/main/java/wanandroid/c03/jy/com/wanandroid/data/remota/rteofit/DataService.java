package wanandroid.c03.jy.com.wanandroid.data.remota.rteofit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import wanandroid.c03.jy.com.wanandroid.AppConstant;
import wanandroid.c03.jy.com.wanandroid.data.remota.rteofit.intercept.AddHearderInterceptor;
import wanandroid.c03.jy.com.wanandroid.data.remota.rteofit.intercept.SaveCookieIntercept;

/**
 * User:lenovo
 * Date:2018/12/8
 * Time:10:29
 * author:lzy
 */

public class DataService {
    private static final long DEFAULT_TIMOUT = 20000;
    private static final int PAGE_TAG = 0;

    private static volatile WARetrofitService mRetrofitService;

    private static volatile WARetrofitService mRetrofitServices;
    private static Context mContext;

    public static WARetrofitService getService(Context context,boolean clen){

        if(mRetrofitService == null){
            synchronized (DataService.class){
                if(mRetrofitService == null){
                    mContext = context;
                    Retrofit retrofit;
                    if(clen){
                        retrofit = new Retrofit.Builder()
                                .client(getOkhttpClient(context))
                                .addConverterFactory(GsonConverterFactory.create())
                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                .baseUrl(AppConstant.BASE_URL)
                                .build();
                    }else{
                       retrofit = new Retrofit.Builder()
                                .client(getOkhttpClient(context))
                                .addConverterFactory(GsonConverterFactory.create())
                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                .baseUrl(AppConstant.BASE_URL)
                                .build();
                    }
                    mRetrofitService = retrofit.create(WARetrofitService.class);
                }
            }
        }
        return mRetrofitService;
    }
    public static WARetrofitService getVideoService(Context context){

        if(mRetrofitServices == null){
            synchronized (DataService.class){
                if(mRetrofitServices == null){
                    mContext = context;
                    Retrofit retrofit;

                       retrofit = new Retrofit.Builder()
                                .addConverterFactory(GsonConverterFactory.create())
                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                .baseUrl(AppConstant.VIDEO_URL)
                                .build();

                    mRetrofitServices = retrofit.create(WARetrofitService.class);
                }
            }
        }
        return mRetrofitServices;
    }

    private static OkHttpClient getOkhttpClient(Context context) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        //创建缓存路径，定义缓存文件的大小
        Cache cache = new Cache(new File(context.getCacheDir(), "Cache"), 1024 * 1024 * 10);
//        MyCacheinterceptor myCacheinterceptor = new MyCacheinterceptor(context);

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
//                .addInterceptor(myCacheinterceptor)
//                .addNetworkInterceptor(myCacheinterceptor)
                .cache(cache)
                .addInterceptor(logging)
                .addInterceptor(new AddHearderInterceptor())
                .addInterceptor(new SaveCookieIntercept())
                .connectTimeout(DEFAULT_TIMOUT, TimeUnit.MILLISECONDS)
                .readTimeout(DEFAULT_TIMOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(DEFAULT_TIMOUT, TimeUnit.MILLISECONDS)
                .build();

        return okHttpClient;
    }

}
