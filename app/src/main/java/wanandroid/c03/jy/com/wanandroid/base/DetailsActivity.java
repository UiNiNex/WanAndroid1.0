//package wanandroid.c03.jy.com.wanandroid.util;
//
//import android.app.Activity;
//import android.content.ClipData;
//import android.content.ClipboardManager;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.drawable.ColorDrawable;
//import android.net.Uri;
//import android.os.Bundle;
//import android.support.v7.widget.Toolbar;
//import android.text.Html;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.webkit.WebSettings;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//import android.widget.ImageView;
//import android.widget.PopupWindow;
//import android.widget.TextView;
//import android.widget.Toast;
//
////import com.test.lc.wanandroid.R;
////import com.test.lc.wanandroid.base.BaseActivity;
////import com.test.lc.wanandroid.util.StatusBarUtil;
////import com.umeng.analytics.MobclickAgent;
//
//import java.util.ArrayList;
//
//import wanandroid.c03.jy.com.wanandroid.R;
//import wanandroid.c03.jy.com.wanandroid.base.BaseActivity;
//
////支持App内部javascript交互
////webview.getSettings().setJavaScriptEnabled(true);
////自适应屏幕
//// webview.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
//// webview.getSettings().setLoadWithOverviewMode(true);
////  //设置可以支持缩放
//// webview.getSettings().setSupportZoom(true);
//// 扩大比例的缩放
//// webview.getSettings().setUseWideViewPort(true);
////设置是否出现缩放工具
////webview.getSettings().setBuiltInZoomControls(true);
//public class DetailsActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener {
//
//    private TextView details_text;
//    private WebView details_web;
//    private Toolbar details_toolbar;
//    private Intent intent;
//    private ImageView details_menu;
//    private String webUrl;
//
//    //剪切板管理工具类
//    private ClipboardManager clipboardManager;
//    //剪切板Data对象
//    private ClipData clipData;
//
//    @Override
//    protected void initView() {
//
//        details_text = findViewById(R.id.details_text);
//
//        details_web = findViewById(R.id.details_web);
//
//        details_toolbar = findViewById(R.id.details_toolbar);
//
//        details_menu = findViewById(R.id.details_menu);
//
//        StatusBarUtil.setColor(this, R.color.tab_toolbar);
//
//        details_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                finish();
//
//            }
//        });
//
//        details_menu.setOnClickListener(this);
//
//        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
//
//
//        init();
//
//
//    }
//
//
//    private void init() {
//
//        intent = getIntent();
//
//        webUrl = intent.getStringExtra("details_web");
//        String text = intent.getStringExtra("details_text");
//
//        details_text.setText(Html.fromHtml(text));
//
//
//        LoadWeb(webUrl);
//
//    }
//
//    private void LoadWeb(String webUrl) {
//        details_web.addJavascriptInterface(new MJavascriptInterface(this), "imagelistener");//将js对象与java对象进行映射
//        WebSettings web_settings = details_web.getSettings();
//        web_settings.setJavaScriptEnabled(true);
//        web_settings.setLoadWithOverviewMode(true);
//        web_settings.setSupportZoom(true);
//        web_settings.setBuiltInZoomControls(true);
//        web_settings.setDisplayZoomControls(false);
//        web_settings.setDefaultTextEncodingName("utf-8");
//        web_settings.setDomStorageEnabled(true);
//        details_web.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                view.getSettings().setJavaScriptEnabled(true);
//                super.onPageStarted(view, url, favicon);
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                view.getSettings().setJavaScriptEnabled(true);
//                super.onPageFinished(view, url);
//                addImageClickListner(view);//待网页加载完全后设置图片点击的监听方法 } });
//                //new Thread(GetComment).start();
//            }
//        });
//        //
//        //
//        // details_web.setWebViewClient(new WebViewClient());
//        details_web.loadUrl(webUrl);
//    }
//
//    // 注入js函数监听
//    private void addImageClickListner(WebView view) {
//        // 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去，获取该页所有图片存到picArray中并且获得点击图片的位置
////        details_web.loadUrl("javascript:(function(){" + "var objs = document.getElementsByTagName(\"img\"); "
////                + "var picArray = new Array(); " + "for(var i=0;i<objs.length;i++) " + "{" + "  picArray[i] = objs[i].src  " + "  }  "
////                + "for(var i=0;i<objs.length;i++)  " + "{" + " objs[i].index = i; " + "    objs[i].onclick=function()  " + "    {  "
////                + "        window.imagelistner.openImage(picArray,this.index);  " + "    }  " + "}" + "})()");
////    }
//
//
//        //"cc_detail_blog_img"这个ClassName和前端对应的，前端那边不能修改，对应的是img的ClassName
////        view.loadUrl("javascript:(function(){" + "var objs = document.getElementsByClassName(\"cc_detail_blog_img\");" +
////                " " + " var array=new Array(); " +
////                " for(var j=0;j<objs.length;j++){ " + "array[j]=objs[j].src;" + " }  " +
////                "for(var i=0;i<objs.length;i++)  " + "{" + "  objs[i].onclick=function()  " + "  {  " + "   " +
////                " window.imagelistener.openImage(this.src,array);  " + "  }  " + "}" + "})()");
//        view.loadUrl("javascript:(function(){" +
//                "var objs = document.getElementsByTagName(\"img\"); " +
//                "for(var i=0;i<objs.length;i++)  " +
//                "{"
//                + "    objs[i].onclick=function()  " +
//                "    {  "
//                + "        window.imagelistner.openImage(this.src);  " +
//                "    }  " +
//                "}" +
//                "})()");
//
//    }
//
//    @Override
//    protected int getActivityLayoutId() {
//        return R.layout.activity_details;
//    }
//
//    @Override
//    protected void loadData() {
//
//    }
//
//
//    @Override
//    public void onClick(View v) {
//
//        switch (v.getId()) {
//
//            case R.id.details_menu:
//
//                PupopWindow();
//
//                break;
//            case R.id.pup_refresh:
//
//                details_web.reload();//刷新
//
//                break;
//
//            case R.id.pup_copy:
//
//                clipData = ClipData.newPlainText("Simple test", webUrl);
//
//                clipboardManager.setPrimaryClip(clipData);
//
//                Toast.makeText(this, "地址已复制到剪切板", Toast.LENGTH_SHORT).show();
//
//                break;
//
//            case R.id.pup_open:
//                //调用系统默认浏览器
//                Uri uri = Uri.parse(webUrl);
//                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                startActivity(intent);
//                break;
//
//            case R.id.pup_share:
//                Intent textIntent = new Intent(Intent.ACTION_SEND);
//                textIntent.setType("text/plain");
//                textIntent.putExtra(Intent.EXTRA_TEXT, "WanAndroid" + '\n' + "https://fir.im/e39b");
//                startActivity(Intent.createChooser(textIntent, "分享到"));
//
//                break;
//
//            default:
//
//                break;
//
//        }
//
//    }
//
//    private void PupopWindow() {
//
//        PopupWindow popupWindow = new PopupWindow();
//
//        View view = LayoutInflater.from(this).inflate(R.layout.pupopwindow, null);
//        popupWindow.setContentView(view);
//        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//        popupWindow.setWidth(500);
//        popupWindow.setBackgroundDrawable(new ColorDrawable());
//        popupWindow.setOutsideTouchable(true);
//        popupWindow.showAsDropDown(details_menu, -30, 6);
//
//        TextView pup_refresh = view.findViewById(R.id.pup_refresh);
//        TextView pup_copy = view.findViewById(R.id.pup_copy);
//        TextView pup_open = view.findViewById(R.id.pup_open);
//        TextView pup_share = view.findViewById(R.id.pup_share);
//
//        pup_refresh.setOnClickListener(this);
//        pup_copy.setOnClickListener(this);
//        pup_open.setOnClickListener(this);
//        pup_share.setOnClickListener(this);
//
//    }
//
//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        return false;
//    }
//
//
//    public class MJavascriptInterface {
//        private Activity context;
//
//        private static final String TAG = "SIMON";
//
//        public MJavascriptInterface(Activity context) {
//            this.context = context;
//        }
//
////        @android.webkit.JavascriptInterface //必须添加
////        public void openImage(String img, String[] array) {
////            Log.i(TAG, "openImage: " + img);
////            for (String imgUrl : array) {
////                Log.i(TAG, "openImage: " + imgUrl);
////            }
////            Intent intent = new Intent(context, ViewPhotoActivity.class);
////            intent.putExtra("imageUrls", array);
////            intent.putExtra("curImg", img);
////            context.startActivity(intent);
////        }
//
//        ArrayList<String> images = new ArrayList<>();
//
//        @android.webkit.JavascriptInterface
//        public void readImageUrl(String img) {
//            //把所有图片的url保存在ArrayList<String>中
//            images.add(img);
//        }
//        //  @android.webkit.JavascriptInterface
//        //对于targetSdkVersion>=17的，要加这个声明
//
//        public void openImage(String clickimg) {
//            int index = 0;
//            ArrayList<String> list = images;
//            for (String url : list)
//                if (url.equals(clickimg))
//                    index = list.indexOf(clickimg);
//            //获取点击图片在整个页面图片中的位置
//            Intent intent = new Intent();
//            Bundle bundle = new Bundle();
//            bundle.putStringArrayList("list_image", list);
//            bundle.putInt("index", index);
//            intent.putExtra("bundle", bundle);
//            //将所有图片的url以及点击图片的位置作为参数传给启动的activity
//            intent.setClass(context, ViewPhotoActivity.class);
//            context.startActivity(intent);//启动ViewPagerActivity,用于显示图片
//
//
////        public void readImageUrl(String img) {
////            //把所有图片的url保存在ArrayList<String>中 images.add(img); }
////            // @android.webkit.JavascriptInterface
////            // 对于targetSdkVersion>=17的，要加这个声明
////            public void openImage(String clickimg)
////            // /点击图片所调用到的函数
////            { int index = 0; ArrayList<String> list = addImages();
////             for (String url : list) if (url.equals(clickimg)) index = list.indexOf(clickimg);
////            // 获取点击图片在整个页面图片中的位置
////                // Intent intent = new Intent(); Bundle bundle = new Bundle();
////                // bundle.putStringArrayList("list_image", list); bundle.putInt("index", index);
////                // intent.putExtra("bundle", bundle);//将所有图片的url以及点击图片的位置作为参数传给启动的activity intent.setClass(context, ShowWebImageActivity.class); context.startActivity(intent);//启动ViewPagerActivity,用于显示图片 } } //去重复 private ArrayList<String> addImages() { ArrayList<String> list = new ArrayList<>(); Set set = new HashSet(); for (String cd:images) { if(set.add(cd)){ list.add(cd); } } return list;
//
//
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        MobclickAgent.onResume(this);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        MobclickAgent.onPause(this);
//    }
//
//}
