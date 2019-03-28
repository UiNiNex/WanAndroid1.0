package wanandroid.c03.jy.com.wanandroid.base;

import android.content.Context;

/**
 * User:lenovo
 * Date:2018/12/8
 * Time:19:47
 * author:lzy
 */
public interface IBaseView <T extends IBasePresenter>{
    void setPresenter(T presenter);
    Context getContextObject();
}
