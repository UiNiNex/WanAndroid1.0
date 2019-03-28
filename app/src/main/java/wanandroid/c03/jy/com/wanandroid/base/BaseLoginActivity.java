package wanandroid.c03.jy.com.wanandroid.base;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import wanandroid.c03.jy.com.wanandroid.AppConstant;
import wanandroid.c03.jy.com.wanandroid.GlideApp;
import wanandroid.c03.jy.com.wanandroid.R;
import wanandroid.c03.jy.com.wanandroid.navigation.NavigationContract;
import wanandroid.c03.jy.com.wanandroid.util.GlideBlurformation;
import wanandroid.c03.jy.com.wanandroid.util.SharePreferenceUtils;

/**
 * User:lenovo
 * Date:2018/12/24
 * Time:10:51
 * author:lzy
 */
public class BaseLoginActivity extends BaseActivity{

    private UMAuthListener authListener;

    public UMAuthListener getUMAuthListener(final NavigationView navigationView, final TextView
            tv, final ImageView iv){
        authListener = new UMAuthListener() {
            /**
             * @desc 授权开始的回调
             * @param platform 平台名称
             */
            @Override
            public void onStart(SHARE_MEDIA platform) {
            }
            /**
             * @desc 授权成功的回调
             * @param platform 平台名称
             * @param action 行为序号，开发者用不上
             * @param data 用户资料返回
             */
            @Override
            public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
                SharePreferenceUtils.saveToGlobalSp(BaseLoginActivity.this, AppConstant
                        .LOGGING_STATUS, true);
                loginsucceed(navigationView ,tv,iv,data.get("screen_name"),data.get
                        ("profile_image_url"));

            }
            /**
             * @desc 授权失败的回调
             * @param platform 平台名称
             * @param action 行为序号，开发者用不上
             * @param t 错误原因
             */
            @Override
            public void onError(SHARE_MEDIA platform, int action, Throwable t) {
                Toast.makeText(BaseLoginActivity.this, "失败：" + t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
            /**
             * @desc 授权取消的回调
             * @param platform 平台名称
             * @param action 行为序号，开发者用不上
             */
            @Override
            public void onCancel(SHARE_MEDIA platform, int action) {
                Toast.makeText(BaseLoginActivity.this, "取消了", Toast.LENGTH_LONG).show();
            }
        };
        return authListener;
    }

    public void loginsucceed(NavigationView navigationView,TextView tv, ImageView iv,String sTv,
                             String sIv){
        if(!sIv.isEmpty() && !sTv.isEmpty()){
            tv.setText(sTv);
            tv.setClickable(false);
            GlideApp.with(this).load(sIv).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(iv);

            Menu menu = navigationView.getMenu();
            MenuItem item = menu.findItem(R.id.nav_logout);
            item.setVisible(true);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode,resultCode,data);
    }


}
