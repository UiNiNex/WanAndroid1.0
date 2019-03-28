package wanandroid.c03.jy.com.wanandroid.login;

import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.Observer;

import io.reactivex.disposables.Disposable;
import wanandroid.c03.jy.com.wanandroid.data.LoginRepository;
import wanandroid.c03.jy.com.wanandroid.data.entity.User;
import wanandroid.c03.jy.com.wanandroid.exception.ExceptionManager;
import wanandroid.c03.jy.com.wanandroid.util.CommonUtils;

import static wanandroid.c03.jy.com.wanandroid.AppConstant.ParamsUser.KEY_USER_NAME;
import static wanandroid.c03.jy.com.wanandroid.AppConstant.ParamsUser.KEY_USER_PASSWORD;

/**
 * User:lenovo
 * Date:2018/12/8
 * Time:23:26
 * author:lzy
 */
public class LoginPresenter implements LoginContract.LoginPresenter {


    private LoginContract.LoginView mView;
    private LoginRepository mLoginRepository;

    public LoginPresenter(LoginRepository mLoginRepository) {

        this.mLoginRepository = mLoginRepository;
    }

    @Override
    public void login(String userName, String passworld) {
        mLoginRepository.login(new io.reactivex.Observer<User>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(User user) {
                mView.loginSurccess(user);
                mLoginRepository.save(user);
            }

            @Override
            public void onError(Throwable e) {
;                mView.loginloginFail(e.getLocalizedMessage());
            }

            @Override
            public void onComplete() {

            }
        }, (RxFragment) mView, CommonUtils.buildHashMap(new String[]{KEY_USER_NAME, KEY_USER_PASSWORD},
                new String[]{userName, passworld}));
    }

    @Override
    public void attachView(LoginContract.LoginView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
