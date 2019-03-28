package wanandroid.c03.jy.com.wanandroid.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wanandroid.c03.jy.com.wanandroid.MainActivity;
import wanandroid.c03.jy.com.wanandroid.R;
import wanandroid.c03.jy.com.wanandroid.base.BaseActivity;
import wanandroid.c03.jy.com.wanandroid.data.entity.AutoMassage;

public class MassageActivity extends BaseActivity {

    @BindView(R.id.massage_toolbar)
    Toolbar massageToolbar;
    @BindView(R.id.massage_webView)
    WebView massageWebView;
    @BindView(R.id.massage_toolbar_title)
    TextView massageToolbarTitle;
    @BindView(R.id.scrollView2)
    ScrollView scrollView2;
    private String detailsTitle;
    private UMShareListener umShareListener;
    private String url;
    private String imageUrl;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_massage);
        ButterKnife.bind(this);
        massageToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        EventBus.getDefault().register(this);
        EventBus.getDefault().post("");
        Intent intent = getIntent();
       if(intent != null){
           url = intent.getStringExtra("detail s_web");
            detailsTitle = intent.getStringExtra("details_text");

            massageToolbar.setTitle(detailsTitle);

            massageWebView.loadUrl(url);

        }


        registerForContextMenu(massageToolbar);
        setSupportActionBar(massageToolbar);
        massageToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
        getWindow().setNavigationBarColor(Color.TRANSPARENT);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    private void initView() {
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */ /**
          * @descrption 分享成功的回调
          * @param platform 平台类型
          */ /**
           * @descrption 分享失败的回调
           * @param platform 平台类型
           * @param t 错误原因
           */ /**
            * @descrption 分享取消的回调
            * @param platform 平台类型
            */

           umShareListener = new UMShareListener() {
                /**
                 * @descrption 分享开始的回调
                 * @param platform 平台类型
                 */
                @Override
                public void onStart(SHARE_MEDIA platform) {
                }
                /**
                 * @descrption 分享成功的回调
                 * @param platform 平台类型
                 */
                @Override
                public void onResult(SHARE_MEDIA platform) {
                    Toast.makeText(MassageActivity.this,"成功了",Toast.LENGTH_LONG).show();
                }
                /**
                 * @descrption 分享失败的回调
                 * @param platform 平台类型
                 * @param t 错误原因
                 */
                @Override
                public void onError(SHARE_MEDIA platform, Throwable t) {
                    Toast.makeText(MassageActivity.this,"失                                            败"+t.getMessage(),Toast.LENGTH_LONG).show();
                }
                /**
                 * @descrption 分享取消的回调
                 * @param platform 平台类型
                 */
                @Override
                public void onCancel(SHARE_MEDIA platform) {
                    Toast.makeText(MassageActivity.this,"取消                                          了",Toast.LENGTH_LONG).show();
                }
            };
    }

    @SuppressLint("JavascriptInterface")
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void MassgeTransfer(AutoMassage autoMassage) {
        Log.e("LZY------url",autoMassage.url);
        url = autoMassage.url;
        imageUrl = autoMassage.imageUrl;

        massageWebView.getSettings().setJavaScriptEnabled(true);

        WebSettings settings = massageWebView.getSettings();
        //设置屏幕适应
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

        //防止中文乱码
        settings.setDefaultTextEncodingName("UTF -8");
        settings.setJavaScriptEnabled(true);
        //载入js
        //massageWebView.addJavascriptInterface(new JavascriptInterface(this),"imagelistner");

        massageWebView.setWebViewClient(new WebViewClient() {
            // 网页跳转
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            // 网页加载结束
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                // html加载完成之后，添加监听图片的点击js函数

                addImageClickListner();
            }

            private void addImageClickListner() {

            }
        });

        massageWebView.loadUrl(autoMassage.url);

        title = autoMassage.title;
        massageToolbarTitle.setText(title);



    }

    public void setWebView(String url){

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE, Menu.FIRST + 1, 5, "分享").setIcon(
                android.R.drawable.ic_menu_delete);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case Menu.FIRST+1:


                UMWeb web = new UMWeb(massageWebView.getUrl());
                web.setTitle(massageWebView.getTitle());//标题
//                if(imageUrl != null){
//                    UMImage thumb =  new UMImage(this, imageUrl);
//                    web.setThumb(thumb);  //缩略图
//                }
                web.setDescription("my description");//描述
                new ShareAction(MassageActivity.this).withText("hello")
                        .setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN)
                        .withMedia(web)
                        .setCallback(umShareListener).open();
                break;
            default:
                break;
        }

        return true;
    }

    @OnClick({R.id.massage_toolbar, R.id.massage_webView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.massage_toolbar:

                break;
            case R.id.massage_webView:

                break;
        }


    }


}

