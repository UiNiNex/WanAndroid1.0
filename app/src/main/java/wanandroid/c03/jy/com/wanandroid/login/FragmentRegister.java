package wanandroid.c03.jy.com.wanandroid.login;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import wanandroid.c03.jy.com.wanandroid.R;
import wanandroid.c03.jy.com.wanandroid.base.BasFragment;
import wanandroid.c03.jy.com.wanandroid.data.RegisterReposition;
import wanandroid.c03.jy.com.wanandroid.data.entity.User;
import wanandroid.c03.jy.com.wanandroid.view.LoadingPage;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentRegister extends BasFragment implements LoginContract.IRegisterView {

    @BindView(R.id.sliderover_user_img)
    ImageView slideroverUserImg;
    @BindView(R.id.login_edt_username)
    EditText loginEdtUsername;
    @BindView(R.id.textInputLayout6)
    TextInputLayout textInputLayout6;
    @BindView(R.id.login_edt_password)
    EditText loginEdtPassword;
    @BindView(R.id.textInputLayout5)
    TextInputLayout textInputLayout5;
    @BindView(R.id.login_edt_repassword)
    EditText loginEdtRepassword;
    @BindView(R.id.textInputLayout4)
    TextInputLayout textInputLayout4;
    @BindView(R.id.login_btn_register)
    Button loginBtnRegister;
    @BindView(R.id.login_tv_to_login)
    TextView loginTvToLogin;
    Unbinder unbinder;

    private RegistePresenter mPresenter;

    public String mRepassword;
    private String mPassword;
    private String mUserName;

    public FragmentRegister() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPresenter = new RegistePresenter(RegisterReposition.getmInstance(getContext().getApplicationContext()));
        setPresenter(mPresenter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_fragment_register, container, false);

        unbinder = ButterKnife.bind(this, inflate);

        intView();
        return inflate;
    }

    private void intView() {
        loginBtnRegister.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {
                mUserName = loginEdtUsername.getText().toString();
                mPassword = loginEdtPassword.getText().toString();
                mRepassword = loginEdtRepassword.getText().toString();
                showError(getContext(),loginEdtUsername,loginEdtPassword,loginEdtRepassword,mPresenter, mUserName, mPassword, mRepassword);
//                Presenter.register(mEdtUserName.getText().toString(),mEdtPassword.getText().toString(),mEdtRepassword.getText().toString());
            }
        });

        loginTvToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFragment(FragmentLogin.class,R.id.login_fragment);
            }
        });
    }

    @Override
    public void registerSurceecss(User user) {
        Toast.makeText(getContext(), "注册成功", Toast.LENGTH_LONG).show();
        removeLoadingPage();
        closeActivity();
    }

    @Override
    public void rgisteFail(String msg) {
        onError(new LoadingPage.OnReloadCallBack() {
            @Override
            public void reload() {
                mPresenter.registerSurceecss(mUserName, mPassword, mRepassword);
            }
        }, msg);
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setPresenter(LoginContract.IRegistePresenter presenter) {
        presenter.attachView(this);
    }

    @Override
    public Context getContextObject() {
        return getContext();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mPresenter.detachView();
    }
}
