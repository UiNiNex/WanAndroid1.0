package wanandroid.c03.jy.com.wanandroid.base;

/**
 * User:lenovo
 * Date:2018/12/8
 * Time:19:47
 * author:lzy
 */
public interface IBasePresenter <T extends IBaseView>{

    void attachView(T view);

    void detachView();
}
