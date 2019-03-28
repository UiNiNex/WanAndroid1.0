package wanandroid.c03.jy.com.wanandroid.login;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.umeng.socialize.UMShareAPI;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;

import wanandroid.c03.jy.com.wanandroid.MainActivity;
import wanandroid.c03.jy.com.wanandroid.R;
import wanandroid.c03.jy.com.wanandroid.base.BaseActivity;

public class LoginActivity extends BaseActivity implements FragmentLogin.OnUMComplete{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addFragment(FragmentLogin.class,R.id.login_fragment);
        EventBus.getDefault().register(this);
        FragmentLogin.setmOnUMComplete(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void onUMComplete(Map<String, String> data) {
        //1553395323531
        Log.d("LZY---------data",data.get("expiration"));
        EventBus.getDefault().postSticky(data);
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUMData(String s){

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
