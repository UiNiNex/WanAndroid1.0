package wanandroid.c03.jy.com.wanandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.jzvd.JzvdStd;
import wanandroid.c03.jy.com.wanandroid.adepter.HomeAdapter;
import wanandroid.c03.jy.com.wanandroid.adepter.KnowHierarAdpter;
import wanandroid.c03.jy.com.wanandroid.base.BaseHomeActivity;
import wanandroid.c03.jy.com.wanandroid.base.BaseLoginActivity;
import wanandroid.c03.jy.com.wanandroid.data.entity.AutoMassage;
import wanandroid.c03.jy.com.wanandroid.data.entity.HomeUser;
import wanandroid.c03.jy.com.wanandroid.data.entity.KnowHerarData;
import wanandroid.c03.jy.com.wanandroid.home.HomeFragment;
import wanandroid.c03.jy.com.wanandroid.home.MassageActivity;
import wanandroid.c03.jy.com.wanandroid.knowledgeHierarchy.FragmentsMessageActivity;
import wanandroid.c03.jy.com.wanandroid.knowledgeHierarchy.KnowledgeHierarchyFragment;
import wanandroid.c03.jy.com.wanandroid.login.LoginActivity;
import wanandroid.c03.jy.com.wanandroid.navigation.JDNavigation.JDNavigation;
import wanandroid.c03.jy.com.wanandroid.navigation.NavigationFragment;
import wanandroid.c03.jy.com.wanandroid.oficialAccounts.VideoFragment;
import wanandroid.c03.jy.com.wanandroid.project.ProjectFragment;
import wanandroid.c03.jy.com.wanandroid.ui.mapNavigation.MapNavigations;
import wanandroid.c03.jy.com.wanandroid.util.SharePreferenceUtils;

import static wanandroid.c03.jy.com.wanandroid.AppConstant.KEY_UM_HEAD_PROFILE_STAUTUS;
import static wanandroid.c03.jy.com.wanandroid.AppConstant.KEY_UM_HEAD_PROFILE_URL;
import static wanandroid.c03.jy.com.wanandroid.AppConstant.LOGGING_STATUS;
import static wanandroid.c03.jy.com.wanandroid.AppConstant.ParamsUser.KEY_USER_NAME;
import static wanandroid.c03.jy.com.wanandroid.AppConstant.ParamsUser.KEY_USER_PASSWORD;

