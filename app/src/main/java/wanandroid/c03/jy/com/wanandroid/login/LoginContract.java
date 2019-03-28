package wanandroid.c03.jy.com.wanandroid.login;

import android.util.Log;

import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.HashMap;

import io.reactivex.Observer;
import wanandroid.c03.jy.com.wanandroid.base.IBasePresenter;
import wanandroid.c03.jy.com.wanandroid.base.IBaseView;
import wanandroid.c03.jy.com.wanandroid.data.entity.User;

/**
 * User:lenovo
 * Date:2018/12/8
 * Time:19:46
 * author:lzy
 */
public interface LoginContract {
    public interface LoginPresenter extends IBasePresenter<LoginView>{
        void login(String userName,String passworld);
    }

    public interface LoginView extends IBaseView<LoginPresenter>{

        void loginSurccess(User user);
        void loginloginFail(String mag);

    }

    public interface IRegistePresenter extends IBasePresenter<IRegisterView>{

        void registerSurceecss(String userName,String password,String rePassword);

    }

    public interface IRegisterView extends IBaseView<IRegistePresenter>{

        void registerSurceecss(User user);
        void rgisteFail(String msg);

    }

    public interface IRegisterMode{

        void registe(Observer<User> callBack, RxFragment rxFragment, HashMap<String,String> hashMap);
        void savUser(User user);

    }

    public interface ILoginMode{

        void login(Observer<User> callBack,RxFragment rxFragment,HashMap<String, String> hashMap);
        void aoutoLogin(Observer<User> callBack,RxFragment rxFragment);
        void save(User user);

    }
}
