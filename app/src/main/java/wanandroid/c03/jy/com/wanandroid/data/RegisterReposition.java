package wanandroid.c03.jy.com.wanandroid.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;

import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import wanandroid.c03.jy.com.wanandroid.data.entity.HttpResult;
import wanandroid.c03.jy.com.wanandroid.data.entity.User;
import wanandroid.c03.jy.com.wanandroid.data.remota.rteofit.DataService;
import wanandroid.c03.jy.com.wanandroid.data.remota.rteofit.WARetrofitService;
import wanandroid.c03.jy.com.wanandroid.exception.ExceptionManager;
import wanandroid.c03.jy.com.wanandroid.login.LoginContract;
import wanandroid.c03.jy.com.wanandroid.util.CommonUtils;
import wanandroid.c03.jy.com.wanandroid.util.SharePreferenceUtils;

import static wanandroid.c03.jy.com.wanandroid.AppConstant.LOGGING_STATUS;
import static wanandroid.c03.jy.com.wanandroid.AppConstant.ParamsUser.KEY_USER_NAME;
import static wanandroid.c03.jy.com.wanandroid.AppConstant.ParamsUser.KEY_USER_PASSWORD;

/**
 * User:lenovo
 * Date:2018/12/8
 * Time:21:20
 * author:lzy
 */
public class RegisterReposition implements LoginContract.IRegisterMode {

    private static volatile RegisterReposition mInstance;

    private WARetrofitService mService;

    private Context mContext;

    private RegisterReposition(Context context){
        mContext = context.getApplicationContext();
        mService = DataService.getService(mContext,false);
    }

    public static RegisterReposition getmInstance(Context context){
        if(mInstance == null){
            synchronized (RegisterReposition.class){
                if(mInstance == null){
                    mInstance = new RegisterReposition(context);
                }
            }
        }
        return mInstance;
    }

    @Override
    public void registe(Observer<User> callBack, RxFragment rxFragment, HashMap<String, String> hashMap) {
        mService.register(hashMap).flatMap(new Function<HttpResult<User>, ObservableSource<User>>() {
            @Override
            public ObservableSource<User> apply(HttpResult<User> userHttpResult) throws Exception {

                if(userHttpResult.errorCode == 0){
                    if(userHttpResult.data != null){
                        return Observable.just(userHttpResult.data);
                    }else{
                        return Observable.error(ExceptionManager.buildServerErrorMessage(userHttpResult.errorCode,userHttpResult.errorMsg));
                    }
                }
                return Observable.error(ExceptionManager.buildServerErrorMessage(userHttpResult.errorCode,userHttpResult.errorMsg));
            }
        }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(rxFragment.<User>bindUntilEvent(FragmentEvent.DETACH))
                .subscribe(callBack);
    }

    @Override
    public void savUser(final User user) {
        Observable.create(new ObservableOnSubscribe<Object>() {

            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {

                String userName = "";
                String passworld = "";

                if(user != null){
                    userName = user.getUsername();
                    passworld = user.getPassword();
                    HashMap<String, String> map = CommonUtils.buildHashMap(new String[]{KEY_USER_NAME, KEY_USER_PASSWORD}, new String[]{userName, passworld});
                    SharePreferenceUtils.saveToGlobalSp(mContext,map);
                    SharePreferenceUtils.saveToGlobalSp(mContext,LOGGING_STATUS,true);
                }
            }

        }).observeOn(Schedulers.io()).subscribe(

        );
    }
}