public class MainActivity extends BaseHomeActivity
        implements NavigationView.OnNavigationItemSelectedListener, HomeAdapter.HomePageOnClicks, HomeFragment.HomeBanneOnClicke, KnowHierarAdpter.KnowHieraraPageOnClicks {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.main_framelayout)
    FrameLayout mainFramelayout;
    @BindView(R.id.main_bottom_tab_home)
    RadioButton mainBottomTabHome;
    @BindView(R.id.main_bottom_tab_knowledge)
    RadioButton mainBottomTabKnowledge;
    @BindView(R.id.main_bottom_tab_official_accounts)
    RadioButton mainBottomTabOfficialAccounts;
    @BindView(R.id.main_bottom_tab_navigation)
    RadioButton mainBottomTabNavigation;
    @BindView(R.id.main_bottom_tab_project)
    RadioButton mainBottomTabProject;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private NavigationView navigationView;
    private TextView navUserName;
    private Unbinder bind;
    private DrawerLayout drawer;
    private ImageView navUerImage;
    private SharedPreferences globalSP;
    private SensorManager sensorManager;
    private SensorEventListener sensorEventListener;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent = new Intent();
            intent.putExtra("","");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind = ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        View headerView = navView.getHeaderView(0);
        navUserName = headerView.findViewById(R.id.nav_user_name);
        navUerImage = headerView.findViewById(R.id.imageView);
        navUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //登陆状态
        globalSP = SharePreferenceUtils.getGlobalSP(this);
        boolean logginStatus = globalSP.getBoolean(LOGGING_STATUS, false);

        if (logginStatus) {
            String userName = globalSP.getString(KEY_USER_NAME, "");
            navUserName.setText(userName);
//            String navImagUrl = globalSP.getString(KEY_UM_HEAD_PROFILE_URL, "");
//            if(!navImagUrl.isEmpty()){
//                GlideApp.with(this).load(navImagUrl).into(navUerImage);
//            }
            boolean aBoolean = globalSP.getBoolean(KEY_UM_HEAD_PROFILE_STAUTUS,false);
            if(aBoolean){
                UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.QQ,
                        getUMAuthListener(navigationView,navUserName,navUerImage));
            }
        } else {
            navUserName.setText("登录");


        }
        addFragment(HomeFragment.class, R.id.main_framelayout);
        logingstatus(navigationView, navUserName, this);
        initView();

    }

    private void initView() {
        View mContent_main = getLayoutInflater().inflate(R.layout.content_main, null);
        RadioGroup mRadioGroup = mContent_main.findViewById(R.id.bottom);

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

        toolbar.setTitle("首页");

        //一键回顶部
        fabOnClick(fab);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //上下文菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //侧拉菜单的点击事件
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            startActivity(new Intent(MainActivity.this, MapNavigations.class));
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_about_us) {

        } else if (id == R.id.nav_logout) {
            HashMap<String, String> spmap = new HashMap<>();

            spmap.put(KEY_USER_NAME, "登录");
            spmap.put(KEY_USER_PASSWORD, "");
            spmap.put(KEY_UM_HEAD_PROFILE_URL, "");
            SharePreferenceUtils.saveToGlobalSp(this, spmap);
            SharePreferenceUtils.saveToGlobalSp(this, AppConstant.LOGGING_STATUS, false);

            navUserName.setClickable(true);
            logingstatus(navigationView, navUserName, this);
            SharedPreferences globalSP = SharePreferenceUtils.getGlobalSP(this);
//            boolean aBoolean = globalSP.getBoolean(KEY_UM_HEAD_PROFILE_STAUTUS,false);
            startActivity(new Intent(this, LoginActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //点击事件
    @OnClick({R.id.main_bottom_tab_home, R.id.main_bottom_tab_knowledge, R.id.main_bottom_tab_official_accounts, R.id.main_bottom_tab_navigation, R.id.main_bottom_tab_project, R.id.radioGroup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_bottom_tab_home:
                addFragment(HomeFragment.class, R.id.main_framelayout);
                break;
            case R.id.main_bottom_tab_knowledge:
                toolbar.setTitle("知识体系");
                addFragment(KnowledgeHierarchyFragment.class, R.id.main_framelayout);
                break;
            case R.id.main_bottom_tab_official_accounts:
                toolbar.setTitle("公众号");
                addFragment(VideoFragment.class, R.id.main_framelayout);
                break;
            case R.id.main_bottom_tab_navigation:
                toolbar.setTitle("导航");
                addFragment(JDNavigation.class, R.id.main_framelayout);
                break;
            case R.id.main_bottom_tab_project:
                toolbar.setTitle("项目");
                addFragment(ProjectFragment.class, R.id.main_framelayout);
                break;
        }
    }

    //点击事件接口回调
    @Override
    public void onClick(HomeUser mArticles) {
        AutoMassage autoMassage = new AutoMassage();
        autoMassage.title = mArticles.getTitle();
        autoMassage.url = mArticles.getLink();
        autoMassage.imageUrl = mArticles.getEnvelopePic();
        EventBus.getDefault().postSticky(autoMassage);
        startActivity(new Intent(MainActivity.this, MassageActivity.class));
    }

    @Override
    public void onClick(KnowHerarData mKnowHerarData) {
        EventBus.getDefault().postSticky(mKnowHerarData);
        startActivity(new Intent(MainActivity.this, FragmentsMessageActivity.class));
    }

    //Eventbus
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void HomeTransfer(String s) {
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onUMData(Map<String, String> data){
        if(drawer == null){
            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        }
        HashMap<String, String> map = new HashMap<>();

        SharePreferenceUtils.saveToGlobalSp(this,map);
        SharePreferenceUtils.saveToGlobalSp(this, AppConstant.LOGGING_STATUS, true);
        SharePreferenceUtils.saveToGlobalSp(this,KEY_UM_HEAD_PROFILE_STAUTUS,true);
        logingstatus(navigationView, navUserName, this);
        navUserName.setText(data.get("screen_name"));
        GlideApp.with(this).load(data.get("profile_image_url")).into(navUerImage);
        logingstatus(navigationView, navUserName, this);
    }

    @Override
    public void BannerAutoMasseg(AutoMassage autoMassage) {
        EventBus.getDefault().postSticky(autoMassage);
        startActivity(new Intent(MainActivity.this, MassageActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
        EventBus.getDefault().unregister(this);
        UMShareAPI.get(this).release();
    }

}
