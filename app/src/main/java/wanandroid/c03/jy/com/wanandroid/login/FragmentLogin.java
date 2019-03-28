package wanandroid.c03.jy.com.wanandroid.login;


import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import wanandroid.c03.jy.com.wanandroid.AppConstant;
import wanandroid.c03.jy.com.wanandroid.R;
import wanandroid.c03.jy.com.wanandroid.base.BasFragment;
import wanandroid.c03.jy.com.wanandroid.data.LoginRepository;
import wanandroid.c03.jy.com.wanandroid.data.entity.User;
import wanandroid.c03.jy.com.wanandroid.util.SharePreferenceUtils;
import wanandroid.c03.jy.com.wanandroid.view.LoadingPage;

import static wanandroid.c03.jy.com.wanandroid.AppConstant.ParamsUser.KEY_USER_NAME;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentLogin extends BasFragment implements LoginContract.LoginView {


    @BindView(R.id.sliderover_user_img)
    ImageView slideroverUserImg;
    @BindView(R.id.login_edt_username)
    EditText loginEdtUsername;
    @BindView(R.id.textInputLayout4)
    TextInputLayout textInputLayout4;
    @BindView(R.id.login_edt_password)
    EditText loginEdtPassword;
    @BindView(R.id.textInputLayout5)
    TextInputLayout textInputLayout5;
    @BindView(R.id.login_btn_login)
    Button loginBtnLogin;
    @BindView(R.id.login_tv_to_register)
    TextView loginTvToRegister;
    Unbinder unbinder;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.textView4)
    TextView textView4;
    @BindView(R.id.fragment_login_youmeng_weixin)
    ImageView imageView3;
    @BindView(R.id.fragment_login_youmeng_xinlang)
    ImageView imageView5;
    @BindView(R.id.fragment_login_youmeng_qq)
    ImageView fragmentLoginYoumengQq;
    private UMAuthListener authListener;
        public static  OnUMComplete mOnUMComplete;
    private String mLoginEdtpasw;
    private String mLoginEdtName;

    public FragmentLogin() {
        // Required empty public constructor
    }

    private LoginContract.LoginPresenter mLoginPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setPresenter(new LoginPresenter(LoginRepository.getInstance(context.getApplicationContext())));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */ /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */ /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */ /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
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
                    if(mOnUMComplete != null){
                        mOnUMComplete.onUMComplete(data);
                    }
                }
                /**
                 * @desc 授权失败的回调
                 * @param platform 平台名称
                 * @param action 行为序号，开发者用不上
                 * @param t 错误原因
                 */
                @Override
                public void onError(SHARE_MEDIA platform, int action, Throwable t) {
                    Toast.makeText(getContext(), "失败：" + t.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                /**
                 * @desc 授权取消的回调
                 * @param platform 平台名称
                 * @param action 行为序号，开发者用不上
                 */
                @Override
                public void onCancel(SHARE_MEDIA platform, int action) {
                    Toast.makeText(getContext(), "取消了", Toast.LENGTH_LONG).show();
                }
            };
    }

    @OnClick({R.id.login_btn_login, R.id.login_tv_to_register,R.id.fragment_login_youmeng_weixin, R.id.fragment_login_youmeng_qq, R.id.fragment_login_youmeng_xinlang})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_btn_login:
                mLoginEdtName = loginEdtUsername.getText().toString();
                mLoginEdtpasw = loginEdtPassword.getText().toString();

                if (mLoginEdtName.isEmpty() || mLoginEdtpasw.isEmpty()) {
                    if (isNull(mLoginEdtName)) {
                        edtError(loginEdtUsername, mLoginEdtName, "#00ffff", "账号不能为空");
                    }
                    if (isNull(mLoginEdtpasw)) {
                        edtError(loginEdtPassword, mLoginEdtpasw, "#00ffff", "密码不能为空");
                    }
                } else {
                    showLoadingPage(LoadingPage.MODE_ONLY_SHOW_LOGING);
                    mLoginPresenter.login(mLoginEdtName, mLoginEdtpasw);
                }
                break;
            case R.id.login_tv_to_register:
                addFragment(FragmentRegister.class, R.id.login_fragment);
                break;
            case R.id.fragment_login_youmeng_weixin:

                break;
            case R.id.fragment_login_youmeng_qq:

                if(Build.VERSION.SDK_INT>=23){
                    String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CALL_PHONE,Manifest.permission.READ_LOGS,Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.SET_DEBUG_APP,Manifest.permission.SYSTEM_ALERT_WINDOW,Manifest.permission.GET_ACCOUNTS,Manifest.permission.WRITE_APN_SETTINGS};
                    ActivityCompat.requestPermissions(getActivity(),mPermissionList,123);
                }

                UMShareAPI.get(getActivity()).isInstall(getActivity(), SHARE_MEDIA.WEIXIN);
                UMShareConfig config = new UMShareConfig();
                config.isNeedAuthOnGetUserInfo(true);
                UMShareAPI.get(getActivity()).setShareConfig(config);
                UMShareAPI.get(getContext()).getPlatformInfo(getActivity(), SHARE_MEDIA.QQ,
                        authListener);
                break;
            case R.id.fragment_login_youmeng_xinlang:
                break;
        }
    }

    @Override
    public void loginSurccess(User user) {
        removeLoadingPage();
        Toast.makeText(getContext(),
                "登录成功", Toast.LENGTH_LONG).show();
        SharePreferenceUtils.saveToGlobalSp(getContext(),KEY_USER_NAME,mLoginEdtName);
        SharePreferenceUtils.saveToGlobalSp(getContext(), AppConstant.LOGGING_STATUS,true);
        closeActivity();
    }

    @Override
    public void loginloginFail(String mag) {
//        onError(mag);
        removeLoadingPage();
        Toast.makeText(getContext(), mag, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setPresenter(LoginContract.LoginPresenter presenter) {
        mLoginPresenter = presenter;
        mLoginPresenter.attachView(this);
    }

    @Override
    public Context getContextObject() {
        return getContext();
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           String permissions[], int[] grantResults) {
//        Toast.makeText(getContext(),requestCode+"",Toast.LENGTH_SHORT).show();
//    }

    public static void setmOnUMComplete(OnUMComplete OnUMComplete) {
        mOnUMComplete = OnUMComplete;
    }

    public interface OnUMComplete{
        void onUMComplete(Map<String, String> data);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mLoginPresenter.detachView();
        unbinder.unbind();
    }

}
