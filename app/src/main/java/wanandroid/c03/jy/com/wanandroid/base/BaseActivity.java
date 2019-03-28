package wanandroid.c03.jy.com.wanandroid.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.List;

import wanandroid.c03.jy.com.wanandroid.AppConstant;
import wanandroid.c03.jy.com.wanandroid.R;
import wanandroid.c03.jy.com.wanandroid.util.SharePreferenceUtils;
import wanandroid.c03.jy.com.wanandroid.view.LoadingPage;

import static wanandroid.c03.jy.com.wanandroid.AppConstant.ParamsUser.KEY_USER_NAME;

/**
 * User:lenovo
 * Date:2018/12/8
 * Time:11:06
 * author:lzy
 */
public class BaseActivity extends RxAppCompatActivity{

    private FragmentManager mFragmentManager;
    private LoadingPage loadingPage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        mFragmentManager = getSupportFragmentManager();
    }

    //封装公共的添加fragment方法
    public void addFragment(Class<? extends BasFragment> zClass, int layoutId){
        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        //获取将要添加fragment的类名
        String tag = zClass.getName();
        //在布局管理器中永磊名获取标记
        Fragment fragmentByTag = mFragmentManager.findFragmentByTag(tag);

        if(fragmentByTag != null){
            if(fragmentByTag.isAdded()){
                transaction.show(fragmentByTag);
                hideIOtherPage(transaction,fragmentByTag);
            }else{
                transaction.add(layoutId,fragmentByTag,tag);
                hideIOtherPage(transaction,fragmentByTag);
            }
        }else{

            try {
                fragmentByTag = zClass.newInstance();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }

            if(fragmentByTag != null){
                transaction.add(layoutId,fragmentByTag,tag);
                hideIOtherPage(transaction,fragmentByTag);
            }

        }
        transaction.commit();
    }


    public void hideIOtherPage(FragmentTransaction fragmentTransaction,Fragment willShowFragment){
        List<Fragment> fragments = mFragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            if(fragment != willShowFragment){
                fragmentTransaction.hide(fragment);
            }
        }
    }

    public void showLodingPage(int mode){
        showLodingPage(android.R.id.content,mode);
    }

    public void showLodingPage(int groupId,int mode){
        View viewById = findViewById(groupId);
        if(viewById instanceof ViewGroup){
            showLodingPage((ViewGroup) viewById,mode);
        }
    }

    public void showLodingPage(ViewGroup viewGroup,int mode){
        if(loadingPage == null){
            loadingPage = (LoadingPage) LayoutInflater.from(this).inflate(R.layout.layout_laoding_page, viewGroup, false);
            viewGroup.addView(loadingPage);
        }
        loadingPage.startLoading(mode);

    }

    protected void onError(){

        if(loadingPage != null){
            loadingPage.onError();
        }

    }
    protected void onError(String msg){
        if(loadingPage != null){
            loadingPage.onError(msg);
        }

    }
    protected void onError(LoadingPage.OnReloadCallBack callBack ,String msg){
        if(loadingPage != null){
            loadingPage.onError(callBack,msg);
        }
    }



    protected void removeLoadingPage(){
        if(loadingPage != null){
            ((ViewGroup)loadingPage.getParent()).removeView(loadingPage);
            loadingPage = null;
        }
    }

    public void logingstatus(NavigationView navigationView, TextView nav_header_title, Context context){

        //登录状态
        boolean bloggingstatus = SharePreferenceUtils.getGlobalSP(this).getBoolean(AppConstant.LOGGING_STATUS, false);

        if(bloggingstatus){
            Menu menu = navigationView.getMenu();
            MenuItem item = menu.findItem(R.id.nav_logout);
            item.setVisible(true);
            nav_header_title.setClickable(false);
            String username = SharePreferenceUtils.getGlobalSP(context).getString(KEY_USER_NAME, "");
            nav_header_title.setText(username);
        }else{
            nav_header_title.setText(SharePreferenceUtils.getGlobalSP(context).getString(KEY_USER_NAME,"登录"));
            Menu menu = navigationView.getMenu();
            MenuItem item = menu.findItem(R.id.nav_logout);
            item.setVisible(false);
        }

    }

    public Fragment getFragment(){
        List<Fragment> fragments = mFragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            if(!fragment.isHidden()){
                return fragment;
            }
        }
        return null;
    }

}
