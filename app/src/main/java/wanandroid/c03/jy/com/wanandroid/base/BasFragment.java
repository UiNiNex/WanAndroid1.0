package wanandroid.c03.jy.com.wanandroid.base;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.trello.rxlifecycle2.components.support.RxFragment;

import org.greenrobot.eventbus.EventBus;

import wanandroid.c03.jy.com.wanandroid.AppConstant;
import wanandroid.c03.jy.com.wanandroid.R;
import wanandroid.c03.jy.com.wanandroid.login.LoginContract;
import wanandroid.c03.jy.com.wanandroid.login.RegistePresenter;
import wanandroid.c03.jy.com.wanandroid.view.LoadingPage;

/**
 * User:lenovo
 * Date:2018/12/8
 * Time:11:28
 * author:lzy
 */
@SuppressLint("ValidFragment")
public class BasFragment extends RxFragment{

    private BaseActivity mBaseActivity;
    private ObjectAnimator outAnimator, inAnimator;
    private ObjectAnimator imageOutAnimator, iamgeInAnimator;
    private ObjectAnimator fabOutAnimator, fabInAnimator;
    private RadioGroup radioGroup;
    private FloatingActionButton fab;
    private ImageView imageView;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if(activity instanceof BaseActivity){
            mBaseActivity = (BaseActivity) activity;
        }

    }

    protected void showLoadingPage(int mode) {
        if(mBaseActivity != null){
            mBaseActivity.showLodingPage(mode);
        }
    }


    protected void showLoadingPage(int groupId,int mode) {
        if(mBaseActivity != null){
            mBaseActivity.showLodingPage(groupId,mode);
        }
    }

    protected void showLoadingPage(ViewGroup group, int mode){
        if(mBaseActivity != null){
            mBaseActivity.showLodingPage(group,mode);
        }
    }


    protected void onError(){
        if(mBaseActivity != null){
            mBaseActivity.onError();
        }

    }
    protected void onError(String msg){

        if(mBaseActivity != null){
            mBaseActivity.onError(msg);
        }
    }
    protected void onError(LoadingPage.OnReloadCallBack callBack , String msg){
        if(mBaseActivity != null){
            mBaseActivity.onError(callBack,msg);
        }
    }

    protected void removeLoadingPage(){
        if(mBaseActivity != null){
            mBaseActivity.removeLoadingPage();
        }
    }


    protected void addFragment(Class<? extends BasFragment> zClass,int layoutId){
        if(mBaseActivity != null){
            mBaseActivity.addFragment(zClass,layoutId);
        }
    }

    protected void closeActivity(){
        if(mBaseActivity != null){
            mBaseActivity.finish();
        }
    }

    public void showError(EditText nameId, EditText passwordId, LoginContract.LoginPresenter mPresenter, String username, String password){

        if(isNull(username) && isNull(password)){
            if(isNull(username)){
                edtError(nameId,username,AppConstant.EDIT_ERROR_COLOR,"账号不能为空");
            }
            if(isNull(password)){
                edtError(passwordId,username,AppConstant.EDIT_ERROR_COLOR,"密码不能为空");
            }else if(isSize(username) && isSize(password)){
                showLoadingPage(LoadingPage.MODE_ONLY_SHOW_LOGING);
                mPresenter.login(username, password);
            }
        } else {
            if(isSize(username)) {
                edtError(nameId, AppConstant.EDIT_ERROR_COLOR, "账号不能小于6且大于12位");
            }
            if(isSize(password)){
                edtError(passwordId,AppConstant.EDIT_ERROR_COLOR,"密码不能小于6且大于12位");
            }
        }

    }

    public boolean isNull(String user){
        if(user.isEmpty()){
            return true;
        }
        return false;
    }

    public boolean isSize(String user){
        String isSize =  "[a-zA-Z0-9]{6,12}";
        if(user.matches(isSize)){
            return true;
        }
        return false;
    }

    public void showError(Context context, EditText nameId, EditText passwordId, EditText rePasswordId, RegistePresenter mPresenter, String username, String password, String repassword){

        if(isNull(username) && isNull(password) && isNull(repassword)){
            if(isNull(username)){
                edtError(nameId,username,AppConstant.EDIT_ERROR_COLOR,AppConstant.EDIT_ERROR_USERNAME_NULL_MASSAGE);
            }
            if(isNull(password)){
                edtError(passwordId,password,AppConstant.EDIT_ERROR_COLOR,AppConstant.EDIT_ERROR_PASSWORD_NULL_MASSAGE);
            }
            if(isNull(repassword)){
                edtError(rePasswordId,repassword,AppConstant.EDIT_ERROR_COLOR,AppConstant.EDIT_ERROR_PASSWORD_NULL_MASSAGE);
            }
        }else {

            if(password.equals(repassword)){
                if (isSize(username) && isSize(password) && isSize(repassword)) {
                    showLoadingPage(LoadingPage.MODE_ONLY_SHOW_LOGING);
                    mPresenter.registerSurceecss(username, password, repassword);
                }else {
                    Toast.makeText(context,AppConstant.EDIT_ERROR_STRING_SIZE_MASSAGE,Toast.LENGTH_SHORT).show();
                }

            }else {
                edtError(rePasswordId, AppConstant.EDIT_ERROR_COLOR,AppConstant.EDIT_ERROR_PASSWORD_AND_REPASSWORD_MASSAGE);
            }

        }
    }

    public void edtError(EditText edtId, String color, String error) {

        //3.设置文字及颜色
        edtId.setError(Html.fromHtml("<font color=" + color + ">" + error + "</font>"));
    }


    public void edtError(EditText edtId,String username, String color, String error) {
        getPopwindow(edtId,color,error);

    }

    private void getPopwindow(EditText edtId, String color, String error) {
        //3.设置文字及颜色
        edtId.setError(Html.fromHtml("<font color=" + color + ">" + error + "</font>"));
    }

    public void hdieBottom(RecyclerView recyclerView, FragmentActivity activity){
        radioGroup = activity.findViewById(R.id.radioGroup);
        imageView = activity.findViewById(R.id.imageView2);
        fab = activity.findViewById(R.id.fab);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy>10){
                    if (outAnimator == null) {
                        outAnimator = ObjectAnimator.ofFloat(radioGroup, "translationY", 0, radioGroup.getHeight());
                        imageOutAnimator = ObjectAnimator.ofFloat(imageView,"translationX",
                                0,-imageView.getWidth());
                        fabOutAnimator = ObjectAnimator.ofFloat(fab,"translationX",0, fab
                                .getWidth()+ fab.getPaddingLeft());

                        fabOutAnimator.setDuration(800);
                        imageOutAnimator.setDuration(800);
                        outAnimator.setDuration(800);
                    }
                    if (!outAnimator.isRunning() && radioGroup.getTranslationY() <= 0) {
                        fab.hide();
                        fabOutAnimator.start();
                        imageOutAnimator.start();
                        outAnimator.start();

                    }

                    return;
                }
                if(dy<-10){
                    if (inAnimator == null) {
                        inAnimator = ObjectAnimator.ofFloat(radioGroup, "translationY", radioGroup.getHeight
                                (), 0);
                        iamgeInAnimator = ObjectAnimator.ofFloat(imageView,"translationX",
                                -imageView.getWidth()
                                ,0);
                        fabInAnimator = ObjectAnimator.ofFloat(fab,"translationX",fab
                                .getWidth()+ fab.getPaddingLeft(),0);

                        fabInAnimator.setDuration(800);
                        iamgeInAnimator.setDuration(800);
                        inAnimator.setDuration(800);
                    }
                    if (!inAnimator.isRunning() && radioGroup.getTranslationY() >= radioGroup.getHeight()) {
                        fabInAnimator.start();
                        iamgeInAnimator.start();
                        inAnimator.start();
                        fab.show();
                    }
                }
//
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
