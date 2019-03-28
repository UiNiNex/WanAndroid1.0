package wanandroid.c03.jy.com.wanandroid;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

/**
 * User:leno
 * Date:2018/12/21
 * Time:16:25
 * author:lzy
 */
public class MyApp extends MultiDexApplication {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        UMConfigure.init(this,"5a12384aa40fa3551f0001d1"
                ,"umeng", UMConfigure.DEVICE_TYPE_PHONE,"");//58edcfeb310c93091c000be2 5965ee00734be40b580001a0
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "5c1de413b465f5252d000024");
        PlatformConfig.setSinaWeibo("3921700954", "5c1de413b465f5252d000024", "http://sns.whalecloud.com");
        PlatformConfig.setQQZone("100424468", "5c1de413b465f5252d000024");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
