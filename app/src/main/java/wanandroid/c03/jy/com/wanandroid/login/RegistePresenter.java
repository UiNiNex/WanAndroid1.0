package wanandroid.c03.jy.com.wanandroid.login;

import com.trello.rxlifecycle2.components.support.RxFragment;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import wanandroid.c03.jy.com.wanandroid.data.entity.User;
import wanandroid.c03.jy.com.wanandroid.util.CommonUtils;

import static wanandroid.c03.jy.com.wanandroid.AppConstant.ParamsUser.KEY_USER_NAME;
import static wanandroid.c03.jy.com.wanandroid.AppConstant.ParamsUser.KEY_USER_PASSWORD;
import static wanandroid.c03.jy.com.wanandroid.AppConstant.ParamsUser.KEY_USER_REPASSWORD;

/**
 * User:lenovo
 * Date:2018/12/8
 * Time:21:19
 * author:lzy
 */
public class RegistePresenter implements LoginContract.IRegistePresenter {

    private LoginContract.IRegisterMode mMode;

    private LoginContract.IRegisterView mView;

    public RegistePresenter (LoginContract.IRegisterMode mode){
        mMode = mode;
    }

    @Override
    public void registerSurceecss(String userName,String password,String rePassword) {
        mMode.registe(new Observer<User>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(User user) {
                mView.registerSurceecss(user);
                mMode.savUser(user);
            }

            @Override
            public void onError(Throwable e) {
                mView.rgisteFail(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        },(RxFragment)mView, CommonUtils.buildHashMap(new String[]{KEY_USER_NAME, KEY_USER_PASSWORD, KEY_USER_REPASSWORD},new String[]{userName,password,rePassword}));
    }


    @Override
    public void attachView(LoginContract.IRegisterView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
